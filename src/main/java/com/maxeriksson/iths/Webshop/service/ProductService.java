package com.maxeriksson.iths.Webshop.service;

import com.maxeriksson.iths.Webshop.domain.product.Category;
import com.maxeriksson.iths.Webshop.domain.product.Product;
import com.maxeriksson.iths.Webshop.repository.product.CategoryRepository;
import com.maxeriksson.iths.Webshop.repository.product.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@ApplicationScope
public class ProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;

    public void add(Product product) {
        product.setForSale(true);
        save(product);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void add(Category category) {
        categoryRepository.save(category);
    }

    public List<Product> getProductsForSale(boolean isForSale) {
        return productRepository.findByIsForSale(isForSale);
    }

    public Optional<Product> getProduct(String name) {
        return productRepository.findById(name);
    }

    public List<Product> searchProductByName(String name) {
        List<Product> products = new ArrayList<>();
        name = name.toLowerCase();
        for (Product product : productRepository.findByNameContaining(name)) {
            products.add(product);
        }
        return products;
    }

    public List<Product> getProductsBy(Category category) {
        return productRepository.findByCategory(category);
    }

    public List<String> getAllCategoryNames() {
        List<String> categories = new ArrayList<>();
        for (Category category : getAllCategories()) {
            categories.add(category.getName());
        }
        return categories;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(String name) {
        return categoryRepository.findById(name);
    }

    public void remove(Product product) {
        product.setForSale(false);
        productRepository.save(product);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}
