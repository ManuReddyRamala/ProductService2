package com.example.authentication.dtos;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
