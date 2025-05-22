package com.example.ktcspringschedulercrud.repository;

import com.example.ktcspringschedulercrud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
