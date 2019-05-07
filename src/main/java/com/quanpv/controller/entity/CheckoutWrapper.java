package com.quanpv.controller.entity;

public class CheckoutWrapper {

    private String name;
    private String email;
    private String phone;
    private String adddress;
    private String note;
    private String token;

    public CheckoutWrapper() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdddress() {
        return adddress;
    }

    public void setAdddress(String adddress) {
        this.adddress = adddress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "CheckoutWrapper{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", adddres='" + adddress + '\'' +
                ", note='" + note + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
