package com.maxeriksson.iths.Webshop.domain.order;

import com.maxeriksson.iths.Webshop.domain.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer")
    @NotNull(message = "Order must belong to a User")
    private User customer;

    @Column(name = "datetime", columnDefinition = "DATETIME")
    @NotNull(message = "Order date must be specified")
    private LocalDateTime orderDate;

    @Column(name = "expedited")
    @NotNull
    private boolean expedited;

    @OneToMany(
            mappedBy = "order",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull(message = "Order must have a list of products")
    @Size(min = 1, message = "Order must have at least one Product")
    private List<OrderLine> products;

    public Order() {}

    public Order(User customer, LocalDateTime orderDate, List<OrderLine> products) {
        this.customer = customer;
        this.orderDate = orderDate;
        setProducts(products);
        this.expedited = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderLine> getProducts() {
        return products;
    }

    public void setProducts(List<OrderLine> products) {
        this.products = products;
        for (OrderLine orderLine : products) {
            orderLine.setOrder(this);
        }
    }

    public boolean isExpedited() {
        return expedited;
    }

    public void setExpedited(boolean expedited) {
        this.expedited = expedited;
    }
}
