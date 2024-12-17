package com.example.authentication.dtos;

import lombok.Data;

@Data
public class signUpRequest {
    private String name;
    private  String email;
    private String password;
}
