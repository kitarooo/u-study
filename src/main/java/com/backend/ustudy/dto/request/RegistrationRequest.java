package com.backend.ustudy.dto.request;

import com.backend.ustudy.entity.enums.ClientRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {
    String firstname;
    String phoneNumber;
    String email;
    ClientRole role;
    String password;
    String confirmPassword;
}
