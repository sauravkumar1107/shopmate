package com.shopmate.product.transformer;

import com.shopmate.product.model.Product;
import com.shopmate.product.request.CreateProductRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductTransformer {
    public Product buildProductFromCreateProductRequest(@NonNull final CreateProductRequest createProductRequest) {
        return Product.builder()
                .productId(createProductRequest.getProductId())
                .name(createProductRequest.getName())
                .price(createProductRequest.getPrice())
                .title(createProductRequest.getTitle())
                .availableUnits(createProductRequest.getAvailableUnits())
                .category(createProductRequest.getCategory())
                .description(createProductRequest.getDescription())
                .imageS3Link(createProductRequest.getImageS3Link())
                .features(createProductRequest.getFeatures())
                .sellerId(createProductRequest.getSellerId())
                .build();
    }
}
