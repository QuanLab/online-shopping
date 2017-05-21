package com.quanpv.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "CARD")
public class BillProduct {

    @Id
    @Column(name = "BILL_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

//    @ManyToOne
//    @JoinColumn(name = "CUSTOMER_ID")
//    private Customer customer;
//
//    @OneToMany
//    private Product product;

    private int quantity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateDeliver;
    private boolean status;

}
