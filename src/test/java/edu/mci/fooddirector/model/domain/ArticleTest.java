package edu.mci.fooddirector.model.domain;

import edu.mci.fooddirector.model.enums.ArticleCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    private Article article;

    @BeforeEach
    void setUp() {
        article = new Article();
    }

    @Test
    void getName() {
        article.setName("Test Article");
        assertEquals("Test Article", article.getName());
    }

    @Test
    void setName() {
        article.setName("Test Article");
        assertEquals("Test Article", article.getName());
    }

    @Test
    void getNetPrice() {
        article.setNetPrice(100.0);
        assertEquals(100.0, article.getNetPrice(), 0.01);
    }

    @Test
    void setNetPrice() {
        article.setNetPrice(100.0);
        assertEquals(100.0, article.getNetPrice(), 0.01);
    }

    @Test
    void getNetPriceDiscounted() {
        article.setNetPrice(100.0);
        article.setDiscount(10.0);
        assertEquals(90.0, article.getNetPriceDiscounted(), 0.01);
    }

    @Test
    void getGrossPriceDiscounted() {
        article.setNetPrice(100.0);
        article.setDiscount(10.0);
        article.setTaxRate(20.0);
        assertEquals(108.0, article.getGrossPriceDiscounted(), 0.01);
    }

    @Test
    void getTaxRate() {
        article.setTaxRate(20.0);
        assertEquals(20.0, article.getTaxRate(), 0.01);
    }

    @Test
    void setTaxRate() {
        article.setTaxRate(20.0);
        assertEquals(20.0, article.getTaxRate(), 0.01);
    }

    @Test
    void getImage() {
        article.setImage("image.png");
        assertEquals("image.png", article.getImage());
    }

    @Test
    void setImage() {
        article.setImage("image.png");
        assertEquals("image.png", article.getImage());
    }

    @Test
    void getDescription() {
        article.setDescription("This is a test article.");
        assertEquals("This is a test article.", article.getDescription());
    }

    @Test
    void setDescription() {
        article.setDescription("This is a test article.");
        assertEquals("This is a test article.", article.getDescription());
    }

    @Test
    void getArticleCategory() {
        article.setArticleCategory(ArticleCategory.Starter);
        assertEquals(ArticleCategory.Starter, article.getArticleCategory());
    }

    @Test
    void setArticleCategory() {
        article.setArticleCategory(ArticleCategory.Starter);
        assertEquals(ArticleCategory.Starter, article.getArticleCategory());
    }

    @Test
    void isDailyOffer() {
        article.setDailyOffer(true);
        assertTrue(article.isDailyOffer());
    }

    @Test
    void setDailyOffer() {
        article.setDailyOffer(true);
        assertTrue(article.isDailyOffer());
    }

    @Test
    void getDiscount() {
        article.setDiscount(15.0);
        assertEquals(15.0, article.getDiscount(), 0.01);
    }

    @Test
    void setDiscount() {
        article.setDiscount(15.0);
        assertEquals(15.0, article.getDiscount(), 0.01);
    }

    @Test
    void isDeleted() {
        article.setDeleted(true);
        assertTrue(article.isDeleted());
    }

    @Test
    void setDeleted() {
        article.setDeleted(true);
        assertTrue(article.isDeleted());
    }

    @Test
    void getTax() {
        article.setNetPrice(100.0);
        article.setDiscount(10.0);
        article.setTaxRate(20.0);
        assertEquals(18.0, article.getGrossPriceDiscounted() - article.getNetPriceDiscounted(), 0.01);
    }
}
