package com.example.ecom_server.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom_server.models.Product;
import com.example.ecom_server.repos.ProductRepo;

@CrossOrigin("*")
@RestController
@RequestMapping("/products")
public class ProductControllers {
    
    private static final Logger log = LoggerFactory.getLogger(ProductControllers.class);

    @Autowired 
    private ProductRepo productRepo;
    
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        List<Product> products = productRepo.findAll();
        log.info("Fetched all products. Count: {}", products.size());
        return products;
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product newProduct) {
        log.info("Adding new product: {}", newProduct.getName());
        return productRepo.save(newProduct);
    }

    @DeleteMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        Optional<Product> findProduct = productRepo.findById(id);
        if (findProduct.isEmpty()) {
            log.error("Failed to delete product with ID: {}", id);
            return "Failed to delete product";
        }
        productRepo.deleteById(id);
        log.info("Product deleted with ID: {}", id);
        return "Product deleted";
    }

    @PutMapping("/product/edit/{id}")
    public Product editProduct(@PathVariable String id, @RequestBody Product newProduct) {
        Product findProduct = productRepo.findById(id).get();
        findProduct.setName(newProduct.getName());
        findProduct.setDescription(newProduct.getDescription());
        findProduct.setCategory(newProduct.getCategory());
        findProduct.setTags(newProduct.getTags());
        findProduct.setPrice(newProduct.getPrice());
        findProduct.setStock(newProduct.getStock());
        
        log.info("Updating product with ID: {}", id);
        return productRepo.save(findProduct);
    }
}


