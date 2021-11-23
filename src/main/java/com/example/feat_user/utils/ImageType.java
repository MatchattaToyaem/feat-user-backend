package com.example.feat_user.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageType {
    public String getImageType(String base64Header) throws Exception {
        Pattern pattern = Pattern.compile("(jpeg|png)");
        Matcher matcher = pattern.matcher(base64Header);
        if(matcher.find()){
            return matcher.group(0);
        }
        else{
            throw new Exception("Support only jpeg and png file");
        }
    }
}
