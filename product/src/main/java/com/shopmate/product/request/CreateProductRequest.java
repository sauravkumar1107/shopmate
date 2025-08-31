package com.shopmate.product.request;

import com.shopmate.product.model.Features;
import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private String title;
    private String description;
    private double price;
    private int availableUnits;
    private String sellerId;
    private String imageS3Link;
    private String category;
    private Features features;
    private String productId;
}
