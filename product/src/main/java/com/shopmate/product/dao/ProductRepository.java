package com.shopmate.product.dao;

import com.shopmate.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
   Optional<Product> findById(String id);

   Product save(Product product);
}
