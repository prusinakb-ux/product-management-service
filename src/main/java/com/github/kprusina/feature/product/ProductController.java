package com.github.kprusina.feature.product;

import com.github.kprusina.feature.product.projection.ProductDto;
import com.github.kprusina.feature.product.request.ProductFilter;
import com.github.kprusina.feature.product.request.ProductRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @Operation(summary = "Fetch list of products.")
  @ApiResponse(responseCode = "200", description = "Returns list of all products.")
  @GetMapping
  public Page<ProductDto> getProductById(
      @ModelAttribute ProductFilter filter,
      @PageableDefault(size = 20, sort = "name") Pageable pageable) {
    return productService.getProducts(filter, pageable);
  }

  @Operation(summary = "Get product by id.")
  @ApiResponse(responseCode = "200", description = "Returns product by id.")
  @GetMapping("/{id}")
  public ProductDto getProductById(@PathVariable Long id) {
    return productService.findById(id);
  }

  @Operation(summary = "Save product.")
  @ApiResponse(responseCode = "200", description = "Returns new product dto.")
  @PostMapping
  public ProductDto createProduct(@Valid @RequestBody ProductRequest productRequest) {
    return productService.createProduct(productRequest);
  }
}
