package com.maxeriksson.iths.Webshop.service;

import com.maxeriksson.iths.Webshop.domain.order.OrderLine;
import com.maxeriksson.iths.Webshop.domain.product.Product;
import com.maxeriksson.iths.Webshop.repository.order.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

@Service
@SessionScope
public class ShoppingBasketService {

    @Autowired private OrderRepository orderRepository;

    private Map<Product, OrderLine> products = new HashMap<>();

    public Map<Product, OrderLine> getAllProducts() {
        return products;
    }

    public void addProduct(OrderLine orderLine) {
        products.put(orderLine.getProduct(), orderLine);
    }
}
