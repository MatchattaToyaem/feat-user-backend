package com.example.feat_user.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Point {
    @NonNull
    private double x;
    @NonNull
    private double y;

    public Point(final double x, final double y){
        this.x = x;
        this.y = y;
    }
}
