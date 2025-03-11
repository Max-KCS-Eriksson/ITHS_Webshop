package com.maxeriksson.iths.Webshop.controller;

import com.maxeriksson.iths.Webshop.domain.product.Category;
import com.maxeriksson.iths.Webshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired private ProductService service;

    private String viewPackage = "product/";
    private String productListView = viewPackage + "product_list";

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("categories", service.getAllCategories());
        model.addAttribute("products", service.getAllProducts());

        return productListView;
    }

    @GetMapping("/category/{id}")
    public String getProductsByCategory(Model model, @PathVariable("id") String id) {
        model.addAttribute("categories", service.getAllCategories());
        Optional<Category> category = service.getCategory(id);
        if (category.isPresent()) {
            model.addAttribute("products", service.getProductsBy(category.get()));
        }

        return productListView;
    }
}
