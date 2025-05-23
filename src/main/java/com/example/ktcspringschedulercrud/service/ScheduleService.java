package com.example.ktcspringschedulercrud.service;

import com.example.ktcspringschedulercrud.dto.ScheduleRequestDto;
import com.example.ktcspringschedulercrud.dto.ScheduleResponseDto;
import com.example.ktcspringschedulercrud.entity.Schedule;
import com.example.ktcspringschedulercrud.entity.User;
import com.example.ktcspringschedulercrud.exception.InvalidPasswordException;
import com.example.ktcspringschedulercrud.exception.ScheduleNotFoundException;
import com.example.ktcspringschedulercrud.exception.UserNotFoundException;
import com.example.ktcspringschedulercrud.repository.ScheduleRepository;
import com.example.ktcspringschedulercrud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        User user = userRepository.findById(scheduleRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(scheduleRequestDto.getUserId().toString()));

        Schedule schedule = new Schedule(user, scheduleRequestDto.getTitle(), scheduleRequestDto.getTask(), scheduleRequestDto.getPassword());
        Schedule saved = scheduleRepository.save(schedule);
        return toResponse(saved);
    }

    public ScheduleResponseDto getScheduleById(Long id) {
        return toResponse(scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException(id.toString())));
    }

    public Page<ScheduleResponseDto> searchSchedules(String updatedStart, String updatedEnd, Long userId, int page, int size) {
        try {
            LocalDateTime start = (updatedStart != null) ? LocalDate.parse(updatedStart).atStartOfDay() : LocalDateTime.MIN;
            LocalDateTime end = (updatedEnd != null) ? LocalDate.parse(updatedEnd).atTime(LocalTime.MAX) : LocalDateTime.now();
            Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());

            Page<Schedule> schedules = scheduleRepository.searchByConditions(start, end, userId, pageable);
            return schedules.map(this::toResponse);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("invalid format: {YYYY-MM-DD} expected");
        }
    }


    public ScheduleResponseDto updateScheduleById(Long id, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException(id.toString()));

        if (!schedule.getPassword().equals(scheduleRequestDto.getPassword())) {
            throw new InvalidPasswordException(scheduleRequestDto.getPassword());
        }

        if (scheduleRequestDto.getTitle() != null) schedule.setTitle(scheduleRequestDto.getTitle());
        if (scheduleRequestDto.getTask() != null) schedule.setTask(scheduleRequestDto.getTask());

        Schedule updated = scheduleRepository.save(schedule);

        User user = updated.getUser();
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return toResponse(updated);
    }

    public void deleteScheduleById(Long id, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException(id.toString()));

        if (!schedule.getPassword().equals(scheduleRequestDto.getPassword())) {
            throw new InvalidPasswordException(scheduleRequestDto.getPassword());
        }

        User user = schedule.getUser();

        scheduleRepository.delete(schedule);

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
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
