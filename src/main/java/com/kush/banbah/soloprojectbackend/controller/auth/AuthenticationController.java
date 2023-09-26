package com.kush.banbah.soloprojectbackend.controller.auth;


import com.kush.banbah.soloprojectbackend.controller.auth.RequestAndResponse.AuthenticationRequest;
import com.kush.banbah.soloprojectbackend.controller.auth.RequestAndResponse.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication Controller class to receive authenticate requests
 * As LMS, no one has authority to sign up or register for classes
 * Hence only authenticate endpoint is exposed
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * Post mapping for authenticate endpoint
     * @param authRequest containing email and password
     * @return response entity with token
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody AuthenticationRequest authRequest) {

        return ResponseEntity
                .status(200)
                .body(service.authenticate(authRequest));


    }


}
