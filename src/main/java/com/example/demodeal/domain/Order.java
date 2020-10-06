package com.example.demodeal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Order {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private double finallyPrice;

    @Column
    private String address;// shipping address

    public Order() {
    }

    public Order(double finallyPrice, String address) {

        this.finallyPrice = finallyPrice;
        this.address = address;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getFinallyPrice() {
        return finallyPrice;
    }

    public void setFinallyPrice(double finallyPrice) {
        this.finallyPrice = finallyPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
