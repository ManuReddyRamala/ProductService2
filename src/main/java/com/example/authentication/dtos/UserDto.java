package com.example.authentication.dtos;

import com.example.authentication.Models.Roles;
import com.example.authentication.Models.User;
import lombok.Data;

import java.util.List;
@Data
public class UserDto {
    private String name;
    private String email;
    private List<Roles> roles;

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

}
