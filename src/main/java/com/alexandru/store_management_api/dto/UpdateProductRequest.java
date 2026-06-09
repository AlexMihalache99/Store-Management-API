package com.alexandru.store_management_api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductRequest(

    @Positive(message = "Product price must be greater than 0")
    BigDecimal price,

    @PositiveOrZero(message = "Stock quantity must not be negative")
    Integer stockQuantity
) {}
