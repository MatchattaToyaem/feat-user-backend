package com.example.feat_user.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class Users {
    @Id
    private String _id;
    @NonNull
    private String email;
    @NonNull
    private String gender;
    @NonNull
    private double footLength;
    @NonNull
    private ComponentPoints leftFootComponent;
    @NonNull
    private ComponentPoints rightFootComponent;
    @NonNull
    private Feet leftFoot;
    @NonNull
    private Feet rightFoot;
    public Users(final String email,
                 final String gender,
                 final double footLength,
                 final ComponentPoints leftFootComponent,
                 final ComponentPoints rightFootComponent,
                 final Feet leftFoot,
                 final Feet rightFoot){

        this.email = email;
        this.gender = gender;
        this.footLength = footLength;
        this.leftFootComponent = leftFootComponent;
        this.rightFootComponent = rightFootComponent;
        this.leftFoot = leftFoot;
        this.rightFoot = rightFoot;
    }

}
