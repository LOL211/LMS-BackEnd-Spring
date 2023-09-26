package com.kush.banbah.soloprojectbackend.controller.auth;


import com.kush.banbah.soloprojectbackend.config.authConfig.JwtService;
import com.kush.banbah.soloprojectbackend.controller.auth.RequestAndResponse.AuthenticationRequest;
import com.kush.banbah.soloprojectbackend.controller.auth.RequestAndResponse.AuthenticationResponse;
import com.kush.banbah.soloprojectbackend.database.user.User;
import com.kush.banbah.soloprojectbackend.database.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for Authentication controller to separate controller and handling logic
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //    public AuthenticationResponse register(RegisterRequest registerRequest) {
//        UserEntity user = UserEntity.builder()
//                .name(registerRequest.getName())
//                .email(registerRequest.getEmail())
//                .password(passwordEncoder.encode(registerRequest.getPassword()))
//                .role(UserEntity.Role.STUDENT)
//                .build();
//
//        repo.save(user);
//        String jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }


    /**
     * Authenticates a user and generates a JWT token.
     * Authentication manager authenticates user
     * JWT token is created if successful
     *
     * @param authRequest The authentication request containing the user's email and password.
     * @return An AuthenticationResponse containing the generated JWT token.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        User user = repo.findByEmail(authRequest.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}

