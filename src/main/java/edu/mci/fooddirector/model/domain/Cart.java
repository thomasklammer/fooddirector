package edu.mci.fooddirector.model.domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<CartItem>();
    }

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}
