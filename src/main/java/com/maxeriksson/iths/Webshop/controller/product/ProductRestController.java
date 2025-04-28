package com.maxeriksson.iths.Webshop.controller.product;

import com.maxeriksson.iths.Webshop.domain.product.Category;
import com.maxeriksson.iths.Webshop.domain.product.Product;
import com.maxeriksson.iths.Webshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired private ProductService productService;

    // Create operations

    @PostMapping
    public ResponseEntity<Product> addNewProduct(@RequestBody Product product) {
        Category category = product.getCategory();
        productService.add(category);
        productService.add(product);
        return ResponseEntity.accepted().body(product);
    }

    // Read operations

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getProductsForSale(true);
        return ResponseEntity.ok().body(products);
    }

    // Update operations

    // Delete operations
}
