package com.userfront.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.userfront.dao.UserDao;
import com.userfront.domain.User;

@Service
public class UserSecurityService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserSecurityService.class);

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> retrivedUser = userDao.findByUsername(username);
		if (!retrivedUser.isPresent()) {
			logger.warn("Username {} not found", username);
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		// le get est safe car on est sur d'avoir l element sinon une exception va se
		// declencher avant
		return retrivedUser.get();
	}

}
