package edu.mci.fooddirector.model.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.page.WebStorage;
import edu.mci.fooddirector.model.callbacks.CartCallback;
import edu.mci.fooddirector.model.domain.Article;
import edu.mci.fooddirector.model.domain.Cart;
import edu.mci.fooddirector.model.domain.CartItem;
import org.springframework.stereotype.Service;


@Service
public class CartService {

    private final String cartKey = "cart";

    public void clearCart() {
        WebStorage.removeItem(WebStorage.Storage.SESSION_STORAGE, cartKey);
    }

    public void getCart(CartCallback cartCallback) {
        WebStorage.getItem(WebStorage.Storage.SESSION_STORAGE, cartKey, value -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Cart cart = mapper.readValue(value, Cart.class);
                cartCallback.onCartLoaded(cart);
            } catch (Exception e) {
                clearCart();
                cartCallback.onCartLoaded(new Cart());
            }

        });

    }

    public void setCart(Cart cart) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String json = mapper.writeValueAsString(cart);
            WebStorage.setItem(WebStorage.Storage.SESSION_STORAGE, cartKey, json);
        } catch (Exception ex) {
            System.out.println("error while setting cart to storage: " + ex.getMessage());
            //shit
        }
    }


    public void addCartItem(Article article, int amount) {
        getCart(cart -> {
            var cartItem = new CartItem();
            cartItem.setArticle(article);
            cartItem.setAmount(amount);

            cart.addCartItem(cartItem);
            setCart(cart);
        });
    }
}
