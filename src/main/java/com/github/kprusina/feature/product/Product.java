package com.github.kprusina.feature.product;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Product")
@Getter
@Setter
@ToString
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "code", length = 10, nullable = false, unique = true)
  private String code;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "price_eur", nullable = false, precision = 12, scale = 2)
  private BigDecimal priceEur;

  @Column(name = "price_usd", nullable = false, precision = 12, scale = 2)
  private BigDecimal priceUsd;

  private Boolean isAvailable;
}
