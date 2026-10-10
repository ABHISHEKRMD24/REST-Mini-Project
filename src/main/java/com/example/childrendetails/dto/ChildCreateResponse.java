package com.example.childrendetails.dto;

import com.example.childrendetails.model.Child;

public class ChildCreateResponse {
    private String message;
    private Child child;

    public ChildCreateResponse() {
    }

    public ChildCreateResponse(String message, Child child) {
        this.message = message;
        this.child = child;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
}
