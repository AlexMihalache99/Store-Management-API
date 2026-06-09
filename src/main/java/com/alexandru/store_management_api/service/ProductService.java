package com.alexandru.store_management_api.service;


import com.alexandru.store_management_api.dto.CreateProductRequest;
import com.alexandru.store_management_api.dto.ProductResponse;
import com.alexandru.store_management_api.dto.UpdateProductRequest;
import com.alexandru.store_management_api.entity.Product;
import com.alexandru.store_management_api.exception.ProductNotFoundException;
import com.alexandru.store_management_api.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());

        Product savedProduct = productRepository.save(product);
        return toResponse(savedProduct);
    }
    
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return toResponse(product);
    }

    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
        
        if(request.price() != null) {
            product.setPrice(request.price());
        }
        if(request.stockQuantity() != null) {
            product.setStockQuantity(request.stockQuantity());
        }

        Product updatedProduct = productRepository.save(product);

        return toResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);
    }

    private ProductResponse toResponse(Product product) {
    return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStockQuantity()
    );
}
}
