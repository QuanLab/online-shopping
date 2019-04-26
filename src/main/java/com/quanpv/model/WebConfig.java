package com.quanpv.model;

import javax.persistence.*;


@Entity
@Table(name = "WEB_CONFIG")
public class WebConfig {

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(length = 10000)
    private String value;


    public WebConfig() {
    }

    public WebConfig(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "WebConfig{" +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
