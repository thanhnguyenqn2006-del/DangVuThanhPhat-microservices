package com.example.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ToppingRequest(
    @NotBlank(message = "Tên topping không được để trống")
    String name,

    @NotNull(message = "Giá topping không được để trống")
    @Positive(message = "Giá topping phải lớn hơn 0")
    BigDecimal price,

    String status
) {}
