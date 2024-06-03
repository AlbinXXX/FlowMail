package com.flowform.FlowForm.model;

public class ProgressUpdate {
    private int current;
    private int total;
    private String email;
    private String message;

    // Constructors
    public ProgressUpdate() {
    }

    public ProgressUpdate(int current, int total, String email, String message) {
        this.current = current;
        this.total = total;
        this.email = email;
        this.message = message;
    }

    // Getters and Setters
    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

