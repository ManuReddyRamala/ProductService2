package com.example.authentication.dtos;

import lombok.Data;

@Data
public class SendEmailDto {

    private String to;
    private String from;
    private String subject;
    private String body;

}
