package com.example.feat_user.utils;

import org.bson.internal.Base64;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class ComputeFootParameters {

    double ratio = 0;

    //Convert base64 string to image, which can use in opencv
    private Mat ConvertBase64ToImage(Object rawBase64) {
        byte[] rawByteImage = Base64.decode(((String) rawBase64).split(",")[1]);
        return Imgcodecs.imdecode(new MatOfByte(rawByteImage), Imgcodecs.IMREAD_UNCHANGED);
    }

    //Compute pixel to cm ratio
    public void setPixelRatio(double pixels, double cm) {
        ratio =  cm / pixels;
    }


    //Image enhancement
    public Mat prepareImage(Mat source)
    {

        double alpha = -1;
        double beta = 150;
        // init
        Mat hsvImg = new Mat();
        List<Mat> hsvPlanes = new ArrayList<>();
        Mat thresholdImg = new Mat();

        //Enhance image by using brightness
        Mat blur = new Mat();
        Imgproc.GaussianBlur(source, blur, new Size(27, 27), 10, 10, Core.BORDER_DEFAULT);
        Mat bright = new Mat(blur.rows(),blur.cols(), blur.type());
        Mat bright1 = new Mat(blur.rows(),blur.cols(), blur.type());
        Mat bctest = new Mat();
        source.convertTo(bright, 0, alpha, beta);
        //Enhance image by using scharr
        Imgproc.Scharr(bright, bctest, Imgproc.CV_SCHARR, 1, 0);
        bctest.convertTo(bright, CvType.CV_8UC3);
        bctest.convertTo(bright1,0, -5, 200);

        //Convert to HSV Image
        // threshold the image with the histogram average value
        hsvImg.create(source.size(), CvType.CV_8U);
        Imgproc.cvtColor(source, hsvImg, Imgproc.COLOR_BGR2HSV);
        // enhance
        Imgproc.GaussianBlur( hsvImg, hsvImg, new Size(27, 27), 10, 10, Core.BORDER_DEFAULT );
        Core.split(hsvImg, hsvPlanes);

        //Get threshold valuer that use to removing bg
        double threshValue = getHistogramAverage(bright1, hsvPlanes.get(0));
        //Remove back ground and convert image to black and white
        Imgproc.threshold(hsvPlanes.get(2), thresholdImg, threshValue, 255, Imgproc.THRESH_BINARY);
        Imgproc.blur(thresholdImg, thresholdImg, new Size(5, 5));

        // dilate to fill gaps, erode to smooth edges
        Imgproc.dilate(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 1);
        Imgproc.erode(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 3);

        // create the new image
        Mat foreground = new Mat(source.size(), CvType.CV_8UC1, new Scalar(0, 0, 0));
        source.copyTo(foreground, thresholdImg);

        //Fill all area of foot with black color
        Imgproc.cvtColor(foreground, source, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(source, source, threshValue, 255.0, Imgproc.THRESH_BINARY_INV);
        double kernelSize = source.width() / 220.0;
        Mat kernel = new Mat(new Size(kernelSize, kernelSize), CvType.CV_8UC1);
        Imgproc.morphologyEx(source, source, Imgproc.MORPH_OPEN, kernel);

        return source;
    }


    //Calculate the average histogram value
    private static double getHistogramAverage(Mat hsvImg, Mat hueValues) {
        // init
        double average = 0.0;
        Mat hist_hue = new Mat();
        // 0-180: range of Hue
        MatOfInt histSize = new MatOfInt(190);
        List<Mat> hue = new ArrayList<>();
        hue.add(hueValues);

        // compute the histogram
        Imgproc.calcHist(hue, new MatOfInt(0), new Mat(), hist_hue, histSize, new MatOfFloat(0, 179));
        for (int h = 0; h < 180; h++) {
            // for each bin, get its value and multiply it for the corresponding
            // hue
            average += (hist_hue.get(h, 0)[0] * h);
        }

        // return the average hue of the image
        return average / hsvImg.size().height / hsvImg.size().width;
    }

    //Generate bounding for foot image
    private Rect getBounding(Mat image) {
        Mat points = Mat.zeros(image.size(), image.type());
        Core.findNonZero(image, points);
        MatOfPoint mat = new MatOfPoint(points);
        return Imgproc.boundingRect(mat);
    }

    //Get foot length by using bounding technique
    public double getFootLength(String image) {
        Mat decodedImage = prepareImage(ConvertBase64ToImage(image));
        Mat gray = decodedImage.clone();
        Rect bounding = getBounding(gray);
        return bounding.height;
    }

    //Get length between foot components by using formula |component1-compnent2| * ratio; ratio = actual length(cm)/image length(pixels)
    public double getLengthBetweenComponents(double value1, double value2) {
        return new BigDecimal(Math.abs(value1 - value2) * ratio ).round(new MathContext(4)).doubleValue();
    }

    /*
    Get foot component width by using counting black pixel and the multiply with ration; ratio = actual length(cm)/image length(pixels),
    This use to compute fore width, middle width, heel width
     */
    public double getComponentWidth(double position, String image) {
        Mat preparedImage = prepareImage(ConvertBase64ToImage(image));
        int black = 0;
        for (int col = 0; col < preparedImage.width(); col++) {
            double[] color = preparedImage.get((int) position, col);
            if (color[0] == 0.0) {
                black ++;
            }
        }
        return new BigDecimal(black * ratio ).round(new MathContext(4)).doubleValue();
    }

    /*
    Compute arch index by cutting foot into three paths and count only black pixels.
     */
    public double getArchIndex(String image){
        Mat preparedImage = prepareImage(ConvertBase64ToImage(image));
        Mat cutImage = cutFinger(preparedImage);
        Double factor = cutImage.height()/3.0;
        int areaA = 0;
        int areaB = 0;
        int areaC = 0;
        for (int row =0; row<cutImage.height(); row++){
            for (int col = 0; col< cutImage.width(); col++){
                if(cutImage.get(row, col)[0] == 0.0){
                    if(row <= factor){
                        areaA++;
                    }
                    else if(row <= factor * 2){
                        areaB++;
                    }
                    else{
                        areaC++;
                    }
                }
            }
        }
        return (areaB*1.00)/((areaA*1.00) + (areaB*1.00) + (areaC*1.00));
    }

    /*
    Classify foot type by using arch index
     */
    public String getFootType(double archIndex){

        String footType = "normal";
        if(archIndex < 0.21){
            footType = "high";
        }else if(archIndex > 0.26){
            footType = "flat";
        }
        return footType;
    }

    /*
    Cut foot finger by find white pixel which is among the black pixels and row of image is less than foot length divided by 3,
    then cut from the row=0 to the row, which have the correct condition.
     */
    private Mat cutFinger (Mat source){
        double current_color = -1;
        int color_switch;
        int row_of_finger = 0;
        for (int y = 0; y < source.height(); y++) {
            color_switch = 0;
            for (int x = 0; x < source.width(); x++){
                double[] color = source.get(y, x);
                if(color[0] != current_color && y < source.height()/3){
                    if(current_color != -1){
                        color_switch++;
                    }
                    current_color = color[0];
                }
            }
            if(color_switch > 2){
                row_of_finger = y;
            }
        }
        Rect crop = new Rect(0, row_of_finger, source.width(), (source.height() - row_of_finger));
        return new Mat(source,crop);
    }
}
