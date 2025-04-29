package com.maxeriksson.iths.Webshop.controller.product;

import com.maxeriksson.iths.Webshop.domain.product.Category;
import com.maxeriksson.iths.Webshop.domain.product.Product;
import com.maxeriksson.iths.Webshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/category")
    public ResponseEntity<?> getProductsByCategory(@RequestBody Map<String, String> requestBody) {
        String categoryName = requestBody.get("category");
        if (categoryName == null || categoryName.isBlank()) {
            return ResponseEntity.badRequest().body("A \"category\" value must be given");
        }

        Optional<Category> category = productService.getCategory(categoryName);
        if (category.isEmpty()) {
            List<String> categories = productService.getAllCategoryNames();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Not such Category exists\nAvailable categories:" + categories);
        }

        List<Product> products = productService.getProductsBy(category.get());
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProductsByName(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().body("A \"name\" value must be given");
        }

        List<Product> products = productService.searchProductByName(name);
        return ResponseEntity.ok().body(products);
    }

    // Update operations

    // Delete operations

    // Helpers

    private Optional<ResponseEntity<String>> setRequestBodyPriceIfPresent(
            Map<String, String> requestBody, Product product) {
        String priceField = requestBody.get("price");
        if (priceField != null) {
            try {
                product.setPrice(Integer.parseInt(priceField));
            } catch (NumberFormatException e) {
                return Optional.of(
                        ResponseEntity.badRequest().body("Value of \"price\" must be an integer"));
            }
        }
        return Optional.empty();
    }
}
