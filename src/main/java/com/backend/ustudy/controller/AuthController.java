package com.backend.ustudy.controller;

import com.backend.ustudy.dto.request.LoginRequest;
import com.backend.ustudy.dto.request.RegistrationRequest;
import com.backend.ustudy.dto.response.AuthenticationResponse;
import com.backend.ustudy.exception.handler.ExceptionResponse;
import com.backend.ustudy.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    @Operation(summary = "Регистрация для клиента", description = "Ендпоинт для регистрации!",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "400", description = "User already exist exception!"
                    )
            })
    public String registration(@RequestBody RegistrationRequest request) {
        return authService.registration(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Авторизация для всех пользователей", description = "Ендпоинт для авторизации и выдачи токена!",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "400", description = "Incorrect Data Exception!"
                    )
            })
    public AuthenticationResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/send")
    @Operation(summary = "Отправка кода на почту", description = "Ендпоинт для повторной отправки кода",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "404", description = "User not found"
                    )
            })
    public String resend(@RequestHeader("Authorization") String token) {
        return authService.resend(token);
    }

    @PostMapping("/confirm")
    @Operation(summary = "Добавить комментарий", description = "Ендпоинт для добавления комментария!",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "404", description = "User not found!"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "404", description = "Invalid code!")
            })
    public String confirm(@RequestHeader("Authorization") String token, @RequestBody Integer code) {
        return authService.registerConfirm(token,code);
    }
}
