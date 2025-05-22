package com.example.ktcspringschedulercrud.repository;

import com.example.ktcspringschedulercrud.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s " + "WHERE s.updatedAt BETWEEN :start AND :end " + "AND (:name IS NULL OR s.user.name = :name) " +
            "ORDER BY s.updatedAt DESC")
    List<Schedule> searchByConditions(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("name") String name);

}