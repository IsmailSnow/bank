package com.userfront.service;

import java.util.Optional;
import java.util.Set;

import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;

public interface UserService {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	boolean checkUserExists(String email, String username);

	boolean checkUsernameExists(String username);

	boolean checkEmailExists(String email);

	void saveUser(User user);

	User create(User user, Set<UserRole> userRoles);
}
