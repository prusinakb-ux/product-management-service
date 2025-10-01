package com.github.kprusina.feature.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kprusina.feature.product.projection.ProductDto;
import com.github.kprusina.feature.product.request.ProductFilter;
import java.math.BigDecimal;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

  private MockMvc mockMvc;

  @Mock private ProductService productService;

  @InjectMocks private ProductController productController;

  @BeforeEach
  void setup() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(productController)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(new ObjectMapper()))
            // Add this to correctly handle Pageable arguments
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();
  }

  @Test
  void testGetProductsEndpoint() throws Exception {
    // Create a proper PageImpl with content, pageable, and total
    Page<ProductDto> emptyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);

    when(productService.getProducts(any(ProductFilter.class), any(Pageable.class)))
        .thenReturn(emptyPage);

    mockMvc
        .perform(get("/products").param("page", "0").param("size", "10"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetProductByIdEndpoint() throws Exception {
    ProductDto productDto = new ProductDto();
    productDto.setId(1L);
    productDto.setName("Test Product");
    productDto.setCode("ABC123");
    productDto.setPriceEur(BigDecimal.valueOf(100));
    productDto.setPriceUsd(BigDecimal.valueOf(120));
    productDto.setIsAvailable(true);

    when(productService.findById(1L)).thenReturn(productDto);

    mockMvc
        .perform(get("/products/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }
}
