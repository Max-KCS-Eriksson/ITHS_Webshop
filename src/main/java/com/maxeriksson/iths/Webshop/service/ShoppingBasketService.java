package com.maxeriksson.iths.Webshop.service;

import com.maxeriksson.iths.Webshop.domain.order.OrderLine;
import com.maxeriksson.iths.Webshop.repository.order.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Service
@SessionScope
public class ShoppingBasketService {

    @Autowired private OrderRepository orderRepository;

    private List<OrderLine> products;
}
