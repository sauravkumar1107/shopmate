package com.shopmate.product.controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.shopmate.product.model.InvocationRequest;
import com.shopmate.product.service.EndpointSelectorService;
import com.shopmate.product.service.InvocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QueryController{

    private final EndpointSelectorService selectorService;
    private final InvocationService invocationService;

    @GetMapping("/route")
    public String routeQuery(@RequestParam String query) throws IOException, InterruptedException {
        List<String> apiOptions = List.of(
                "GET/http://localhost:8080/api/product/match/{id} -> Returns products similar to the given product id",
                "GET/http://localhost:8080/api/product/{id} → Returns product with of a particular id"
                );

        InvocationRequest invocationRequest= selectorService.selectEndpointWithParams(query, apiOptions);
        String fetchProductResponse= invocationService.fetchResponseFromEndpoint(invocationRequest);
        return fetchProductResponse;
    }


}
