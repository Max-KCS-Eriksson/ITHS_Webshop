package com.maxeriksson.iths.Webshop.service;

import com.maxeriksson.iths.Webshop.domain.order.Order;
import com.maxeriksson.iths.Webshop.repository.order.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Service
@ApplicationScope
public class OrderService {

    @Autowired OrderRepository orderRepository;

    public List<Order> getAllOrdersExpedited(boolean expedited) {
        return orderRepository.findByExpedited(expedited);
    }

    public Order markOrderAsExpedited(Order order) {
        order.setExpedited(true);

        return orderRepository.save(order);
    }

    public Order getOrder(int id) {
        return orderRepository.findById(id).get();
    }
}
