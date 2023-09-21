package com.kush.banbah.soloprojectbackend.controller.testsDetails.ResponseAndRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTestRequest {
    @NotNull(message = "Test name cannot be empty")
    private String newTestName;
}
