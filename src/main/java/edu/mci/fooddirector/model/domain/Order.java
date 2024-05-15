package edu.mci.fooddirector.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.mci.fooddirector.model.enums.OrderStatus;
import edu.mci.fooddirector.model.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="tbOrders")
public class Order extends AbstractEntity {

    @NotNull
    @Column(name="orderdate")
    private LocalDateTime orderDate;

    @NotNull
    @Column(name="paymentmethod")
    private PaymentMethod paymentMethod;

    @NotNull
    @Column(name="orderstatus")
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "deliveryaddress_id")
    @NotNull
    private Address deliveryAddress;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @JsonIgnore
    public double getOrderValue() {
        double sum = 0;
        for (OrderDetail item : orderDetails) {
            sum += item.getTotalGrossValue();
        }
        return sum;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
