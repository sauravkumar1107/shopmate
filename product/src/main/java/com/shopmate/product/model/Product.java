package com.shopmate.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Getter
@AllArgsConstructor
public class Product {
    private String name;
    private String title;
    private String description;
    private double price;
    private int availableUnits;
    @Id
    private String productId;
    private String sellerId;
    private String imageS3Link;
    private String category;
    private Features features;

}
