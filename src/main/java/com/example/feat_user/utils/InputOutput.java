package com.example.feat_user.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class InputOutput {

    public String save(String base64, String email, String component, String side) throws Exception {
        String[] base64Component = base64.split(",");
        String imageType = new ImageType().getImageType(base64Component[0]);
        String directory = "./foot_images/"+email;
        createDirectory(directory);
        String fileName = directory+"/"+component+"_"+side+"."+ imageType;
        File imageFile = new File(fileName);
        BufferedImage imageStream = new FileUtilities().convertBase64ToStream(base64Component[1]);
        ImageIO.write(imageStream, imageType, imageFile);
        return fileName;
    }

    private void createDirectory(String path){
        File directory = new File(path);

        if(!directory.exists()){
            directory.mkdirs();
        }
    }
}
