package com.kush.banbah.soloprojectbackend.auth;

import com.kush.banbah.soloprojectbackend.User.Role;
import com.kush.banbah.soloprojectbackend.User.User;
import com.kush.banbah.soloprojectbackend.User.UserRepo;
import com.kush.banbah.soloprojectbackend.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest registerRequest) {
    User user = User.builder()
            .firstname(registerRequest.getFirstname())
            .lastname(registerRequest.getLastname())
            .email(registerRequest.getEmail())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .role(Role.STUDENT)
            .build();

    repo.save(user);
    String jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

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
