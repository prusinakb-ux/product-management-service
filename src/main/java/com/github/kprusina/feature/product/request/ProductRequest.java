package com.github.kprusina.feature.product.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductRequest {

  @NotBlank private String name;

  @DecimalMin("0.0")
  @NotNull
  private BigDecimal priceEur;

  private Boolean isAvailable = true;
}
