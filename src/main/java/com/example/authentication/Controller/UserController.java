package com.example.authentication.Controller;

import com.example.authentication.Models.Token;
import com.example.authentication.Models.User;
import com.example.authentication.Services.UserService;
import com.example.authentication.dtos.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
//    UserController(UserService userService) {
//        this.userService = userService;
//    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody signUpRequest request) throws JsonProcessingException {
        User u= userService.signUp(request.getEmail(),request.getPassword(), request.getName());
        return UserDto.from(u);
    }
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        Token t=userService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());
        LoginResponseDto response=new LoginResponseDto();
        if(t!=null)
        {

            response.setTokenValue(t.getValue());
            response.setStatus("Success");
            response.setExpiry( t.getUser().getCreated_at());
            response.setActive(true);
            return response;

        }
        response.setStatus("Success");
        return response;
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        try{
            userService.logout(logoutRequestDto.getToken());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable String token) {
       User u= userService.validateToken(token);
       if(u==null)
       {
           return null;
//           throw new RuntimeException("Invalid Token");
       }
       return UserDto.from(u);
    }



}
