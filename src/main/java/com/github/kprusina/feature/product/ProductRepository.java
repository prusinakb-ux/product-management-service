package com.github.kprusina.feature.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepository
    extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {}
