package com.example.ktcspringschedulercrud.service;

import com.example.ktcspringschedulercrud.dto.ScheduleRequestDto;
import com.example.ktcspringschedulercrud.dto.ScheduleResponseDto;
import com.example.ktcspringschedulercrud.entity.Schedule;
import com.example.ktcspringschedulercrud.entity.User;
import com.example.ktcspringschedulercrud.repository.ScheduleRepository;
import com.example.ktcspringschedulercrud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        User user = userRepository.findById(scheduleRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User id: {" + scheduleRequestDto.getUserId() + "} not found"));

        Schedule schedule = new Schedule(user, scheduleRequestDto.getTitle(), scheduleRequestDto.getTask(), scheduleRequestDto.getPassword());
        Schedule saved = scheduleRepository.save(schedule);
        return toResponse(saved);
    }

    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt")).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ScheduleResponseDto getScheduleById(Long id) {
        return toResponse(scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Schedule id: {" + id + "} not found")));
    }

    public List<ScheduleResponseDto> searchSchedules(String updatedStart, String updatedEnd, String username) {
        try {
            LocalDateTime start = (updatedStart != null) ? LocalDate.parse(updatedStart).atStartOfDay() : LocalDateTime.MIN;
            LocalDateTime end = (updatedEnd != null) ? LocalDate.parse(updatedEnd).atTime(LocalTime.MAX) : LocalDateTime.now();

            List<Schedule> schedules = scheduleRepository.searchByConditions(start, end, username);
            return schedules.stream().map(this::toResponse).collect(Collectors.toList());

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("format error: {yyyy-MM-dd} expected");
        }
    }

    private ScheduleResponseDto toResponse(Schedule schedule) {
        return new ScheduleResponseDto(schedule.getId(),
                schedule.getUser().getId(),
                schedule.getUser().getName(),
                schedule.getTitle(),
                schedule.getTask(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt());
    }
}
