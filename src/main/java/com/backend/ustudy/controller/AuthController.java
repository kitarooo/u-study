package com.backend.ustudy.controller;

import com.backend.ustudy.dto.request.LoginRequest;
import com.backend.ustudy.dto.request.RegistrationRequest;
import com.backend.ustudy.dto.response.AuthenticationResponse;
import com.backend.ustudy.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public String registration(@RequestBody RegistrationRequest request) {
        return authService.registration(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/send")
    public String resend(@RequestHeader("Authorization") String token) {
        return authService.resend(token);
    }

    @PostMapping("/confirm")
    public String confirm(@RequestHeader("Authorization") String token, @RequestBody Integer code) {
        return authService.registerConfirm(token,code);
    }
}
