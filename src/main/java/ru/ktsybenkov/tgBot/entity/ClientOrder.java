package ru.ktsybenkov.tgBot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clientOrders")
public class ClientOrder {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Client client;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private double total;

    public ClientOrder(Client client, int status, double total) {
        this.client = client;
        this.status = status;
        this.total = total;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
