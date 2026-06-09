package com.alexandru.store_management_api.controller;

import com.alexandru.store_management_api.dto.CreateProductRequest;
import com.alexandru.store_management_api.dto.ProductResponse;
import com.alexandru.store_management_api.dto.UpdateProductRequest;
import com.alexandru.store_management_api.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponse create(@Valid @RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.getAllProducts();
    }

    @PatchMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @Valid@RequestBody UpdateProductRequest request) {

        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
