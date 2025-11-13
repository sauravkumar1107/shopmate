package com.shopmate.product.dao;

import com.shopmate.product.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> filterProducts(String name, String category, Double minPrice, Double maxPrice);

}
