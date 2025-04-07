package com.maxeriksson.iths.Webshop.controller;

import com.maxeriksson.iths.Webshop.domain.order.Order;
import com.maxeriksson.iths.Webshop.domain.order.OrderLine;
import com.maxeriksson.iths.Webshop.domain.product.Product;
import com.maxeriksson.iths.Webshop.service.ProductService;
import com.maxeriksson.iths.Webshop.service.ShoppingBasketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/shopping-basket")
public class ShoppingBasketController {

    @Autowired private ShoppingBasketService shoppingBasket;
    @Autowired private ProductService productService;

    private String viewPackage = "order/";
    private String shoppingBasketView = viewPackage + "shopping_basket";
    private String orderConfirmationView = viewPackage + "checkout_confirmation";

    @GetMapping
    public ModelAndView getShoppingBasketView(ModelMap model) {
        String view = shoppingBasketView;
        addDefaultAttributes(model);

        return new ModelAndView(view, model);
    }

    @PostMapping("/add")
    public ModelAndView addProduct(
            ModelMap model,
            @RequestParam("product_id") String productId,
            @RequestParam int quantity) {
        Optional<Product> product = productService.getProduct(productId);
        if (product.isPresent()) {
            shoppingBasket.addProduct(new OrderLine(product.get(), quantity));
        }

        String view = "redirect:" + this.getClass().getAnnotation(RequestMapping.class).value()[0];
        addDefaultAttributes(model);

        return new ModelAndView(view, model);
    }

    @PostMapping("/remove")
    public ModelAndView removeProduct(
            ModelMap model, @RequestParam("product_id") String productId) {
        Optional<Product> product = productService.getProduct(productId);
        if (product.isPresent()) {
            shoppingBasket.removeProduct(product.get());
        }

        String view = "redirect:" + this.getClass().getAnnotation(RequestMapping.class).value()[0];
        addDefaultAttributes(model);

        return new ModelAndView(view, model);
    }

    @PostMapping("/checkout")
    public ModelAndView checkout(ModelMap model) {
        int totalPrice = shoppingBasket.getTotalPrice();
        Order order = shoppingBasket.checkout();

        String view = orderConfirmationView;
        model.addAttribute("customer", order.getCustomer().getUsername());
        model.addAttribute("order_date", order.getOrderDate().toLocalDate());
        model.addAttribute("products", order.getProducts());
        model.addAttribute("total_price", totalPrice);

        return new ModelAndView(view, model);
    }

    private void addDefaultAttributes(ModelMap model) {
        model.addAttribute("products", shoppingBasket.getAllProducts().values());
        model.addAttribute("total_price", shoppingBasket.getTotalPrice());

        model.addAttribute("categories", productService.getAllCategories());
    }
}
