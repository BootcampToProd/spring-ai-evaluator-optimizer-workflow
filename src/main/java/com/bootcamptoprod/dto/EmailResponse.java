package com.bootcamptoprod.dto;

import java.util.List;

public record EmailResponse(
        String finalEmail,        // The polished email
        int improvementRounds,    // How many times we improved it
        List<String> processLog   // Think of it like a journal of changes
) {
}