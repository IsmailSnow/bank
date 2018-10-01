package com.userfront.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.userfront.domain.Recipient;

public interface RecipientDao extends JpaRepository<Recipient, Long> {

	@Query("SELECT u FROM Recipient u WHERE  u.user.username = :username")
	List<Recipient> findAllrecipientByUsername(@Param("username")String username);

	Recipient findByName(String recipientName);

	void deleteByName(String recipientName);
}
