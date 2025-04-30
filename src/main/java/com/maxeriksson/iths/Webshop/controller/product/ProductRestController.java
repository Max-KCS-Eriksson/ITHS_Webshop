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

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String id, @RequestBody Map<String, String> requestBody) {
        Optional<Product> optional = productService.getProduct(id);
        if (optional.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Cannot update nonexisting Product \"" + id + "\"");
        }
        Product product = optional.get();

        Optional<ResponseEntity<String>> setPriceResponse =
                setRequestBodyPriceIfPresent(requestBody, product);
        boolean failedSetPrice = setPriceResponse.isPresent();
        if (failedSetPrice) return setPriceResponse.get();

        Optional<ResponseEntity<String>> setCategoryResponse =
                setRequestBodyCategoryIfPresent(requestBody, product);
        boolean failedSetCategory = setCategoryResponse.isPresent();
        if (failedSetCategory) return setCategoryResponse.get();

        setRequestBodyForSaleIfPresent(requestBody, product);

        productService.save(product);
        return ResponseEntity.ok().body(product);
    }

    // Delete operations

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        Optional<Product> optional = productService.getProduct(id);
        if (optional.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Cannot delete nonexisting Product \"" + id + "\"");
        }
        Product product = optional.get();

        boolean success = productService.delete(product);
        if (!success) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(
                            "Cannot delete Product \""
                                    + id
                                    + "\", likely due to it being referenced by other entities");
        }
        return ResponseEntity.ok().body(product);
    }

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

    private Optional<ResponseEntity<String>> setRequestBodyCategoryIfPresent(
            Map<String, String> requestBody, Product product) {
        String categoryName = requestBody.get("category");
        if (categoryName != null) {
            Optional<Category> category = productService.getCategory(categoryName);
            if (category.isEmpty()) {
                List<String> categories = productService.getAllCategoryNames();
                String body = "Not such Category exists\nAvailable categories:" + categories;
                return Optional.of(ResponseEntity.badRequest().body(body));

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
