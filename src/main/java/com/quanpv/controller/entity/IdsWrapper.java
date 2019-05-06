package com.quanpv.controller.entity;

import java.util.List;

public class IdsWrapper {

    private List<Integer> list;

    public IdsWrapper() {

    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ImageWrapper{" +
                "list=" + list +
                '}';
    }
}
