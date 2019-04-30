package com.quanpv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CARD")
public class Cart {

    @Id
    @Column(name = "CARD_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Integer id;

    private String idCustom;

    private Date createdDate;

    @JsonIgnore
    @ManyToOne
    private Customer customer;

    private String status;

    public Cart(String idCustom, Date createdDate, String status) {
        this.idCustom= idCustom;
        this.createdDate = createdDate;
        this.status = status;
    }

    public Cart() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCustom() {
        return idCustom;
    }

    public void setIdCustom(String idCustom) {
        this.idCustom = idCustom;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", dateCreated=" + createdDate +
                ", customer=" + customer +
                ", status='" + status + '\'' +
                '}';
    }
}
