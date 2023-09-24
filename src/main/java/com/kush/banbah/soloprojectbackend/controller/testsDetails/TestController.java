package com.kush.banbah.soloprojectbackend.controller.testsDetails;

import com.kush.banbah.soloprojectbackend.controller.testsDetails.ResponseAndRequest.CreateTestRequest;
import com.kush.banbah.soloprojectbackend.controller.testsDetails.ResponseAndRequest.ScoreUpdateRequest;
import com.kush.banbah.soloprojectbackend.database.user.User;
import com.kush.banbah.soloprojectbackend.exceptions.*;
import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestExceptions.InvalidTestNameException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/test")
public class TestController {

    private final TestService testService;

    @GetMapping("/student/{className}")
    public ResponseEntity<String> studentRequest(@PathVariable String className, Authentication auth) throws EntityNotFoundException, EntityDoesNotBelongException {

            return ResponseEntity
                    .status(200)
                    .body(testService.getStudentScores(className, auth));


    }

    @GetMapping("/teacher/{className}")
    public ResponseEntity<String> teacherRequest(@PathVariable String className, Authentication auth) throws EntityNotFoundException, EntityDoesNotBelongException {


            return ResponseEntity
                    .status(200)
                    .body(testService.getTeacherTests(className, auth));
    }

    @GetMapping("/teacher/{className}/{testID}")
    public ResponseEntity<String> teacherRequestTest(@PathVariable String className, @PathVariable String testID, Authentication auth) throws EntityNotFoundException, EntityDoesNotBelongException {


            return ResponseEntity
                    .status(200)
                    .body(testService.getTeacherStudentScores(className, Integer.parseInt(testID), auth));

    }

    @PostMapping("/teacher/{className}/{testID}/{studentID}")
    public ResponseEntity<String> teacherUpdateGrade(@PathVariable String className, @PathVariable String testID, @PathVariable int studentID, Authentication auth, @Valid @RequestBody ScoreUpdateRequest newScore) throws EntityNotFoundException, EntityDoesNotBelongException {

        try {
            User student = testService.updateGrade(className, Integer.parseInt(testID), studentID, auth, newScore);
            return ResponseEntity
                    .status(200)
                    .body("Updated score for Student " + student.getName());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(e.getMessage());
        }
    }
        @PostMapping("/teacher/{className}")
        public ResponseEntity<String> teacherCreateTest(@PathVariable String className, Authentication auth, @Valid @RequestBody CreateTestRequest createTest) throws EntityNotFoundException, InvalidRequestException, EntityDoesNotBelongException {


            testService.createTest(className, auth, createTest.getNewTestName());


            return ResponseEntity
                    .status(201)
                    .body("Created Test "+createTest.getNewTestName()+" for class "+className);
        }

    }


