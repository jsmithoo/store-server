package com.example.adapters.inbound.controller;

import com.example.apis.AuthControllerApi;
import com.example.application.JwtService;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.UserLoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*")
@RequestMapping(value = "/api/v1.0/auth")
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerApi {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLogin) {
        return ResponseEntity.ok(authService.login(userLogin));
    }

    /*@GetMapping("/validate")
    public String validateToken(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", ""); // Remove Bearer prefix
        boolean isValid = jwtService.validateToken(token);
        return isValid ? "Token is valid" : "Token is invalid";
    }*/
}
