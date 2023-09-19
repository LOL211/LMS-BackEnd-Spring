package com.kush.banbah.soloprojectbackend.controller.testsDetails;

import com.kush.banbah.soloprojectbackend.exceptions.ClassDoesNotExistException;
import com.kush.banbah.soloprojectbackend.exceptions.UserDoesNotBelongToClassException;
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

    @GetMapping("/student/{classname}")
    public ResponseEntity<String> studentRequest(@PathVariable String classname, Authentication auth) {
        try {
            return ResponseEntity
                    .status(200)
                    .body(testService.getStudentScores(classname, auth));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity
                    .status(500)
                    .body("User not found");
        } catch (ClassDoesNotExistException e) {
            return ResponseEntity
                    .status(404)
                    .body("Classname not found");
        } catch (UserDoesNotBelongToClassException e) {
            return ResponseEntity
                    .status(403)
                    .body("User is not allowed to access " + classname);
        }

    }

    @GetMapping("/teacher/{classname}")
    public ResponseEntity<String> teacherRequest(@PathVariable String classname) {
        return ResponseEntity.ok(classname);
    }
//    @GetMapping("/{classname}")
//    @PreAuthorize("hasAnyAuthority('TEACHER')")
//    public ResponseEntity<String> teacherRequest(@PathVariable String className)
//    {
//        return ResponseEntity.ok(className+" hi");
//    }
}
