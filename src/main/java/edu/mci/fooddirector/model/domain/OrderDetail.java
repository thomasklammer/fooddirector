package edu.mci.fooddirector.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name="tbOrderDetails")
public class OrderDetail extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(name="netvalue")
    private double netValue;

    @Column(name="taxrate")
    private double taxRate;

    @Min(1)
    @Column(name="amount")
    private int amount;

    @Column(name="note")
    private String note;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article articleId) {
        this.article = articleId;
    }

    public double getNetValue() {
        return netValue;
    }

    public void setNetValue(double netValue) {
        this.netValue = netValue;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
