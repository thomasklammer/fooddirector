package edu.mci.fooddirector.model.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartTest {

    private Cart cart;
    private CartItem cartItem1;
    private CartItem cartItem2;
    private Article article1;
    private Article article2;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cartItem1 = mock(CartItem.class);
        cartItem2 = mock(CartItem.class);
        article1 = mock(Article.class);
        article2 = mock(Article.class);

        when(cartItem1.getAmount()).thenReturn(2);
        when(cartItem1.getArticle()).thenReturn(article1);
        when(article1.getGrossPriceDiscounted()).thenReturn(50.0);

        when(cartItem2.getAmount()).thenReturn(3);
        when(cartItem2.getArticle()).thenReturn(article2);
        when(article2.getGrossPriceDiscounted()).thenReturn(30.0);
    }

    @Test
    void addCartItem() {
        cart.addCartItem(cartItem1);
        assertTrue(cart.getCartItems().contains(cartItem1));
    }

    @Test
    void removeCartItem() {
        cart.addCartItem(cartItem1);
        cart.removeCartItem(cartItem1);
        assertFalse(cart.getCartItems().contains(cartItem1));
    }

    @Test
    void getCartItems() {
        cart.addCartItem(cartItem1);
        cart.addCartItem(cartItem2);
        assertEquals(2, cart.getCartItems().size());
        assertTrue(cart.getCartItems().contains(cartItem1));
        assertTrue(cart.getCartItems().contains(cartItem2));
    }

    @Test
    void getPrice() {
        cart.addCartItem(cartItem1);
        cart.addCartItem(cartItem2);
        assertEquals(2 * 50.0 + 3 * 30.0, cart.getPrice(), 0.01);
    }

    @Test
    void getDeliveryCosts() {
        assertEquals(3.0, cart.getDeliveryCosts(), 0.01);
    }

    @Test
    void getTotalPrice() {
        cart.addCartItem(cartItem1);
        cart.addCartItem(cartItem2);
        assertEquals(2 * 50.0 + 3 * 30.0 + 3.0, cart.getTotalPrice(), 0.01);
    }

    @Test
    void isEmpty() {
        assertTrue(cart.isEmpty());
        cart.addCartItem(cartItem1);
        assertFalse(cart.isEmpty());
    }
}
