package com.example.feat_user.models.reponse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class GetEstimateResponse implements Serializable {
    private double archHeight;
}
