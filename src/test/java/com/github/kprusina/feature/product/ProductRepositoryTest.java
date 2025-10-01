package com.github.kprusina.feature.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

  @Autowired private ProductRepository productRepository;

  @Test
  void testSaveAndFindProduct() {
    Product product = new Product();
    product.setName("Test Product");
    product.setCode("ABC123");
    product.setPriceEur(BigDecimal.valueOf(100));
    product.setPriceUsd(BigDecimal.valueOf(120));
    product.setIsAvailable(true);

    Product savedProduct = productRepository.save(product);

    Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());
    assertTrue(foundProduct.isPresent());
    assertEquals("ABC123", foundProduct.get().getCode());
    assertEquals("Test Product", foundProduct.get().getName());
  }
}
