package com.example.feat_user.models.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class GetEstimateRequest implements Serializable {
    private double CSI;
    private double SI;
    private double heelWidth;
    private double heelToApex5;
}
