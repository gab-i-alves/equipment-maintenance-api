package com.web2.projetoweb2.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.web2.projetoweb2.services.EmailService;

public class EmailControler {

    @Autowired
    private EmailService emailService;

    @PostMapping("/test-email")
    public ResponseEntity<String> testEmail() {
        emailService.enviarEmail("recipient@example.com", "Test Email", "This is a test email from the system.");
        return ResponseEntity.ok("Email sent successfully!");
    }
    
}
