package com.shopmate.product.request;

import lombok.Data;

@Data
public class ProductFilterRequest {
    private String name;
    private String category;
    private Double minPrice;
    private Double maxPrice;
}
