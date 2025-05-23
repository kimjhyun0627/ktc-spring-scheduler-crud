package com.example.ktcspringschedulercrud.controller;

import com.example.ktcspringschedulercrud.dto.ScheduleRequestDto;
import com.example.ktcspringschedulercrud.dto.ScheduleResponseDto;
import com.example.ktcspringschedulercrud.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.createSchedule(scheduleRequestDto);
        return ResponseEntity.created(URI.create("/schedule/" + scheduleResponseDto.getId())).body(scheduleResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> searchSchedule(@RequestParam(required = false) String updatedStart,
                                                                    @RequestParam(required = false) String updatedEnd,
                                                                    @RequestParam(required = false) Long userId) {
        List<ScheduleResponseDto> schedules = scheduleService.searchSchedule(updatedStart, updatedEnd, userId);
        return ResponseEntity.ok().body(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok().body(scheduleService.getScheduleById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateScheduleById(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto updated = scheduleService.updateScheduleById(id, scheduleRequestDto);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        scheduleService.deleteScheduleById(id, scheduleRequestDto);
        return ResponseEntity.noContent().build();
    }
}
