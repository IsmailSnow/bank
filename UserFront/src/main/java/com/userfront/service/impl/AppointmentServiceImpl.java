package com.userfront.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.AppointmentDao;
import com.userfront.domain.Appointment;
import com.userfront.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	final private static Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	@Autowired
	private AppointmentDao appointmentDao;

	@Override
	public Appointment createAppointment(Appointment appointment) {
		return appointmentDao.save(appointment);
	}

	@Override
	public List<Appointment> findAll() {
		return appointmentDao.findAll();
	}

	@Override
	public Optional<Appointment> findAppointment(Long id) {
		return appointmentDao.findById(id);
	}

	@Override
	public void confirmAppointment(Long id) {
		Optional<Appointment> appointment = findAppointment(id);
		if (appointment.isPresent()) {
			appointment.get().setConfirmed(true);
			appointmentDao.save(appointment.get());
			logger.debug("the confirmation of the appointment is succeced");
		} else {
			logger.debug("the confirmation failed !");
			throw new IllegalArgumentException("this actual appointment is not found");
		}
	}

}
