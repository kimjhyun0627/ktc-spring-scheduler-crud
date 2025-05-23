package com.example.ktcspringschedulercrud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ScheduleRequestDto {

    @NotNull
    private Long userId;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    private String task;
}
