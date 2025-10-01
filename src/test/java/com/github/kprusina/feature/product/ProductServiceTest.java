package com.github.kprusina.feature.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.kprusina.feature.currency.CurrencyService;
import com.github.kprusina.feature.product.mapper.ProductMapper;
import com.github.kprusina.feature.product.projection.ProductDto;
import com.github.kprusina.feature.product.request.ProductRequest;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock private CurrencyService currencyService;

  @Mock private ProductResourceService productResourceService;

  @Mock private ProductMapper mapper;

  @InjectMocks private ProductService productService;

  private ProductRequest request;
  private Product productEntity;
  private Product savedProduct;
  private ProductDto productDto;

  @BeforeEach
  void setup() {

    request = new ProductRequest();
    request.setName("Test Product");
    request.setPriceEur(BigDecimal.valueOf(100));

    savedProduct = new Product();
    savedProduct.setId(1L);
    savedProduct.setName("Test Product");
    savedProduct.setCode("ABC123");
    savedProduct.setPriceEur(BigDecimal.valueOf(100));
    savedProduct.setPriceUsd(BigDecimal.valueOf(120));
    savedProduct.setIsAvailable(true);

    productDto =
        new ProductDto(
            1L, "ABC123", "Test Product", BigDecimal.valueOf(100), BigDecimal.valueOf(120), true);
  }

  private void mockCreateProduct() {
    productEntity = new Product();
    productEntity.setName("Test Product");
    productEntity.setPriceEur(BigDecimal.valueOf(117.23));

    when(mapper.toEntity(any(ProductRequest.class))).thenReturn(productEntity);
    when(currencyService.convertEurToCurrency(any(BigDecimal.class), any()))
        .thenReturn(BigDecimal.valueOf(120));
    when(productResourceService.save(any(Product.class))).thenReturn(savedProduct);
    when(mapper.toDto(savedProduct)).thenReturn(productDto);
  }

  private void mockFindById() {
    when(productResourceService.findById(1L)).thenReturn(savedProduct);
    when(mapper.toDto(savedProduct)).thenReturn(productDto);
  }

  @Test
  void testCreateProduct() {
    mockCreateProduct();
    ProductDto created = productService.createProduct(request);

    assertEquals(1L, created.getId());
    assertEquals("ABC123", created.getCode());
    assertEquals("Test Product", created.getName());
  }

  @Test
  void testFindById() {
    mockFindById();
    ProductDto found = productService.findById(1L);

    assertEquals(1L, found.getId());
    assertEquals("ABC123", found.getCode());
    assertEquals("Test Product", found.getName());
  }
}
