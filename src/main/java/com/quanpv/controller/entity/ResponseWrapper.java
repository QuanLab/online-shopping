package com.quanpv.controller.entity;

public class ResponseWrapper {

    private Integer status;
    private String message;
    private Object object;

    public ResponseWrapper() {

    }

    public ResponseWrapper(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
