package com.example.ktcspringschedulercrud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
}