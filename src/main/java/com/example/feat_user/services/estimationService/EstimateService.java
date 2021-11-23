package com.example.feat_user.services.estimationService;

import com.example.feat_user.models.reponse.GetEstimateResponse;
import com.example.feat_user.models.request.GetEstimateRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class EstimateService {
    private final String estimationUrl = "https://estimation-servece.herokuapp.com/estimate";
    public GetEstimateResponse getEstimateResponse(double heelWidth, double heelToApex5, double SI, double CSI){
        GetEstimateRequest getEstimateRequest = new GetEstimateRequest();
        getEstimateRequest.setCSI(CSI);
        getEstimateRequest.setHeelWidth(heelWidth);
        getEstimateRequest.setHeelToApex5(heelToApex5);
        getEstimateRequest.setSI(SI);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<GetEstimateRequest> request = new HttpEntity<>(getEstimateRequest);

        ResponseEntity<GetEstimateResponse> response = restTemplate
                .exchange(estimationUrl, HttpMethod.POST, request, GetEstimateResponse.class);
        return response.getBody();
    }
}
