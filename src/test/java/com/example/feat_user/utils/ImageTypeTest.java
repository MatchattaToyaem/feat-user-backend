package com.example.feat_user.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageTypeTest {

    @Test
    void validateValidGetImageType() throws Exception {
        String base64Header = "data:image/jpeg;base64";
        ImageType imageType = new ImageType();
        assertEquals("jpeg", imageType.getImageType(base64Header));
    }

    @Test
    void validateValidGetImageTypePNG() throws Exception {
        String base64Header = "data:image/png;base64";
        ImageType imageType = new ImageType();
        assertEquals("png", imageType.getImageType(base64Header));
    }

    @Test
    void validateInvalidGetImageType() throws Exception {
        String base64Header = "data:image/gif;base64";
        ImageType imageType = new ImageType();
        Throwable exception = assertThrows(Exception.class, ()->imageType.getImageType(base64Header));
        assertEquals("Support only jpeg and png file", exception.getMessage());
    }
}