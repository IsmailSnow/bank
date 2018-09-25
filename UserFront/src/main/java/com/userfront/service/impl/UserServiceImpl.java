package com.userfront.service.impl;

import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.userfront.dao.RoleDao;
import com.userfront.dao.UserDao;
import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;
import com.userfront.service.AccountService;
import com.userfront.service.RoleService;
import com.userfront.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AccountService accountService;

	@Override
	public Optional<User> findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public boolean checkUserExists(String username, String email) {
		if (checkUsernameExists(username) || checkEmailExists(email)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkEmailExists(String email) {
		if (findByEmail(email).isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkUsernameExists(String username) {
		if (findByUsername(username).isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public void saveUser(User user) {
		userDao.save(user);
	}

	@Override
	public User create(User user, Set<UserRole> userRoles) {
		Optional<User> retrievedUser = userDao.findByUsername(user.getFirstName());
		if (retrievedUser.isPresent()) {
			logger.info("User with Username {} already exist. Nothing will be done", user.getUsername());
		} else {
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);
			roleService.createRoleForUser(user, userRoles);
			user.setPrimaryAccount(accountService.createPrimaryAccount());
			user.setSavingsAcount(accountService.createSavingsAccount());
			userDao.save(user);
			retrievedUser = Optional.ofNullable(user);
		}
		// on est sur que le .get is working because we passe the user that we save in a
		// new optional that will be assigned to the retrieved user
		return retrievedUser.get();

	}

}
