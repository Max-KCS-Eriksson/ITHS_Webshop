package com.maxeriksson.iths.Webshop.controller;

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

    private String viewPackage = "order/";
    private String shoppingBasketView = viewPackage + "shopping_basket";
}
