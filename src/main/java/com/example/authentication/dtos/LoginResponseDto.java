package com.example.authentication.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class LoginResponseDto {
    String tokenValue;
    String status;
    Date expiry;
    boolean active;
}
