package edu.mci.fooddirector.model.services;

import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.repositories.OrderDetailRepository;
import edu.mci.fooddirector.model.repositories.OrderRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository) {

        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> findByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
