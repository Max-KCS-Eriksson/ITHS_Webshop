package com.maxeriksson.iths.Webshop.controller.product;

import com.maxeriksson.iths.Webshop.domain.product.Category;
import com.maxeriksson.iths.Webshop.domain.product.Product;
import com.maxeriksson.iths.Webshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = productService.getAllCategories();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(
            @PathVariable("category") String categoryName) {
        Optional<Category> category = productService.getCategory(categoryName);
        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Product> products = productService.getProductsBy(category.get());
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String name) {
        if (name.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        List<Product> products = productService.searchProductByName(name);
        return ResponseEntity.ok().body(products);
    }

    // Update operations

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id, @RequestBody Map<String, String> requestBody) {
        Optional<Product> optional = productService.getProduct(id);
        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Product product = optional.get();

        Optional<ResponseEntity<Product>> setPriceResponse =
                setRequestBodyPriceIfPresent(requestBody, product);
        boolean failedSetPrice = setPriceResponse.isPresent();
        if (failedSetPrice) return setPriceResponse.get();

        Optional<ResponseEntity<Product>> setCategoryResponse =
                setRequestBodyCategoryIfPresent(requestBody, product);
        boolean failedSetCategory = setCategoryResponse.isPresent();
        if (failedSetCategory) return setCategoryResponse.get();

        setRequestBodyForSaleIfPresent(requestBody, product);

        productService.save(product);
        return ResponseEntity.ok().body(product);
    }

    // Delete operations

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        Optional<Product> optional = productService.getProduct(id);
        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Product product = optional.get();

        boolean success = productService.delete(product);
        if (!success) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.ok().body(product);
    }

    // Helpers

    private Optional<ResponseEntity<Product>> setRequestBodyPriceIfPresent(
            Map<String, String> requestBody, Product product) {
        String priceField = requestBody.get("price");
        if (priceField != null) {
            try {
                product.setPrice(Integer.parseInt(priceField));
            } catch (NumberFormatException e) {
                return Optional.of(ResponseEntity.badRequest().build());
            }
        }
        return Optional.empty();
    }

    private Optional<ResponseEntity<Product>> setRequestBodyCategoryIfPresent(
            Map<String, String> requestBody, Product product) {
        String categoryName = requestBody.get("category");
        if (categoryName != null) {
            Optional<Category> category = productService.getCategory(categoryName);
            if (category.isEmpty()) {
                return Optional.of(ResponseEntity.badRequest().build());

            } else product.setCategory(category.get());
        }
        return Optional.empty();
    }

    private void setRequestBodyForSaleIfPresent(Map<String, String> requestBody, Product product) {
        String isForSale = requestBody.get("isForSale");
        try {
            product.setForSale(Integer.parseInt(isForSale) > 0);
        } catch (NumberFormatException e) {
            if (isForSale != null) {
                product.setForSale(Boolean.parseBoolean(isForSale));
            }
        }
    }
}
