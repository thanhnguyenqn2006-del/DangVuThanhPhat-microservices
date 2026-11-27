package com.example.productservice.dto;

import java.math.BigDecimal;

public record ToppingResponse(
    Long id,
    String name,
    BigDecimal price,
    String status
) {}
