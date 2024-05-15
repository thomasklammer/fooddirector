package edu.mci.fooddirector.model.services;

import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
    }

    public List<Order> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findByMonthAndYear(int month, int year) {
        return orderRepository.findByMonthAndYear(month, year);
    }

    public void saveOrder(Order order) {
        try {
            orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save order", e);
        }
    }

    @Transactional
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

}

