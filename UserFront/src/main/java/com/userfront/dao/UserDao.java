package com.userfront.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userfront.domain.User;

public interface UserDao extends JpaRepository<User, Long> {
        Optional<User> findByUsername(String usermane);
        Optional<User> findByEmail(String email);
}
