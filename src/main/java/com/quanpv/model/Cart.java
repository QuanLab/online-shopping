package com.quanpv.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CARD")
public class Cart {

    @Id
    @Column(name = "CARD_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String idCustom;

    private Date dateCreated;

    @ManyToOne
    private Customer customer;

    private String status;

    public Cart(String idCustom, Date dateCreated, String status) {
        this.idCustom= idCustom;
        this.dateCreated = dateCreated;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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
                ", dateCreated=" + dateCreated +
                ", customer=" + customer +
                ", status='" + status + '\'' +
                '}';
    }
}
