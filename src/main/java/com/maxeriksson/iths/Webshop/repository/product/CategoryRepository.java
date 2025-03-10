package com.maxeriksson.iths.Webshop.repository.product;

import com.maxeriksson.iths.Webshop.domain.product.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {}
