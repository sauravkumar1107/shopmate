package com.shopmate.product.controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.shopmate.product.service.EndpointSelectorService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class QueryController{

    private final EndpointSelectorService selectorService;

    public QueryController(EndpointSelectorService selectorService) {
        this.selectorService = selectorService;
    }

    @PostMapping("/route")
    public JsonNode routeQuery(@RequestParam String query) {
        List<String> apiOptions = List.of(
                "GET /transactions/failed?limit={limit}&after={date} → Returns failed transactions",
                "GET /transactions/success?limit={limit} → Returns successful transactions",
                "GET /suppliers/stats?supplierId={id} → Returns statistics for a supplier"
        );

        return selectorService.selectEndpointWithParams(query, apiOptions);
    }
}
