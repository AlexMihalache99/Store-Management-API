package com.alexandru.store_management_api.service;


import com.alexandru.store_management_api.entity.Product;
import com.alexandru.store_management_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateProduct(Long id, BigDecimal price, Integer stockQuantity) {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {

            if(price != null) {
                existingProduct.setPrice(price);
            }
            if(stockQuantity != null) {
                existingProduct.setStockQuantity(stockQuantity);
            }
            return productRepository.save(existingProduct);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
