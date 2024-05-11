package edu.mci.fooddirector.services;

import edu.mci.fooddirector.data.repositories.OrderDetailRepository;
import edu.mci.fooddirector.data.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository) {

        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }
}
