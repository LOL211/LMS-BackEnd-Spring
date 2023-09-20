package com.kush.banbah.soloprojectbackend.controller.testsDetails;

import com.kush.banbah.soloprojectbackend.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/test")
public class TestController {

    private final TestService testService;

    @GetMapping("/student/{className}")
    public ResponseEntity<String> studentRequest(@PathVariable String className, Authentication auth) {
        try {
            return ResponseEntity
                    .status(200)
                    .body(testService.getStudentScores(className, auth));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity
                    .status(500)
                    .body("User not found");
        } catch (ClassDoesNotExistException e) {
            return ResponseEntity
                    .status(404)
                    .body(e.getMessage());
        } catch (UserDoesNotBelongToClassException e) {
            return ResponseEntity
                    .status(403)
                    .body(e.getMessage());
        }

    }

    @GetMapping("/teacher/{className}")
    public ResponseEntity<String> teacherRequest(@PathVariable String className, Authentication auth) {

        try {
            return ResponseEntity
                    .status(200)
                    .body(testService.getTeacherTests(className,auth));
        } catch (ClassDoesNotExistException e) {
            return ResponseEntity
                    .status(404)
                    .body(e.getMessage());
        } catch (NotTeacherOfClassException e) {
            return ResponseEntity
                    .status(403)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/teacher/{className}/{testName}")
    public ResponseEntity<String> teacherRequestTest(@PathVariable String className,@PathVariable String testName, Authentication auth) {

        try {
            return ResponseEntity
                    .status(200)
                    .body(testService.getTeacherStudentScores(className,testName, auth));
        } catch (ClassDoesNotExistException | TestNotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(e.getMessage());
        } catch (NotTeacherOfClassException | TestDoesNotBelongToClassException e) {
            return ResponseEntity
                    .status(403)
                    .body(e.getMessage());
        }
    }



}
