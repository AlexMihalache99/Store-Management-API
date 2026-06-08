package com.alexandru.store_management_api.dto;

import java.math.BigDecimal;

public record CreateProductRequest(
    String name,
    String description,
    BigDecimal price,
    Integer stockQuantity
) {}