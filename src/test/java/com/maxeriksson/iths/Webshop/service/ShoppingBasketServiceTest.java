package com.maxeriksson.iths.Webshop.service;

import com.maxeriksson.iths.Webshop.repository.order.OrderRepository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ShoppingBasketServiceTest {

    @Mock private OrderRepository orderRepository;

    @InjectMocks private static ShoppingBasketService service;
}
