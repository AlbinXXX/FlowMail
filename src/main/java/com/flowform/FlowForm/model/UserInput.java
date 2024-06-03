package com.flowform.FlowForm.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UserInput {
    @NotEmpty(message = "Domain is required.")
    @Pattern(regexp = "^[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid domain format.")
    private String domain;

    @NotEmpty(message = "First name is required.")
    private String firstName;

    @NotEmpty(message = "Last name is required.")
    private String lastName;

    // Constructors
    public UserInput() {
        // Default constructor
    }

    public UserInput(String domain, String firstName, String lastName) {
        this.domain = domain;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // toString Method
    @Override
    public String toString() {
        return "UserInput{" +
                "domain='" + domain + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
