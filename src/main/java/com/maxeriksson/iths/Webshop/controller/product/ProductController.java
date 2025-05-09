package com.maxeriksson.iths.Webshop.controller.product;

import com.maxeriksson.iths.Webshop.domain.product.Category;
import com.maxeriksson.iths.Webshop.domain.product.Product;
import com.maxeriksson.iths.Webshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired private ProductService service;

    private String viewPackage = "product/";
    private String productListView = viewPackage + "product_list";
    private String productDetailViewView = viewPackage + "product_detail";

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("categories", service.getAllCategories());
        model.addAttribute("products", service.getProductsForSale(true));

        return productListView;
    }

    @PostMapping
    public ModelAndView searchProductByName(
            ModelMap model, @RequestParam("product_name") String name) {
        model.addAttribute("categories", service.getAllCategories());

        String viewName = productListView;
        List<Product> results = service.searchProductByName(name);
        if (results.size() == 1) {
            String baseUrlPath = this.getClass().getAnnotation(RequestMapping.class).value()[0];
            viewName = "redirect:" + baseUrlPath + "/" + results.get(0).getName();
        } else {
            model.addAttribute("products", results);
        }

        return new ModelAndView(viewName, model);
    }

    @GetMapping("/category/{id}")
    public String getProductsByCategory(Model model, @PathVariable("id") String id) {
        model.addAttribute("categories", service.getAllCategories());
        Optional<Category> category = service.getCategory(id);
        if (category.isPresent()) {
            model.addAttribute("products", service.getProductsBy(category.get()));
        } else {
            return viewPackage + "category_not_found";
        }

        return productListView;
    }

    @GetMapping("/{id}")
    public String getProductDetailView(Model model, @PathVariable("id") String id) {
        model.addAttribute("categories", service.getAllCategories());
        Optional<Product> product = service.getProduct(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
        } else {
            return viewPackage + "product_not_found";
        }

        return productDetailViewView;
    }
}
