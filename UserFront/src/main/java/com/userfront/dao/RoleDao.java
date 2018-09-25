package com.userfront.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userfront.domain.security.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(String name);

}
