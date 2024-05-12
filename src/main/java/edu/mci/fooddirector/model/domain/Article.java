package edu.mci.fooddirector.model.domain;

import edu.mci.fooddirector.model.enums.ArticleCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Article extends AbstractEntity {
    @NotEmpty
    private String name;

    private double netPrice;

    private double taxRate;

    @Lob
    private byte[] image;

    private String description;

    @NotNull
    private ArticleCategory articleCategory;

    private boolean dailyOffer;

    @Max(100)
    private double discount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(double netPrice) {
        this.netPrice = netPrice;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArticleCategory getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(ArticleCategory articleCategory) {
        this.articleCategory = articleCategory;
    }

    public boolean isDailyOffer() {
        return dailyOffer;
    }

    public void setDailyOffer(boolean dailyOffer) {
        this.dailyOffer = dailyOffer;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
