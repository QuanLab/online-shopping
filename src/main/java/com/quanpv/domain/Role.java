package com.quanpv.domain;

import javax.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    public Role() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
