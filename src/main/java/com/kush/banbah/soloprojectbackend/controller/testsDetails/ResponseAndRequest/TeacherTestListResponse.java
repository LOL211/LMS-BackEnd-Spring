package com.kush.banbah.soloprojectbackend.controller.testsDetails.ResponseAndRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherTestListResponse {
    private String testName;
    private int test_id;
}
