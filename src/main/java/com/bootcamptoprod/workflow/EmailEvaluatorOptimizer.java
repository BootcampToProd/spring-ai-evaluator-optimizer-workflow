package com.bootcamptoprod.workflow;

import com.bootcamptoprod.dto.EditorResponse;
import com.bootcamptoprod.dto.EmailResult;
import com.bootcamptoprod.dto.WriterResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailEvaluatorOptimizer {

    private final ChatClient chatClient;
    private static final int MAX_ATTEMPTS = 4; // Don't try forever!

    // Instructions for our Writer AI
    private static final String WRITER_INSTRUCTIONS = getWriterInstructions();

    // Instructions for our Editor AI
    private static final String EDITOR_INSTRUCTIONS = getEditorInstructions();

    // Constructor - Spring will create this for us
    public EmailEvaluatorOptimizer(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    // Main method that starts the improvement process
    public EmailResult createPerfectEmail(String emailType, String recipientName, String mainMessage, String tonePreference) {
        // Start the recursive improvement loop
        return improveEmailRecursively(emailType, recipientName, mainMessage, tonePreference, "", new ArrayList<>(), 1);
    }

    // Recursive method that keeps improving the email until it's perfect
    private EmailResult improveEmailRecursively(String emailType, String recipientName, String mainMessage,
                                                String tonePreference, String previousFeedback,
                                                List<String> processLog, int attemptNumber) {

        // Safety check - don't go on forever!
        if (attemptNumber > MAX_ATTEMPTS) {
            processLog.add("Reached maximum attempts - returning best version");
            return new EmailResult("Maximum attempts reached", processLog);
        }

        System.out.println("--- Attempt " + attemptNumber + " ---");

        // Step 1: Writer AI creates/improves the email
        WriterResponse draft = writeEmail(emailType, recipientName, mainMessage, tonePreference, previousFeedback);
        processLog.add("Round " + attemptNumber + ": Created email draft");

        System.out.println("Writer's reasoning: " + draft.reasoning());
        System.out.println("Email draft:\n" + draft.email());

        // Step 2: Editor AI reviews the email
        EditorResponse review = reviewEmail(draft.email(), emailType, tonePreference);

        System.out.println("Editor's verdict: " + review.verdict());
        System.out.println("Editor's suggestions: " + review.suggestions());

        // Step 3: Are we done? If yes, return the perfect email!
        if ("GOOD_TO_SEND".equals(review.verdict())) {
            processLog.add("Editor approved: Email is ready to send!");
            return new EmailResult(draft.email(), processLog);
        }

        // Step 4: Not perfect yet, prepare feedback and try again
        String feedbackForNextRound = "Previous email:\n" + draft.email() +
                "\n\nEditor feedback: " + review.suggestions() +
                "\n\nPlease improve the email based on this feedback.";

        processLog.add("Editor feedback: " + review.suggestions());

        // Recursive call - try again with the feedback
        return improveEmailRecursively(emailType, recipientName, mainMessage, tonePreference,
                feedbackForNextRound, processLog, attemptNumber + 1);
    }

    // Method that asks Writer AI to create/improve email
    private WriterResponse writeEmail(String emailType, String recipientName, String mainMessage, String tonePreference, String feedback) {

        String prompt = String.format("""
                %s
                
                Email Details:
                - Type: %s
                - Recipient: %s  
                - Main message: %s
                - Tone: %s
                
                %s
                """, WRITER_INSTRUCTIONS, emailType, recipientName, mainMessage, tonePreference, feedback);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .entity(WriterResponse.class);
    }

    // Method that asks Editor AI to review email
    private EditorResponse reviewEmail(String emailContent, String emailType, String tonePreference) {

        String prompt = String.format("""
                %s
                
                Email to review:
                %s
                
                Context: This is a %s email that should have a %s tone.
                """, EDITOR_INSTRUCTIONS, emailContent, emailType, tonePreference);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .entity(EditorResponse.class);
    }

    private static String getWriterInstructions() {
        return """
                You are a professional email writing assistant. Your task is to compose a clear, complete, and professional email based on the user's instructions.
                
                -   Create a suitable subject line and a full email body that covers all the user's key points.
                -   Ensure the tone is appropriate for the situation.
                -   If you receive feedback for revision, apply it to the next draft.
                
                Return your response as a single-line JSON object:
                {"reasoning": "A brief summary of the email's content and tone.", "email": "The complete email content."}
                """;
    }

    private static String getEditorInstructions() {
        return """
                You are a meticulous editor focused on ensuring communications are powerful and concise.
                
                You must evaluate the draft against one primary rule, then a secondary one.
                
                **1. The 80-Word Rule (Primary Check):**
                   - The body of the email (from the greeting to the closing) **MUST be 80 words or less.**
                   - **If the draft is over 80 words, the verdict is ALWAYS "NEEDS_WORK".** Your feedback must state the word count and instruct the writer to shorten the email significantly.
                
                **2. The Clarity Rule (Secondary Check - only if word count is met):**
                   - If the email is 80 words or less, check if its core message is clear and easy to understand.
                
                Your Verdict:
                -   If the word count is over 80, it automatically **NEEDS_WORK**.
                -   If the word count is 80 or less AND the message is clear, it is **"GOOD_TO_SEND"**.
                
                Return your response as a single-line JSON object:
                {"verdict": "GOOD_TO_SEND or NEEDS_WORK", "suggestions": "If NEEDS_WORK, state the word count and the need for brevity. For example: 'The draft is 110 words, exceeding the 80-word limit. Please revise for conciseness.'"}
                """;
    }

}
