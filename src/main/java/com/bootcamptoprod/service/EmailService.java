package com.bootcamptoprod.service;

import com.bootcamptoprod.dto.EmailRequest;
import com.bootcamptoprod.dto.EmailResponse;
import com.bootcamptoprod.dto.EmailResult;
import com.bootcamptoprod.workflow.EmailEvaluatorOptimizer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final EmailEvaluatorOptimizer optimizer;

    public EmailService(EmailEvaluatorOptimizer optimizer) {
        this.optimizer = optimizer;
    }

    public EmailResponse generateEmail(EmailRequest request) {
        try {
            // Use our Evaluator-Optimizer to create the perfect email
            EmailResult result = optimizer.createPerfectEmail(
                    request.emailType(),
                    request.recipientName(),
                    request.mainMessage(),
                    request.tonePreference()
            );

            // Convert to response format
            return new EmailResponse(
                    result.email(),
                    (result.improvementSteps().size() / 2) - 1,
                    result.improvementSteps()
            );

        } catch (Exception e) {
            // If something goes wrong, tell the user nicely
            return new EmailResponse(
                    "Sorry, we couldn't generate your email. Please try again.",
                    0,
                    List.of("Error occurred: " + e.getMessage())
            );
        }
    }
}
