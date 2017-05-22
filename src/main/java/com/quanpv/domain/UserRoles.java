package com.quanpv.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class UserRoles {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    public UserRoles() {

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
