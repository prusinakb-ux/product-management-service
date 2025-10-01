package com.github.kprusina.feature.product.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductFilter {
  private String code;
  private String name;
  private BigDecimal minPriceEur;
  private BigDecimal maxPriceEur;
  private BigDecimal minPriceUsd;
  private BigDecimal maxPriceUsd;
  private Boolean isAvailable;
}
