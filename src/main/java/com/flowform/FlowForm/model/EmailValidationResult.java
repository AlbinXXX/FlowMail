package com.flowform.FlowForm.model;

public class EmailValidationResult {
    private String email;
    private boolean valid;
    private String message;

    // Constructors
    public EmailValidationResult() {
    }

    public EmailValidationResult(String email, boolean valid, String message) {
        this.email = email;
        this.valid = valid;
        this.message = message;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
