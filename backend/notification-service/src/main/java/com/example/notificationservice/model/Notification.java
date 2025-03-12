package com.example.notificationservice.model;



public class Notification {
    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    private String type;
    private  String message;
    private String status;

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
