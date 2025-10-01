package com.github.kprusina.feature.product;

import com.github.kprusina.exception.SoftException;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductResourceService {

  private final ProductRepository productRepository;

  public Product save(Product product) {
    return productRepository.save(product);
  }

  @Transactional(readOnly = true)
  public Page<Product> findAll(Predicate predicate, Pageable pageable) {
    return productRepository.findAll(predicate, pageable);
  }

  @Transactional(readOnly = true)
  public Product findById(Long id) {
    return productRepository.findById(id).orElseThrow(() -> new SoftException("product.notFound"));
  }
}
