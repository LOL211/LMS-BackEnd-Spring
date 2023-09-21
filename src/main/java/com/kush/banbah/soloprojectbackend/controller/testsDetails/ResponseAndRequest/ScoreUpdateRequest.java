package com.kush.banbah.soloprojectbackend.controller.testsDetails.ResponseAndRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScoreUpdateRequest {
    @NotNull(message = "Score cannot be empty")
    private String newScore;
}
