package com.example.ktcspringschedulercrud.repository;

import com.example.ktcspringschedulercrud.entity.Schedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class JdbcScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    public JdbcScheduleRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setTitle(rs.getString("title"));
            schedule.setTask(rs.getString("task"));
            schedule.setPassword(rs.getString("password"));
            schedule.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            schedule.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

            Long userId = rs.getLong("user_id");
            userRepository.findById(userId).ifPresent(schedule::setUser);
            return schedule;
        };
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        String sql = "SELECT id, user_id, title, task, password, created_at, updated_at FROM schedule WHERE id = ?";
        List<Schedule> schedules = jdbcTemplate.query(sql, scheduleRowMapper(), id);
        return schedules.stream().findFirst();
    }

    @Override
    public Schedule save(Schedule schedule) {
        if (schedule.getId() == null) {
            String sql = "INSERT INTO schedule (user_id, title, task, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, schedule.getUser().getId());
                ps.setString(2, schedule.getTitle());
                ps.setString(3, schedule.getTask());
                ps.setString(4, schedule.getPassword());
                ps.setTimestamp(5, Timestamp.valueOf(schedule.getCreatedAt()));
                ps.setTimestamp(6, Timestamp.valueOf(schedule.getUpdatedAt()));
                return ps;
            }, keyHolder);

            Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            schedule.setId(generatedId);
        } else {
            String sql = "UPDATE schedule SET user_id = ?, title = ?, task = ?, password = ?, updated_at = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    schedule.getUser().getId(),
                    schedule.getTitle(),
                    schedule.getTask(),
                    schedule.getPassword(),
                    Timestamp.valueOf(schedule.getUpdatedAt()),
                    schedule.getId());
        }
        return schedule;
    }

    @Override
    public void delete(Schedule schedule) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, schedule.getId());
    }

    @Override
    public Page<Schedule> searchByConditions(LocalDateTime start, LocalDateTime end, Long userId, Pageable pageable) {
        StringBuilder whereSql = new StringBuilder("WHERE 1=1 ");

        List<Object> countQueryParams = new ArrayList<>();
        List<Object> dataQueryParams = new ArrayList<>();

        if (start != null) {
            whereSql.append("AND updated_at >= ? ");
            countQueryParams.add(Timestamp.valueOf(start));
            dataQueryParams.add(Timestamp.valueOf(start));
        }
        if (end != null) {
            whereSql.append("AND updated_at <= ? ");
            countQueryParams.add(Timestamp.valueOf(end));
            dataQueryParams.add(Timestamp.valueOf(end));
        }

        if (userId != null) {
            whereSql.append("AND user_id = ? ");
            countQueryParams.add(userId);
            dataQueryParams.add(userId);
        }

        String countSql = "SELECT COUNT(*) FROM schedule " + whereSql;
        Long total = jdbcTemplate.queryForObject(countSql, Long.class, countQueryParams.toArray());
        if (total == null) total = 0L;


        String dataSqlBuilder =
                "SELECT id, user_id, title, task, password, created_at, updated_at FROM schedule " + whereSql + "ORDER BY updated_at DESC " +
                        "LIMIT ? OFFSET ?";
        dataQueryParams.add(pageable.getPageSize());
        dataQueryParams.add(pageable.getOffset());

        List<Schedule> schedules = jdbcTemplate.query(dataSqlBuilder, scheduleRowMapper(), dataQueryParams.toArray());

        return new PageImpl<>(schedules, pageable, total);
    }
}