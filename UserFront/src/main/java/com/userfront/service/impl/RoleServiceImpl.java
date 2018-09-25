package com.userfront.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.RoleDao;
import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;
import com.userfront.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public void createRoleForUser(User user, Set<UserRole> userRoles) {
		for (UserRole userRole : userRoles) {
			roleDao.save(userRole.getRole());
		}
		user.getUserRoles().addAll(userRoles);

	}

}
