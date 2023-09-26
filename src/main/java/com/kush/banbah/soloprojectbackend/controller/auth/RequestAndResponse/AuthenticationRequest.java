package com.kush.banbah.soloprojectbackend.controller.auth.RequestAndResponse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


/**
 * Class representing an authentication request.
 */
@Data
public class AuthenticationRequest {
    /**
     * The email of the user
     * Includes validation constraints
     */
    @NotNull(message = "Email is missing")
    @NotBlank(message = "Email is blank")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is not in proper format")
    private String email;

    /**
     * The password of the user.
     * Includes validation constraints
     */
    @NotNull
    @NotBlank
    private String password;
}
