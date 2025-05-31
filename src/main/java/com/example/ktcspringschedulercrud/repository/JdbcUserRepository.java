package com.example.ktcspringschedulercrud.repository;

import com.example.ktcspringschedulercrud.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime());
            user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            return user;
        };
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT id, name, email, registered_at, updated_at FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper(), id);

        return users.stream().findFirst();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            String sql = "INSERT INTO users (name, email, registered_at, updated_at) VALUES (?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setTimestamp(3, Timestamp.valueOf(user.getRegisteredAt()));
                ps.setTimestamp(4, Timestamp.valueOf(user.getUpdatedAt()));
                return ps;
            }, keyHolder);

            Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            user.setId(generatedId);
        } else {
            String sql = "UPDATE users SET name = ?, email = ?, updated_at = ? WHERE id = ?";
            jdbcTemplate.update(sql, user.getName(), user.getEmail(), Timestamp.valueOf(user.getUpdatedAt()), user.getId());
        }
        return user;
    }
}