package edu.mci.fooddirector.model.services;

import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.domain.OrderDetail;
import edu.mci.fooddirector.model.repositories.OrderDetailRepository;
import edu.mci.fooddirector.model.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());

    public OrderService(OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository) {

        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
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

