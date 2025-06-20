package com.bootcamptoprod.controller;

import com.bootcamptoprod.dto.EmailRequest;
import com.bootcamptoprod.dto.EmailResponse;
import com.bootcamptoprod.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/generate")
    public ResponseEntity<EmailResponse> generateEmail(@RequestBody EmailRequest request) {
        EmailResponse response = emailService.generateEmail(request);
        return ResponseEntity.ok(response);
    }
}
