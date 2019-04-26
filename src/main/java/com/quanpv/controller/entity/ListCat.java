package com.quanpv.controller.entity;

import com.quanpv.model.Category;

import java.util.List;

public class ListCat {

    private List<Category> list;

    public ListCat() {

    }


    public List<Category> getList() {
        return list;
    }

    public void setList(List<Category> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ListCat{" +
                "list=" + list +
                '}';
    }
}
