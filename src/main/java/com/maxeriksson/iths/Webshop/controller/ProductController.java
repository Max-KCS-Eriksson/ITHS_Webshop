package com.maxeriksson.iths.Webshop.controller;

import com.maxeriksson.iths.Webshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
