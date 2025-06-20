package com.bootcamptoprod.dto;

public record EmailRequest(
        String emailType,        // e.g., "job_followup", "raise_request"
        String recipientName,    // "John Smith"
        String mainMessage,      // "I want to follow up on my interview"
        String tonePreference    // "professional", "friendly", "formal"
) {
}