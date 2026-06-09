package com.alexandru.store_management_api.service;

import com.alexandru.store_management_api.dto.CreateProductRequest;
import com.alexandru.store_management_api.dto.UpdateProductRequest;
import com.alexandru.store_management_api.dto.ProductResponse;
import com.alexandru.store_management_api.entity.Product;
import com.alexandru.store_management_api.exception.ProductNotFoundException;
import com.alexandru.store_management_api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_savesAndReturnsResponse() {
        CreateProductRequest req = new CreateProductRequest("Widget", "A widget", BigDecimal.valueOf(9.99), 10);
        Product saved = new Product("Widget", "A widget", BigDecimal.valueOf(9.99), 10);
        when(productRepository.save(any(Product.class))).thenReturn(saved);

        ProductResponse resp = productService.createProduct(req);

        assertThat(resp.name()).isEqualTo("Widget");
        assertThat(resp.price()).isEqualByComparingTo(BigDecimal.valueOf(9.99));
        assertThat(resp.stockQuantity()).isEqualTo(10);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getAllProducts_returnsResponses() {
        Product p1 = new Product("A", "d", BigDecimal.valueOf(1), 1);
        Product p2 = new Product("B", "d2", BigDecimal.valueOf(2), 2);
        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProductResponse> list = productService.getAllProducts();

        assertThat(list).hasSize(2);
        assertThat(list.get(0).name()).isEqualTo("A");
        verify(productRepository).findAll();
    }

    @Test
    void getProductById_found() {
        Product p = new Product("A", "d", BigDecimal.valueOf(1), 1);
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));

        ProductResponse resp = productService.getProductById(1L);

        assertThat(resp.name()).isEqualTo("A");
    }

    @Test
    void getProductById_notFound_throws() {
        when(productRepository.findById(42L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(42L));
    }

    @Test
    void updateProduct_updatesAndReturns() {
        Product existing = new Product("Old", "d", BigDecimal.valueOf(5), 5);
        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UpdateProductRequest req = new UpdateProductRequest(BigDecimal.valueOf(7.5), 8);
        ProductResponse resp = productService.updateProduct(1L, req);

        assertThat(resp.price()).isEqualByComparingTo(BigDecimal.valueOf(7.5));
        assertThat(resp.stockQuantity()).isEqualTo(8);
        verify(productRepository).save(existing);
    }

    @Test
    void deleteProduct_deletesWhenFound() {
        Product existing = new Product("X", "d", BigDecimal.valueOf(3), 3);
        when(productRepository.findById(9L)).thenReturn(Optional.of(existing));

        productService.deleteProduct(9L);

        verify(productRepository).delete(existing);
    }
}
