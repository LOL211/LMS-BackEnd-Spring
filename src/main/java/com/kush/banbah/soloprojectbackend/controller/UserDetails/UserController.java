package com.kush.banbah.soloprojectbackend.controller.UserDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/details")
    public ResponseEntity<String> retrieveUserDetails(Authentication auth) {

        return ResponseEntity
                .status(200)
                .body(userService.retrieveUserDetails(auth));
    }
}
