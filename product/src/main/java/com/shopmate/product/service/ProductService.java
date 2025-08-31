package com.shopmate.product.service;

import com.shopmate.product.dao.ProductRepository;
import com.shopmate.product.model.Product;
import com.shopmate.product.request.CreateProductRequest;
import com.shopmate.product.transformer.ProductTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTransformer productTransformer;
    public boolean addProduct(CreateProductRequest createProductRequest) {
        try {
            Product logProduct= productRepository.save(productTransformer.buildProductFromCreateProductRequest(createProductRequest));
            log.info("product : {}", logProduct.getPrice());
            return true;
        } catch (Exception e) {
            log.error("Error occurred while saving", e);
            return false;
        }
    }


    public Product getProduct(String id) {
        return productRepository.findById(id).orElse(null);
    }

}
