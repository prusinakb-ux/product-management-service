package com.github.kprusina.feature.product;

import com.github.kprusina.enumeration.CurrencyCode;
import com.github.kprusina.feature.currency.CurrencyService;
import com.github.kprusina.feature.product.mapper.ProductMapper;
import com.github.kprusina.feature.product.projection.ProductDto;
import com.github.kprusina.feature.product.request.ProductFilter;
import com.github.kprusina.feature.product.request.ProductRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.NEVER)
public class ProductService {
  private final CurrencyService currencyService;
  private final ProductResourceService productResourceService;
  private final ProductMapper mapper;

  public Page<ProductDto> getProducts(ProductFilter filter, Pageable pageable) {
    QProduct product = QProduct.product;
    Predicate predicate = buildPredicate(filter, product);

    return productResourceService.findAll(predicate, pageable).map(mapper::toDto);
  }

  public ProductDto createProduct(ProductRequest request) {
    Product product = mapper.toEntity(request);
    product.setCode(generateUniqueCode());
    product.setPriceUsd(
        currencyService.convertEurToCurrency(request.getPriceEur(), CurrencyCode.USD.label));

    return mapper.toDto(productResourceService.save(product));
  }

  @Transactional(readOnly = true)
  public ProductDto findById(Long id) {
    return mapper.toDto(productResourceService.findById(id));
  }

  private Predicate buildPredicate(ProductFilter filter, QProduct product) {
    BooleanBuilder builder = new BooleanBuilder();

    if (filter.getCode() != null) builder.and(product.code.containsIgnoreCase(filter.getCode()));
    if (filter.getName() != null) builder.and(product.name.containsIgnoreCase(filter.getName()));
    if (filter.getMinPriceEur() != null) builder.and(product.priceEur.goe(filter.getMinPriceEur()));
    if (filter.getMaxPriceEur() != null) builder.and(product.priceEur.loe(filter.getMaxPriceEur()));
    if (filter.getIsAvailable() != null)
      builder.and(product.isAvailable.eq(filter.getIsAvailable()));

    return builder;
  }

  private String generateUniqueCode() {
    return UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
  }
}
