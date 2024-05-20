package edu.mci.fooddirector.model.domain;

import edu.mci.fooddirector.model.enums.OrderStatus;
import edu.mci.fooddirector.model.enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderTest {

    private Order order;
    private User user;
    private Address address;
    private OrderDetail orderDetail1;
    private OrderDetail orderDetail2;

    @BeforeEach
    void setUp() {
        order = new Order();
        user = mock(User.class);
        address = mock(Address.class);
        orderDetail1 = mock(OrderDetail.class);
        orderDetail2 = mock(OrderDetail.class);
    }

    @Test
    void getOrderValue() {
        when(orderDetail1.getTotalGrossValue()).thenReturn(100.0);
        when(orderDetail2.getTotalGrossValue()).thenReturn(200.0);
        order.setOrderDetails(Arrays.asList(orderDetail1, orderDetail2));
        assertEquals(300.0, order.getOrderValue());
    }

    @Test
    void getOrderDate() {
        LocalDateTime now = LocalDateTime.now();
        order.setOrderDate(now);
        assertEquals(now, order.getOrderDate());
    }

    @Test
    void setOrderDate() {
        LocalDateTime now = LocalDateTime.now();
        order.setOrderDate(now);
        assertEquals(now, order.getOrderDate());
    }

    @Test
    void getPaymentMethod() {
        order.setPaymentMethod(PaymentMethod.CreditCard);
        assertEquals(PaymentMethod.CreditCard, order.getPaymentMethod());
    }

    @Test
    void setPaymentMethod() {
        order.setPaymentMethod(PaymentMethod.CreditCard);
        assertEquals(PaymentMethod.CreditCard, order.getPaymentMethod());
    }

    @Test
    void getOrderStatus() {
        order.setOrderStatus(OrderStatus.Delivered);
        assertEquals(OrderStatus.Delivered, order.getOrderStatus());
    }

    @Test
    void setOrderStatus() {
        order.setOrderStatus(OrderStatus.Delivered);
        assertEquals(OrderStatus.Delivered, order.getOrderStatus());
    }

    @Test
    void getDeliveryAddress() {
        order.setDeliveryAddress(address);
        assertEquals(address, order.getDeliveryAddress());
    }

    @Test
    void setDeliveryAddress() {
        order.setDeliveryAddress(address);
        assertEquals(address, order.getDeliveryAddress());
    }

    @Test
    void getOrderDetails() {
        List<OrderDetail> details = Arrays.asList(orderDetail1, orderDetail2);
        order.setOrderDetails(details);
        assertEquals(details, order.getOrderDetails());
    }

    @Test
    void setOrderDetails() {
        List<OrderDetail> details = Arrays.asList(orderDetail1, orderDetail2);
        order.setOrderDetails(details);
        assertEquals(details, order.getOrderDetails());
    }

    @Test
    void getUser() {
        order.setUser(user);
        assertEquals(user, order.getUser());
    }

    @Test
    void setUser() {
        order.setUser(user);
        assertEquals(user, order.getUser());
    }
}
