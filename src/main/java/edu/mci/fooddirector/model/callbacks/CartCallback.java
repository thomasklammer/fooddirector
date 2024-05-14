package edu.mci.fooddirector.model.callbacks;

import edu.mci.fooddirector.model.domain.Cart;

public interface CartCallback {
    void onCartLoaded(Cart cart);
}