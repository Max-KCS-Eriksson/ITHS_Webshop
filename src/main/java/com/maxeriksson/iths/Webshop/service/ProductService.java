package com.maxeriksson.iths.Webshop.service;

import com.maxeriksson.iths.Webshop.domain.product.Category;
import com.maxeriksson.iths.Webshop.domain.product.Product;
import com.maxeriksson.iths.Webshop.repository.product.CategoryRepository;
import com.maxeriksson.iths.Webshop.repository.product.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Service
@ApplicationScope
public class ProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;

    public void add(Product product) {
        productRepository.save(product);
    }

    public void add(Category category) {
        categoryRepository.save(category);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}
