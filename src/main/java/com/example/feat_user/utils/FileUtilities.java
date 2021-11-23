package com.example.feat_user.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class FileUtilities {
    public BufferedImage convertBase64ToStream(String base64String){
        BufferedImage image = null;
        byte[] imageByte;
        try {
            imageByte = Base64.getDecoder().decode(base64String);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public String encodeFileToBase64Binary(String imageName) throws Exception{
        File fileImage = new File(imageName);
        String type = fileImage.getName().split("\\.")[1];
        FileInputStream fileInputStreamReader = new FileInputStream(fileImage);
        byte[] bytes = new byte[(int)fileImage.length()];
        fileInputStreamReader.read(bytes);
        String base64Header = "";
        switch (type){
            case "jpg": base64Header = "data:image/jpeg;base64";
                break;
            case "png": base64Header = "data:image/png;base64";
                break;
        }
        String base64 = new String(Base64.getEncoder().encode(bytes), "UTF-8");
        return base64Header+","+base64;
    }
}
