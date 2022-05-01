package com.company.springsecurity_jwt_emailsending.controller;

import com.company.springsecurity_jwt_emailsending.dto.LoginDto;
import com.company.springsecurity_jwt_emailsending.dto.RegisterDto;
import com.company.springsecurity_jwt_emailsending.dto.Response;
import com.company.springsecurity_jwt_emailsending.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
   private final AuthService authService;
@Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){
        Response response=authService.registerUser(registerDto);
        return ResponseEntity.status(response.isSuccess()? 201:409).body(response);
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<?>verifyEmail(@RequestParam String emailCode,@RequestParam String email){
    Response response=authService.verifyEmail(emailCode,email);
    return ResponseEntity.status(response.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
    Response response=authService.login(loginDto);
    return ResponseEntity.status(response.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(response);
    }
}