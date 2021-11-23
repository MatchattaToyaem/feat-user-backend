package com.example.feat_user.utils;

import com.example.feat_user.models.ComponentPoints;
import com.example.feat_user.models.Feet;
import com.example.feat_user.models.Users;
import com.example.feat_user.services.estimationService.EstimateService;

import java.math.BigDecimal;
import java.math.MathContext;

public class ComputeFootData {
    private final Users users;
    public ComputeFootData(Users users){
        this.users = users;
    }

    public Users transform() throws Exception {
        Feet leftFoot = users.getLeftFoot();
        Feet rightFoot = users.getRightFoot();
        ComponentPoints leftFootComponent = users.getLeftFootComponent();
        ComponentPoints rightFootComponent = users.getRightFootComponent();
        transformFoot(leftFootComponent, leftFoot);
        transformFoot(rightFootComponent, rightFoot);
        InputOutput io = new InputOutput();
        if(!users.get_id().equals("")){
            users.set_id(users.get_id());
        }
        //Compute foot data for each foot sole and foot side
        leftFoot.setSideImage(io.save(leftFoot.getSideImage(), users.getEmail(), "side", "left"));
        leftFoot.setSoleImage(io.save(leftFoot.getSoleImage(), users.getEmail(), "sole", "left"));
        rightFoot.setSideImage(io.save(rightFoot.getSideImage(), users.getEmail(), "side", "right"));
        rightFoot.setSoleImage(io.save(rightFoot.getSoleImage(), users.getEmail(), "sole", "right"));
        return users;
    }

    private void transformFoot(ComponentPoints componentPoints, Feet foot){
        String soleImage = foot.getSoleImage();
        ComputeFootParameters computeFootParameters = new ComputeFootParameters();
        double footLength = computeFootParameters
                .getFootLength(soleImage);
        computeFootParameters.setPixelRatio(footLength, users.getFootLength());
        foot.setForeWidth(computeFootParameters.getComponentWidth(componentPoints.getFore().getY(),
                soleImage));
        foot.setMiddleWidth(computeFootParameters.getComponentWidth(componentPoints.getMiddle().getY(),
                soleImage));
        foot.setHeelWidth(computeFootParameters.getComponentWidth(componentPoints.getHeel().getY(),
                soleImage));
        foot.setHeelToForeLength(computeFootParameters.getLengthBetweenComponents(footLength,
                componentPoints.getFore().getY()));
        foot.setArchIndex(computeFootParameters.getArchIndex(soleImage));
        foot.setHeelToMiddleLength(computeFootParameters.getLengthBetweenComponents(componentPoints.getMiddle().getY(),
                footLength));
        foot.setApex1ToApex5(computeFootParameters.getLengthBetweenComponents(componentPoints.getApex1().getX(),
                componentPoints.getApex5().getX()));
        foot.setFootType(computeFootParameters.getFootType(foot.getArchIndex()));
        foot.setHeelToApex5(computeFootParameters.getLengthBetweenComponents(componentPoints.getApex5().getY(),
                footLength));

        foot.setCSI(new BigDecimal(foot.getMiddleWidth()/ foot.getApex1ToApex5())
                .round(new MathContext(4)).doubleValue());
        foot.setSI(new BigDecimal(foot.getMiddleWidth()/ foot.getHeelWidth())
                .round(new MathContext(4)).doubleValue());
        foot.setArchHeight(new EstimateService().getEstimateResponse(foot.getHeelWidth(), foot.getHeelToApex5(),
                foot.getSI(), foot.getCSI()).getArchHeight());
    }
}
