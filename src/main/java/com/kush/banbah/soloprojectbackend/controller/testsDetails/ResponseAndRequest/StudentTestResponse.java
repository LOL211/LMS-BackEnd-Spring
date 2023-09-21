package com.kush.banbah.soloprojectbackend.controller.testsDetails.ResponseAndRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentTestResponse {
    private String testName;
    private int score;
}
