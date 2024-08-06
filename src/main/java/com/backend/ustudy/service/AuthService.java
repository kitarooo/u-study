package com.backend.ustudy.service;

import com.backend.ustudy.dto.request.LoginRequest;
import com.backend.ustudy.dto.request.RegistrationRequest;
import com.backend.ustudy.dto.response.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse login(LoginRequest request);
    String registration(RegistrationRequest request);
    void sendEmail(Integer code, String mail);
    String registerConfirm(String token, Integer code);
    String resend(String token);
}
