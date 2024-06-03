package com.flowform.FlowForm.controller;

import com.flowform.FlowForm.model.EmailFormat;
import com.flowform.FlowForm.model.UserInput;
import com.flowform.FlowForm.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * Controller class to handle HTTP requests related to email operations.
 */
@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    /**
     * Displays the home page with the email input form.
     *
     * @param model Model to hold form attributes
     * @return Name of the Thymeleaf template for the home page
     */
    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("userInput", new UserInput());
        return "index";
    }

    /**
     * Handles form submission for finding emails.
     *
     * @param userInput User input containing domain, first name, and last name
     * @param model     Model to hold attributes
     * @return Redirects to the result page with query parameters
     */
    @PostMapping("/find-emails")
    public String findEmails(@ModelAttribute UserInput userInput, Model model) {
        // Redirect to result page with query parameters
        return "redirect:/result?domain=" + userInput.getDomain() +
                "&firstName=" + userInput.getFirstName() +
                "&lastName=" + userInput.getLastName();
    }

    /**
     * Displays the result page where real-time progress updates will be shown.
     *
     * @param domain    Domain to search for
     * @param firstName User's first name
     * @param lastName  User's last name
     * @param model     Model to hold attributes
     * @return Name of the Thymeleaf template for the result page
     */
    @GetMapping("/result")
    public String showResultPage(@RequestParam String domain,
                                 @RequestParam String firstName,
                                 @RequestParam String lastName,
                                 Model model) {
        model.addAttribute("domain", domain);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        return "result";
    }

    /**
     * SSE endpoint to stream real-time progress updates to the client.
     *
     * @param domain    Domain to search for
     * @param firstName User's first name
     * @param lastName  User's last name
     * @return SseEmitter for sending events
     */
    @GetMapping("/stream-emitter")
    public SseEmitter streamEmitter(@RequestParam String domain,
                                    @RequestParam String firstName,
                                    @RequestParam String lastName) {
        UserInput userInput = new UserInput(domain, firstName, lastName);
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // No timeout

        emailService.findValidEmails(userInput, emitter);

        return emitter;
    }

    /**
     * Displays the stored email formats for a specific domain.
     *
     * @param domain Domain to search for
     * @param model  Model to hold attributes
     * @return Name of the Thymeleaf template for displaying formats
     */
    @GetMapping("/formats/{domain}")
    public String getFormats(@PathVariable String domain, Model model) {
        List<EmailFormat> formats = emailService.getEmailFormats(domain);
        model.addAttribute("formats", formats);
        model.addAttribute("domain", domain);
        return "formats";
    }
}
