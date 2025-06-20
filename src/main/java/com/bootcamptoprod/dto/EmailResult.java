package com.bootcamptoprod.dto;

import java.util.List;

public record EmailResult(
        String email,
        List<String> improvementSteps
) {
}
