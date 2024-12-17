package com.example.authentication.Models;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class demo {
    public static void main(String[] args) {
        String bpwd="$2a$10$CAzJWEUiM.TXEKmTYTnSX./QA6ysO9UmV90JnTeCWZp4GU79JBuDO";
        String pwd="abc";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.matches(pwd,bpwd));

    }
}
