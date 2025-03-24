package com.maxeriksson.iths.Webshop.repository.order;

import com.maxeriksson.iths.Webshop.domain.order.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {}
