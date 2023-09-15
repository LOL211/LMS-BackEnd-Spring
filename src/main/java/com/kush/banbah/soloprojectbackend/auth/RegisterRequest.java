package com.kush.banbah.soloprojectbackend.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class RegisterRequest {

    @NotNull(message = "Name is missing")
    @NotBlank
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is not in proper format")
    private String name;
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String password;

}
