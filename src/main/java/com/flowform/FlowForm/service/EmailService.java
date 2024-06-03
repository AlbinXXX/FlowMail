package com.flowform.FlowForm.service;

import com.flowform.FlowForm.model.EmailFormat;
import com.flowform.FlowForm.model.EmailValidationResult;
import com.flowform.FlowForm.model.ProgressUpdate;
import com.flowform.FlowForm.model.UserInput;
import com.flowform.FlowForm.repository.EmailFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Service class to handle email operations including generation, validation, and storage.
 */
@Service
public class EmailService {

    @Autowired
    private EmailFormatRepository emailFormatRepository;

    @Autowired
    private EmailValidationService emailValidationService; // New service for MX checks

    /**
     * Generates a comprehensive list of email permutations based on first name, last name, and domain.
     *
     * @param firstName User's first name
     * @param lastName  User's last name
     * @param domain    Domain to append to email addresses
     * @return List of email permutations
     */
    public List<String> generateEmailPermutations(String firstName, String lastName, String domain) {
        List<String> permutations = new ArrayList<>();
        String f = firstName.toLowerCase();
        String l = lastName.toLowerCase();
        String initial = f.substring(0, 1);

        // Comprehensive email formats
        permutations.add(f + "." + l + "@" + domain);          // firstname.lastname@domain.com
        permutations.add(initial + "." + l + "@" + domain);    // f.lastname@domain.com
        permutations.add(f + l + "@" + domain);                // firstnamelastname@domain.com
        permutations.add(f + "_" + l + "@" + domain);          // firstname_lastname@domain.com
        permutations.add(initial + l + "@" + domain);          // flastname@domain.com
        permutations.add(f + "-" + l + "@" + domain);          // firstname-lastname@domain.com
        permutations.add(l + "." + f + "@" + domain);          // lastname.firstname@domain.com
        permutations.add(l + f + "@" + domain);                // lastnamefirstname@domain.com
        permutations.add(l + "_" + f + "@" + domain);          // lastname_firstname@domain.com
        permutations.add(l + "-" + f + "@" + domain);          // lastname-firstname@domain.com
        permutations.add(f + "@" + domain);                    // firstname@domain.com
        permutations.add(l + "@" + domain);                    // lastname@domain.com
        permutations.add(initial + "@" + domain);              // f@domain.com
        permutations.add(f + initial + "@" + domain);          // firstNameInitial@domain.com
        permutations.add(initial + l + "@" + domain);          // flastname@domain.com
        permutations.add(l + initial + "@" + domain);          // lastnameInitial@domain.com
        // Add more permutations as needed

        return permutations;
    }

    /**
     * Asynchronously validates an email address by checking MX records.
     *
     * @param email Email address to validate
     * @return CompletableFuture containing the validation result
     */
    @Async("emailTaskExecutor")
    public CompletableFuture<EmailValidationResult> validateEmailAsync(String email) {
        EmailValidationResult result = new EmailValidationResult();
        result.setEmail(email);
        try {
            boolean hasMX = emailValidationService.hasMXRecords(email);
            if (hasMX) {
                result.setValid(true);
                result.setMessage("Email domain has valid MX records.");
            } else {
                result.setValid(false);
                result.setMessage("Validation failed: No MX records found for the domain.");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately
            result.setValid(false);
            result.setMessage("Validation failed: " + e.getMessage());
        }
        return CompletableFuture.completedFuture(result);
    }

    /**
     * Processes email validations and sends real-time updates via Server-Sent Events (SSE).
     *
     * @param userInput User input containing domain, first name, and last name
     * @param emitter   SseEmitter for sending real-time updates
     */
    public void findValidEmails(UserInput userInput, SseEmitter emitter) {
        String domain = userInput.getDomain();
        String firstName = userInput.getFirstName();
        String lastName = userInput.getLastName();

        List<String> permutations = generateEmailPermutations(firstName, lastName, domain);
        List<CompletableFuture<EmailValidationResult>> futures = new ArrayList<>();

        for (String email : permutations) {
            CompletableFuture<EmailValidationResult> future = validateEmailAsync(email);
            futures.add(future);
        }

        CompletableFuture.runAsync(() -> {
            try {
                int total = futures.size();
                int count = 0;
                List<String> foundEmails = new ArrayList<>();

                for (CompletableFuture<EmailValidationResult> future : futures) {
                    EmailValidationResult result = future.get(); // Blocking call
                    count++;
                    if (result.isValid()) {
                        foundEmails.add(result.getEmail());
                        // Extract format, e.g., firstname.lastname
                        String format = result.getEmail().substring(0, result.getEmail().indexOf("@"));
                        saveEmailFormat(domain, format);
                    }

                    // Send progress update
                    emitter.send(SseEmitter.event()
                            .name("progress")
                            .data(new ProgressUpdate(count, total, result.getEmail(), result.getMessage())));
                }

                // Send completion event with found emails
                emitter.send(SseEmitter.event()
                        .name("complete")
                        .data(foundEmails));

                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
    }

    /**
     * Saves the email format to the database if it doesn't already exist for the given domain.
     *
     * @param domain Domain of the email format
     * @param format Email format string
     */
    public void saveEmailFormat(String domain, String format) {
        List<EmailFormat> existingFormats = emailFormatRepository.findByDomain(domain);
        boolean exists = existingFormats.stream()
                .anyMatch(f -> f.getFormat().equals(format));
        if (!exists) {
            EmailFormat emailFormat = new EmailFormat(domain, format);
            emailFormatRepository.save(emailFormat);
        }
    }

    /**
     * Retrieves all email formats associated with a given domain.
     *
     * @param domain Domain to search for
     * @return List of EmailFormat entities
     */
    public List<EmailFormat> getEmailFormats(String domain) {
        return emailFormatRepository.findByDomain(domain);
    }
}
