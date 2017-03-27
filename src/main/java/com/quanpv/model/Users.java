package com.quanpv.model;


import javax.persistence.Id;

public class Users {
    @Id
    private String username;
    private String password;
    private int enable;
}
