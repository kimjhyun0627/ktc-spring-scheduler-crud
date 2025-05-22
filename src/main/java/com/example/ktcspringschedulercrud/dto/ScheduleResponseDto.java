package com.example.ktcspringschedulercrud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private Long userId;
    private String userName;
    private String title;
    private String task;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
