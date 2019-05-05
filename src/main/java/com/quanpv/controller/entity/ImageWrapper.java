package com.quanpv.controller.entity;

import com.quanpv.model.Category;

import java.util.List;

public class ImageWrapper {

    private List<Category> list;

    public ImageWrapper() {

    }


    public List<Category> getList() {
        return list;
    }

    public void setList(List<Category> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ImageWrapper{" +
                "list=" + list +
                '}';
    }
}
