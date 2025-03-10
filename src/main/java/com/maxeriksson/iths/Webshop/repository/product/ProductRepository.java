package com.maxeriksson.iths.Webshop.repository.product;

import com.maxeriksson.iths.Webshop.domain.product.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {}
