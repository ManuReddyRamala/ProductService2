package com.example.authentication.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.List;
@Data
@Entity
public class User extends BaseModel {
    private String name;
    private String email;
    @Column(name = "hashed_password")
    private String hashedPassword;
    @ManyToMany
    private List<Roles> roles;
    @Column(name = "is_verified")
    private boolean isVerified;
}
