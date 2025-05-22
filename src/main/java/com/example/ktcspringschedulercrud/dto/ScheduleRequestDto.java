package com.example.ktcspringschedulercrud.dto;

import lombok.Data;

@Data
public class ScheduleRequestDto {
    private Long userId;
    private String password;
    private String title;
    private String task;
}
