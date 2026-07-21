package com.incubyte.car_dealership.dto;

import jakarta.validation.constraints.*;
public record RegisterRequest(
        @NotBlank String username,
        @Email @NotBlank String email,
        @Size(min = 8, message = "Password must be at least 8 characters") String password
        ) {

}
