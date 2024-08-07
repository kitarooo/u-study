package com.backend.ustudy.service.impl;

import com.backend.ustudy.dto.request.LoginRequest;
import com.backend.ustudy.dto.request.RegistrationRequest;
import com.backend.ustudy.dto.response.AuthenticationResponse;
import com.backend.ustudy.entity.User;
import com.backend.ustudy.entity.enums.Role;
import com.backend.ustudy.entity.enums.Status;
import com.backend.ustudy.exception.*;
import com.backend.ustudy.repository.UserRepository;
import com.backend.ustudy.security.jwt.JwtService;
import com.backend.ustudy.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JavaMailSender mailSender;

    @Value("${gmail}")
    private String gmail;

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword()) && user.getPhoneNumber().equals(request.getPhoneNumber())) {
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        } else {
            throw new IncorrectDataException("Данные введены неправильно!");
        }
    }

    @Override
    public String registration(RegistrationRequest request) {
        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new UserAlreadyExistException("Пользователь с phone number: " + request.getPhoneNumber() + " уже существует!");
        }

        User user = User.builder()
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_UNDEFINED)
                .clientRole(request.getRole())
                .phoneNumber(request.getPhoneNumber())
                .status(Status.INACTIVE)
                .build();

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IncorrectPasswordsException("Пароли не совпадают!");
        } else {
            Random random = new Random();
            Integer code = random.nextInt(1000, 9999);
            user.setCode(code);
            userRepository.save(user);
            sendEmail(code, user.getEmail());
            return "Регистрация прошла успешно!";
        }
    }

    @Override
    public void sendEmail(Integer code, String mail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(gmail);
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setText("Ваш код для подтверждения регистрации: " + code);
        mailSender.send(simpleMailMessage);
    }

    @Override
    public String resend(String token) {
        Long userId = jwtService.extractUserId(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
        Random random = new Random();
        Integer code = random.nextInt(1000, 9999);
        user.setCode(code);
        userRepository.save(user);

        sendEmail(code,user.getEmail());
        return "Новый код был отправлен!";
    }

    @Override
    public String registerConfirm(String token, Integer code) {
        Long userId = jwtService.extractUserId(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
        if (user.getCode().equals(code)) {
            user.setCode(null);
            user.setStatus(Status.ACTIVE);
            user.setRole(Role.ROLE_USER);
            userRepository.save(user);
            return "Вы успешно подтвердили свой аккаунт!";
        } else {
            throw new IncorrectCodeException("Введенный код неверный!");
        }
    }


}
