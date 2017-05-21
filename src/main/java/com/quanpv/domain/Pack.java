package com.quanpv.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Pack {

    @Id
    @Column(name = "PACK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    private float price;
    private int quantity;

    public Pack(Date createdDate, float price, int quantity) {
        this.createdDate = createdDate;
        this.price = price;
        this.quantity = quantity;
    }

    public Pack(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Pack{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
