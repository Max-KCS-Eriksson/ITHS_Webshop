package com.maxeriksson.iths.Webshop.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "name")
    @NotEmpty(message = "Name must not be empty")
    private String name;

    public Category() {}

    public Category(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
