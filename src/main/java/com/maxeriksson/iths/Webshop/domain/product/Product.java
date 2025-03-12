package com.maxeriksson.iths.Webshop.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "name")
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @Column(name = "price")
    @Min(value = 1, message = "Price must be at least 1")
    private int price;

    @ManyToOne
    @JoinColumn(name = "category")
    @NotNull(message = "Product must belong to a Category")
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
