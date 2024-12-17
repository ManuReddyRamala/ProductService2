package com.example.authentication.Models;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Roles extends BaseModel{
    private  String role;

}
