package com.maxeriksson.iths.Webshop.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    public Product() {}

    public Product(String name, int price, Category category) {
        setName(name);
        setPrice(price);
        setCategory(category);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
