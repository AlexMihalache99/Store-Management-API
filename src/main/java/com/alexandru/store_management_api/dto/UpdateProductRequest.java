package com.alexandru.store_management_api.dto;

import java.math.BigDecimal;

public record UpdateProductRequest(
    BigDecimal price,
    Integer stockQuantity
) {}
