package edu.mci.fooddirector.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.mci.fooddirector.model.enums.ArticleCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="tbArticles")
public class Article extends AbstractEntity {
    @NotEmpty
    @Column(name="name")
    private String name;

    @Column(name="netprice")
    private double netPrice;

    @Column(name="taxrate")
    private double taxRate;

    @Column(name="image")
    private String image;

    @Column(name="description")
    private String description;

    @NotNull
    @Column(name="articlecategory")
    private ArticleCategory articleCategory;

    @Column(name="dailyoffer")
    private boolean dailyOffer;

    @Max(100)
    @Column(name="discount")
    private double discount;

    @Column(name="isdeleted")
    private boolean isDeleted;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNetPrice() {
        return netPrice;
    }

    @JsonIgnore
    public double getNetPriceDiscounted() {
        var netPrice = getNetPrice();
        return netPrice - (netPrice * discount / 100);
    }

    @JsonIgnore
    public double getGrossPriceDiscounted() {
        return getNetPriceDiscounted() + calculateTax(getNetPriceDiscounted());
    }

    @JsonIgnore
    private double calculateTax(double value) {
        return value * getTaxRate() / 100;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
