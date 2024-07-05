package ru.ktsybenkov.tgBot.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "client_orders")
public class ClientOrder {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Client client;

    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false, precision = 10,  scale = 2)
    private BigDecimal total;

    public ClientOrder() {
    }

    public ClientOrder(Client client, Integer status, BigDecimal total) {
        this.client = client;
        this.status = status;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
