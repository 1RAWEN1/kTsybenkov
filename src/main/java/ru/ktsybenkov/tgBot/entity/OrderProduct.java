package ru.ktsybenkov.tgBot.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "orderProducts")
public class OrderProduct {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private ClientOrder clientOrder;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private Long countProduct;

    public OrderProduct(ClientOrder clientOrder, Product product
            , Long countProduct) {
        this.clientOrder = clientOrder;
        this.product = product;
        this.countProduct = countProduct;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ClientOrder getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getCountProduct() {
        return countProduct;
    }

    public void setCountProduct(Long countProduct) {
        this.countProduct = countProduct;
    }
}
