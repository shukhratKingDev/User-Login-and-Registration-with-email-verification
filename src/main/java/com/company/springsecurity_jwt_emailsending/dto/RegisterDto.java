package com.company.springsecurity_jwt_emailsending.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @Size(min = 3,max = 50)
    @NotNull
    private String firstName;
    @Size(min = 3,max = 50)
    @NotNull
    private String lastName;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
}
