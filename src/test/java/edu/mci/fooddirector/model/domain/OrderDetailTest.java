package edu.mci.fooddirector.model.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderDetailTest {

    private OrderDetail orderDetail;
    private Order order;
    private Article article;

    @BeforeEach
    void setUp() {
        orderDetail = new OrderDetail();
        order = mock(Order.class);
        article = mock(Article.class);
    }

    @Test
    void getOrder() {
        orderDetail.setOrder(order);
        assertEquals(order, orderDetail.getOrder());
    }

    @Test
    void setOrder() {
        orderDetail.setOrder(order);
        assertEquals(order, orderDetail.getOrder());
    }

    @Test
    void getArticle() {
        orderDetail.setArticle(article);
        assertEquals(article, orderDetail.getArticle());
    }

    @Test
    void setArticle() {
        orderDetail.setArticle(article);
        assertEquals(article, orderDetail.getArticle());
    }

    @Test
    void getNetValue() {
        orderDetail.setNetValue(100.0);
        assertEquals(100.0, orderDetail.getNetValue());
    }

    @Test
    void setNetValue() {
        orderDetail.setNetValue(100.0);
        assertEquals(100.0, orderDetail.getNetValue());
    }

    @Test
    void getTaxRate() {
        orderDetail.setTaxRate(20.0);
        assertEquals(20.0, orderDetail.getTaxRate());
    }

    @Test
    void setTaxRate() {
        orderDetail.setTaxRate(20.0);
        assertEquals(20.0, orderDetail.getTaxRate());
    }

    @Test
    void getAmount() {
        orderDetail.setAmount(5);
        assertEquals(5, orderDetail.getAmount());
    }

    @Test
    void setAmount() {
        orderDetail.setAmount(5);
        assertEquals(5, orderDetail.getAmount());
    }

    @Test
    void getNote() {
        orderDetail.setNote("Test note");
        assertEquals("Test note", orderDetail.getNote());
    }

    @Test
    void setNote() {
        orderDetail.setNote("Test note");
        assertEquals("Test note", orderDetail.getNote());
    }

    @Test
    void getTax() {
        orderDetail.setNetValue(100.0);
        orderDetail.setTaxRate(20.0);
        assertEquals(20.0, orderDetail.getTax(), 0.01);
    }

    @Test
    void getGrossValue() {
        orderDetail.setNetValue(100.0);
        orderDetail.setTaxRate(20.0);
        assertEquals(120.0, orderDetail.getGrossValue(), 0.01);
    }

    @Test
    void getTotalGrossValue() {
        orderDetail.setNetValue(100.0);
        orderDetail.setTaxRate(20.0);
        orderDetail.setAmount(5);
        assertEquals(600.0, orderDetail.getTotalGrossValue(), 0.01);
    }
}
