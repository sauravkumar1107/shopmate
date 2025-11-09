package com.shopmate.product.service;

import com.shopmate.product.model.InvocationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class InvocationService {
    private final JavaHttpClientService javaHttpClientService;
    public String fetchResponseFromEndpoint(InvocationRequest invocationRequest) throws IOException, InterruptedException {
       // String endpoint=invocationRequest.getEndpoint();
       // Map<String,String> paramMap=invocationRequest.getParams();
        String response=javaHttpClientService.callApi(invocationRequest);
        return response;
    }
}
