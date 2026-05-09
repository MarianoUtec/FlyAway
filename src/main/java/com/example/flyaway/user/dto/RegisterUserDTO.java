package com.example.flyaway.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterUserDTO(

        @NotBlank
        @Pattern(
                regexp = ".*[A-Z].*",
                message = "First name must contain at least one uppercase letter"
        )
        String firstName,

        @NotBlank
        @Pattern(
                regexp = ".*[A-Z].*",
                message = "Last name must contain at least one uppercase letter"
        )
        String lastName,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
                message = "Password must contain at least 8 characters, one letter and one number"
        )
        String password

) {
}
