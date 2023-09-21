package com.kush.banbah.soloprojectbackend.controller.testsDetails.ResponseAndRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherTestResponse {
    private String studentName;
    private long score;
    private int studentID;
}
