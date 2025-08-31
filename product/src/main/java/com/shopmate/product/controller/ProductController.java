package com.shopmate.product.controller;
import com.shopmate.product.model.Product;
import com.shopmate.product.request.CreateProductRequest;
import com.shopmate.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    public ResponseEntity<?> addProduct(CreateProductRequest createProductRequest){
       boolean isProductAdded= productService.addProduct(createProductRequest) ;
       return ResponseEntity.ok(isProductAdded);
    }

    public ResponseEntity<?> getProduct(){
        List<Product> productList=new ArrayList<>();
       // productList=productService.getProduct()

    }


}
