package com.kush.banbah.soloprojectbackend.controller.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class AuthenticationRequest {
    @NotNull(message = "Email is missing")
    @NotBlank(message = "Email is blank")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is not in proper format")
    private String email;
    @NotNull
    @NotBlank
    private String password;
}
