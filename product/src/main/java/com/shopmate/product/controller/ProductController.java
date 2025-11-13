package com.shopmate.product.controller;
import com.shopmate.product.model.Product;
import com.shopmate.product.request.CreateProductRequest;
import com.shopmate.product.request.ProductFilterRequest;
import com.shopmate.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    private final InternalQueryController internalQueryController;
   // private final ProductFilterRequest productFilterRequest;
    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody CreateProductRequest createProductRequest){
       boolean isProductAdded = productService.addProduct(createProductRequest) ;
       return ResponseEntity.ok(isProductAdded);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id){
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping("/compare/{id1}/{id2}")
    public ResponseEntity<?> getComparisons(@PathVariable String id1, @PathVariable String id2) {
        List<Product> productList = List.of(productService.getProduct(id1), productService.getProduct(id2));
        return ResponseEntity.ok(internalQueryController.comparison(productList));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        ProductFilterRequest productFilterRequest = new ProductFilterRequest();
        productFilterRequest.setName(name);
        productFilterRequest.setCategory(category);
        productFilterRequest.setMinPrice(minPrice);
        productFilterRequest.setMaxPrice(maxPrice);

        return ResponseEntity.ok(productService.filterProducts(productFilterRequest));
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
