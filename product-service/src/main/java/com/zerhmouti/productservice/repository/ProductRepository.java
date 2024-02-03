package com.zerhmouti.productservice.repository;

import com.zerhmouti.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
