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
    @JsonIgnore
    private Date createdDate;
    @JsonIgnore
    private Date checkoutDate;

    @JsonIgnore
    @ManyToOne
    private Customer customer;

    private String promotionCode;

    private float shippingFee;

    @JsonIgnore
    private String status;

    public Cart(String idCustom, Date createdDate, String status) {
        this.idCustom= idCustom;
        this.createdDate = createdDate;
        this.status = status;
    }

    public Cart() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdCustom() {
        return idCustom;
    }

    public void setIdCustom(String idCustom) {
        this.idCustom = idCustom;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public float getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(float shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", idCustom='" + idCustom + '\'' +
                ", createdDate=" + createdDate +
                ", customer=" + customer +
                ", status='" + status + '\'' +
                '}';
    }
}
