package com.example.feat_user.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ComponentPoints {
    @NonNull
    private Point fore;
    @NonNull
    private Point apex1;
    @NonNull
    private Point apex5;
    @NonNull
    private Point middle;
    @NonNull
    private Point heel;
    @NonNull
    private Point ankleSprain;
    public ComponentPoints(final Point fore,
                           final Point middle,
                           final Point heel,
                           final Point ankleSprain,
                           final Point apex1,
                           final Point apex5){

        this.fore = fore;
        this.middle = middle;
        this.heel = heel;
        this.ankleSprain = ankleSprain;
        this.apex1 = apex1;
        this.apex5 = apex5;
    }
}
