package com.maxeriksson.iths.Webshop.controller.product;

import com.maxeriksson.iths.Webshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired private ProductService productService;

    // Create operations

    // Read operations

    // Update operations

    // Delete operations
}
