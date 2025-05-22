package com.example.ktcspringschedulercrud.controller;

import com.example.ktcspringschedulercrud.dto.ScheduleRequestDto;
import com.example.ktcspringschedulercrud.dto.ScheduleResponseDto;
import com.example.ktcspringschedulercrud.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> create(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.createSchedule(scheduleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleResponseDto);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    @GetMapping
    public List<ScheduleResponseDto> searchSchedules(@RequestParam(required = false) String updatedStart,
                                                     @RequestParam(required = false) String updatedEnd,
                                                     @RequestParam(required = false) String username) {
        return scheduleService.searchSchedules(updatedStart, updatedEnd, username);
    }
}
