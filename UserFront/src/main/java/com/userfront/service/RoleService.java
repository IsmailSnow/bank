package com.userfront.service;

import java.util.Set;

import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;

public interface RoleService {

	void createRoleForUser(User user,Set<UserRole> userRoles);
}
