package com.kush.banbah.soloprojectbackend.controller.testsDetails;

import com.kush.banbah.soloprojectbackend.controller.testsDetails.ResponseAndRequest.CreateTestRequest;
import com.kush.banbah.soloprojectbackend.controller.testsDetails.ResponseAndRequest.ScoreUpdateRequest;
import com.kush.banbah.soloprojectbackend.database.user.User;
import com.kush.banbah.soloprojectbackend.exceptions.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/test")
public class TestController {

    private final TestService testService;

    @GetMapping("/student/{className}")
    public ResponseEntity<String> studentRequest(@PathVariable String className, Authentication auth) throws ClassDoesNotExistException, UserDoesNotBelongToClassException {

            return ResponseEntity
                    .status(200)
                    .body(testService.getStudentScores(className, auth));


    }

    @GetMapping("/teacher/{className}")
    public ResponseEntity<String> teacherRequest(@PathVariable String className, Authentication auth) throws NotTeacherOfClassException, ClassDoesNotExistException {


            return ResponseEntity
                    .status(200)
                    .body(testService.getTeacherTests(className, auth));
    }

    @GetMapping("/teacher/{className}/{testName}")
    public ResponseEntity<String> teacherRequestTest(@PathVariable String className, @PathVariable String testName, Authentication auth) throws NotTeacherOfClassException, ClassDoesNotExistException, TestNotFoundException, TestDoesNotBelongToClassException {


            return ResponseEntity
                    .status(200)
                    .body(testService.getTeacherStudentScores(className, testName, auth));

    }

    @PostMapping("/teacher/{className}/{testName}/{studentID}")
    public ResponseEntity<String> teacherUpdateGrade(@PathVariable String className, @PathVariable String testName, @PathVariable int studentID, Authentication auth, @Valid @RequestBody ScoreUpdateRequest newScore) throws NotTeacherOfClassException, ClassDoesNotExistException, TestNotFoundException, UserDoesNotBelongToClassException, TestDoesNotBelongToClassException {

        try {
            User student = testService.updateGrade(className, testName, studentID, auth, newScore);
            return ResponseEntity
                    .status(200)
                    .body("Updated " + testName + " score for Student " + student.getName());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(e.getMessage());
        }
    }
        @PostMapping("/teacher/{className}")
        public ResponseEntity<String> teacherCreateTest(@PathVariable String className, Authentication auth, @Valid @RequestBody CreateTestRequest createTest) throws NotTeacherOfClassException, ClassDoesNotExistException, InvalidTestNameException {


            testService.createTest(className, auth, createTest.getNewTestName());


            return ResponseEntity
                    .status(201)
                    .body("Created Test "+createTest.getNewTestName()+" for class "+className);
        }

    }


