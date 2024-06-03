package com.flowform.FlowForm.service;

import org.springframework.stereotype.Service;

import javax.naming.NamingEnumeration;
import javax.naming.directory.*;
import java.util.Hashtable;

/**
 * Service class to handle email validations using MX record checks.
 */
@Service
public class EmailValidationService {

    /**
     * Checks if the domain has valid MX records.
     *
     * @param email Email address to validate
     * @return true if MX records exist, false otherwise
     */
    public boolean hasMXRecords(String email) {
        String domain = getDomain(email);
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ctx = new InitialDirContext(env);
            Attributes attrs = ctx.getAttributes(domain, new String[]{"MX"});
            Attribute attr = attrs.get("MX");

            return attr != null && attr.size() > 0;
        } catch (Exception e) {
            // Log the exception (optional)
            return false;
        }
    }

    /**
     * Extracts the domain part from an email address.
     *
     * @param email Email address
     * @return Domain part of the email
     */
    private String getDomain(String email) {
        return email.substring(email.indexOf("@") + 1);
    }
}
