package com.maxeriksson.iths.Webshop.service;

import com.maxeriksson.iths.Webshop.domain.order.Order;
import com.maxeriksson.iths.Webshop.domain.order.OrderLine;
import com.maxeriksson.iths.Webshop.domain.product.Product;
import com.maxeriksson.iths.Webshop.domain.user.User;
import com.maxeriksson.iths.Webshop.repository.order.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public int getTotalPrice() {
        int total = 0;
        for(OrderLine orderLine : products.values()) {
            total += orderLine.getProduct().getPrice() * orderLine.getQuantity();
        }

        return total;
    }

    public void checkout() {
        User customer =
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        List<OrderLine> orderLines = new ArrayList<>(products.values());
        products.clear();

        Order order = new Order(customer, LocalDateTime.now(), orderLines);
        orderRepository.save(order);
    }
}
