package edu.mci.fooddirector.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }
    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @JsonIgnore
    public double getPrice() {
        double sum = 0;

        for (var cartItem : cartItems) {
            sum += cartItem.getAmount() * cartItem.getArticle().getGrossPriceDiscounted();
        }

        return sum;
    }

    @JsonIgnore
    public double getDeliveryCosts() {
        return 3;
    }

    @JsonIgnore
    public double getTotalPrice() {
        return getPrice() + getDeliveryCosts();
    }

    @JsonIgnore
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

}
