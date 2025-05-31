package com.example.ktcspringschedulercrud.repository;

import com.example.ktcspringschedulercrud.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    User save(User user);
}