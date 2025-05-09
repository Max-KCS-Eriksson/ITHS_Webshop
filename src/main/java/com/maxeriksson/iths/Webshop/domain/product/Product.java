package com.maxeriksson.iths.Webshop.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "name")
    @NotBlank(message = "Name must not be empty")
    private String name;

    @Column(name = "price")
    @Min(value = 1, message = "Price must be at least 1")
    private int price;

    @ManyToOne
    @JoinColumn(name = "category")
    @NotNull(message = "Product must belong to a Category")
    private Category category;

    @Column(name = "for_sale")
    @NotNull(message = "For sale status must be specified")
    private boolean isForSale;

    public Product() {}

    public Product(String name, int price, Category category) {
        setName(name);
        setPrice(price);
        setCategory(category);
        this.isForSale = true;
    }

    public Product(String name, int price, Category category, boolean isForSale) {
        setName(name);
        setPrice(price);
        setCategory(category);
        this.isForSale = isForSale;
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

    public boolean isForSale() {
        return isForSale;
    }

    public void setForSale(boolean isForSale) {
        this.isForSale = isForSale;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Product other = (Product) obj;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        if (category == null) {
            if (other.category != null) return false;
        } else if (!category.equals(other.category)) return false;
        return true;
    }
}
