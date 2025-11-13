package com.shopmate.product.dao;

import com.shopmate.product.model.Product;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>,ProductRepositoryCustom {
   Optional<Product> findById(String id);

   Product save(Product product);

}
