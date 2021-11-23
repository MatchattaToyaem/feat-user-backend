package com.example.feat_user.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Feet {
    private double foreWidth;
    private double middleWidth;
    private double heelWidth;
    private double apex1ToApex5;
    private double heelToForeLength;
    private double heelToMiddleLength;
    private double archHeight;
    private double archIndex;
    private double heelToApex5;
    private double SI;
    private double CSI;
    private String footType;
    @NonNull
    private String soleImage;
    @NonNull
    private String sideImage;

    public Feet(final String soleImage,
                final String sideImage) {
        this.soleImage = soleImage;
        this.sideImage = sideImage;
    }
}
