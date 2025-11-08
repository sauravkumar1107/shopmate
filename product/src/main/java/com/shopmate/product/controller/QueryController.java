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

    @GetMapping("/route")
    public JsonNode routeQuery(@RequestParam String query) {
        List<String> apiOptions = List.of(
                "GET /api/product/{id} → Returns product with of a particular id"
        );

        return selectorService.selectEndpointWithParams(query, apiOptions);
    }
}
