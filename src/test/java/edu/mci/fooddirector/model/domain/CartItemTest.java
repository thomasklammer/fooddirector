package edu.mci.fooddirector.model.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartItemTest {

    private CartItem cartItem;
    private Article article;

    @BeforeEach
    void setUp() {
        cartItem = new CartItem();
        article = mock(Article.class);
    }

    @Test
    void getArticle() {
        cartItem.setArticle(article);
        assertEquals(article, cartItem.getArticle());
    }

    @Test
    void setArticle() {
        cartItem.setArticle(article);
        assertEquals(article, cartItem.getArticle());
    }

    @Test
    void getAmount() {
        cartItem.setAmount(5);
        assertEquals(5, cartItem.getAmount());
    }

    @Test
    void setAmount() {
        cartItem.setAmount(5);
        assertEquals(5, cartItem.getAmount());
    }
}
