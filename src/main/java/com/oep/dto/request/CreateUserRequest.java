package com.oep.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {

    private String firstName;
    private String lastName;

    @Email
    private String email;
    private String password;
    private String repeatPassword;
    private String phoneNumber;
    private String address;

}
