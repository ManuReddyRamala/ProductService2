package com.example.authentication.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;
@Data
@Entity
public class Token extends BaseModel{
    private String value;
    private Date expiry;
    @ManyToOne
    private User user;
    private boolean is_valid;
}
