package com.alexandru.store_management_api.controller;

import com.alexandru.store_management_api.dto.CreateProductRequest;
import com.alexandru.store_management_api.dto.ProductResponse;
import com.alexandru.store_management_api.dto.UpdateProductRequest;
import com.alexandru.store_management_api.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController controller;

    @Test
    void create_delegates() {
        CreateProductRequest req = new CreateProductRequest("n","d", BigDecimal.ONE, 1);
        ProductResponse resp = new ProductResponse(null, "n","d", BigDecimal.ONE,1);
        when(productService.createProduct(req)).thenReturn(resp);

        var out = controller.create(req);
        assertThat(out).isSameAs(resp);
        verify(productService).createProduct(req);
    }

    @Test
    void getById_delegates() {
        ProductResponse resp = new ProductResponse(1L,"n","d", BigDecimal.ONE,1);
        when(productService.getProductById(1L)).thenReturn(resp);
        assertThat(controller.getById(1L)).isSameAs(resp);
    }

    @Test
    void getAll_delegates() {
        when(productService.getAllProducts()).thenReturn(List.of());
        assertThat(controller.getAll()).isEmpty();
        verify(productService).getAllProducts();
    }

    @Test
    void update_delegates() {
        UpdateProductRequest req = new UpdateProductRequest(BigDecimal.TEN, 5);
        ProductResponse resp = new ProductResponse(1L,"n","d", BigDecimal.TEN,5);
        when(productService.updateProduct(1L, req)).thenReturn(resp);
        assertThat(controller.update(1L, req)).isSameAs(resp);
    }

    @Test
    void delete_delegates() {
        doNothing().when(productService).deleteProduct(4L);
        controller.deleteProduct(4L);
        verify(productService).deleteProduct(4L);
    }
}
