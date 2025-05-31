package com.example.ktcspringschedulercrud.repository;

import com.example.ktcspringschedulercrud.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ScheduleRepository {
    Optional<Schedule> findById(Long id);

    Schedule save(Schedule schedule);

    void delete(Schedule schedule);

    Page<Schedule> searchByConditions(LocalDateTime start, LocalDateTime end, Long userId, Pageable pageable);
}