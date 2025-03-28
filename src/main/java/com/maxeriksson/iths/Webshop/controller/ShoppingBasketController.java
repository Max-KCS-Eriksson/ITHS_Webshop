package com.maxeriksson.iths.Webshop.controller;

import com.maxeriksson.iths.Webshop.service.ShoppingBasketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopping-basket")
public class ShoppingBasketController {

    @Autowired private ShoppingBasketService service;
}
