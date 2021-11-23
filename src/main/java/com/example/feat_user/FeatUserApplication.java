package com.example.feat_user;

import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FeatUserApplication {

    public static void main(String[] args) {
        OpenCV.loadLocally();
        SpringApplication.run(FeatUserApplication.class, args);
    }

}
