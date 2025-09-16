package com.example.ecom_server.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ecom_server.models.Product;

public interface ProductRepo extends MongoRepository<Product,String> {

}
