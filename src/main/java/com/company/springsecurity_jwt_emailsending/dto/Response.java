package com.company.springsecurity_jwt_emailsending.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String message;
    private boolean success;
    private Object object;

    public Response(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
