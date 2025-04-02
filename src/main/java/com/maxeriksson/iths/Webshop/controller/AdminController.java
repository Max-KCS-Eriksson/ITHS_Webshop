package com.maxeriksson.iths.Webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.maxeriksson.iths.Webshop.service.ProductService;
import com.maxeriksson.iths.Webshop.service.OrderService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private ProductService productService;
    @Autowired private OrderService orderService;

    private String viewPackage = "admin/";
    private String adminPanelView = viewPackage + "admin_index";

    @GetMapping
    public ModelAndView adminPanel() {
        return new ModelAndView(adminPanelView);
    }
}
