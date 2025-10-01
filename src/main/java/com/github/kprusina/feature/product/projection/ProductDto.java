package com.github.kprusina.feature.product.projection;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

  private Long id;
  private String code;
  private String name;
  private BigDecimal priceEur;
  private BigDecimal priceUsd;
  private Boolean isAvailable;
}
