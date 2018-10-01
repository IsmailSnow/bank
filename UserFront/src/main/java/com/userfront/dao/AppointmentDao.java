package com.userfront.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userfront.domain.Appointment;

public interface AppointmentDao extends JpaRepository<Appointment, Long> {

	List<Appointment> findAll();

}
