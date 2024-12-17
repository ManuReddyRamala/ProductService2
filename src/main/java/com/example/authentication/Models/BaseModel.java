package com.example.authentication.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
@Data
@MappedSuperclass
public class BaseModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date created_at;
}
