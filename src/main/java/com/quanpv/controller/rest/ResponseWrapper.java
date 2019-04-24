package com.quanpv.controller.rest;

public class ResponseWrapper {

    private Integer status;
    private String message;

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

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
