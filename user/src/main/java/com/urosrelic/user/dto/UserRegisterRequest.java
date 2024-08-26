package com.urosrelic.user.dto;

import com.urosrelic.user.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    private String email;

    // validation for enum values
    private Role role;

    @NotBlank(message = "Password is mandatory")
    private String password;
}