package com.shopmate.product.dao;

import com.shopmate.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Product> filterProducts(String name, String category, Double minPrice, Double maxPrice) {

        Query query = new Query();
        List<Criteria> criteria = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            criteria.add(Criteria.where("name").regex(name, "i"));
        }

        if (category != null && !category.isEmpty()) {
            criteria.add(Criteria.where("category").is(category));
        }

        if (minPrice != null) {
            criteria.add(Criteria.where("price").gte(minPrice));
        }

        if (maxPrice != null) {
            criteria.add(Criteria.where("price").lte(maxPrice));
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }

        return mongoTemplate.find(query, Product.class);
    }
}

