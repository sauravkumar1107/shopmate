package com.shopmate.product.controller;
import com.shopmate.product.model.Product;
import com.shopmate.product.request.CreateProductRequest;
import com.shopmate.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody CreateProductRequest createProductRequest){
       boolean isProductAdded = productService.addProduct(createProductRequest) ;
       return ResponseEntity.ok(isProductAdded);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id){
        return ResponseEntity.ok(productService.getProduct(id));
    }

    // 1--fetch products for given filters-> name, price, category, features
    // 2--compare products->
       // req-> list of product names
       // resp-> comparison in table formats
    // 3--similar products->
      // req-> product id
      // res-> list of products
    // 4--product info-- done
      // req-> name
    // 5--product review
       // req-> name
       // response-> review summary


}
