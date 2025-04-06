package com.maxeriksson.iths.Webshop.controller;

import com.maxeriksson.iths.Webshop.domain.order.Order;
import com.maxeriksson.iths.Webshop.domain.product.Category;
import com.maxeriksson.iths.Webshop.domain.product.Product;
import com.maxeriksson.iths.Webshop.service.OrderService;
import com.maxeriksson.iths.Webshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private ProductService productService;
    @Autowired private OrderService orderService;

    private String viewPackage = "admin/";
    private String adminPanelView = viewPackage + "admin_panel";
    private String productsPanelView = viewPackage + "product_list";
    private String productDetailPanelView = viewPackage + "product_detail";
    private String ordersPanelView = viewPackage + "order_list";
    private String orderDetailPanelView = viewPackage + "order_detail";

    private String baseUrlPath = this.getClass().getAnnotation(RequestMapping.class).value()[0];

    @GetMapping
    public ModelAndView adminPanel() {
        return new ModelAndView(adminPanelView);
    }

    // Product mappings

    @GetMapping("/products")
    public ModelAndView productsPanel(ModelMap model) {
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("products", productService.getProductsForSale(true));

        String view = productsPanelView;

        return new ModelAndView(view, model);
    }

    @GetMapping("/products/{id}")
    public ModelAndView productDetailPanel(ModelMap model, @PathVariable String id) {
        model.addAttribute("categories", productService.getAllCategories());
        Optional<Product> product = productService.getProduct(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
        }

        String view = productDetailPanelView;

        return new ModelAndView(view, model);
    }

    @PostMapping("/products/delete")
    public ModelAndView deleteProduct(
            ModelMap model, @RequestParam("product_id") String productId) {
        Optional<Product> product = productService.getProduct(productId);
        if (product.isPresent()) {
            productService.remove(product.get());
        }

        String view = "redirect:" + baseUrlPath + "/products";

        return new ModelAndView(view);
    }

    @GetMapping("/products/category/{id}")
    public ModelAndView productsByCategoryPanel(ModelMap model, @PathVariable String id) {
        model.addAttribute("categories", productService.getAllCategories());
        Optional<Category> category = productService.getCategory(id);
        if (category.isPresent()) {
            model.addAttribute("products", productService.getProductsBy(category.get()));
        }

        String view = productsPanelView;

        return new ModelAndView(view, model);
    }

    // Order mappings

    @GetMapping("/orders")
    public ModelAndView ordersByExpeditedStatusPanel(
            ModelMap model, @RequestParam(required = false) boolean expedited) {
        model.addAttribute("expedited", expedited);
        model.addAttribute("orders", orderService.getAllOrdersExpedited(expedited));

        String view = ordersPanelView;

        return new ModelAndView(view, model);
    }

    @GetMapping("/orders/{id}")
    public ModelAndView orderDetailPanel(ModelMap model, @PathVariable int id) {
        Order order = orderService.getOrder(id);
        model.addAttribute("order", order);
        model.addAttribute("customer", order.getCustomer());

        String view = orderDetailPanelView;

        return new ModelAndView(view, model);
    }

    @PostMapping("/orders/expedite")
    public ModelAndView expediteOrder(ModelMap model, @RequestParam int id) {
        Order order = orderService.getOrder(id);
        orderService.markOrderAsExpedited(order);

        model.addAttribute("order", order);
        model.addAttribute("customer", order.getCustomer());

        String view = orderDetailPanelView;

        return new ModelAndView(view, model);
    }
}
