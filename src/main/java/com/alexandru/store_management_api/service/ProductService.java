package com.alexandru.store_management_api.service;


import com.alexandru.store_management_api.dto.CreateProductRequest;
import com.alexandru.store_management_api.dto.ProductResponse;
import com.alexandru.store_management_api.dto.UpdateProductRequest;
import com.alexandru.store_management_api.entity.Product;
import com.alexandru.store_management_api.exception.ProductNotFoundException;
import com.alexandru.store_management_api.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());

        logger.debug("createProduct request: name={}, price={}, stock={}", request.name(), request.price(), request.stockQuantity());
        Product savedProduct = productRepository.save(product);
        logger.info("Created product with id={}", savedProduct.getId());
        return toResponse(savedProduct);
    }
    
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> responses = productRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
        logger.info("Retrieved {} products", responses.size());
        return responses;
    }

    public ProductResponse getProductById(Long id) {
        logger.debug("getProductById id={}", id);
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        logger.info("Retrieved product id={}", id);
        return toResponse(product);
    }

    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
        logger.debug("updateProduct id={} request: price={}, stock={}", id, request.price(), request.stockQuantity());

        if(request.price() != null) {
            product.setPrice(request.price());
        }
        if(request.stockQuantity() != null) {
            product.setStockQuantity(request.stockQuantity());
        }

        Product updatedProduct = productRepository.save(product);

        logger.info("Updated product id={}", updatedProduct.getId());
        return toResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        logger.debug("deleteProduct id={}", id);
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);
        logger.info("Deleted product id={}", id);
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
