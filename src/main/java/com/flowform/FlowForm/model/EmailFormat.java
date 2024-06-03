package com.flowform.FlowForm.model;

import jakarta.persistence.*;

@Entity
@Table(name = "email_formats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"domain", "format"})
})
public class EmailFormat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String domain;

    @Column(nullable = false)
    private String format; // e.g., firstname.lastname

    // Constructors

    public EmailFormat() {
        // Default constructor required by JPA
    }

    public EmailFormat(String domain, String format) {
        this.domain = domain;
        this.format = format;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    // No setter for 'id' since it's auto-generated

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    // toString Method

    @Override
    public String toString() {
        return "EmailFormat{" +
                "id=" + id +
                ", domain='" + domain + '\'' +
                ", format='" + format + '\'' +
                '}';
    }

    // equals and hashCode (Optional but recommended)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailFormat that = (EmailFormat) o;

        if (!domain.equals(that.domain)) return false;
        return format.equals(that.format);
    }

    @Override
    public int hashCode() {
        int result = domain.hashCode();
        result = 31 * result + format.hashCode();
        return result;
    }
}
