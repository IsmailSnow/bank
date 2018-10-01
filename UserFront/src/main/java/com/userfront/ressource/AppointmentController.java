package com.userfront.ressource;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.domain.Appointment;
import com.userfront.domain.User;
import com.userfront.service.AppointmentService;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createAppointment(Model model) {
		Appointment appointment = new Appointment();
		model.addAttribute("appointment", appointment);
		model.addAttribute("dateString", "");
		return "appointment";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createAppointmentPost(@ModelAttribute("appointment") @Valid Appointment appointment, BindingResult result,
			@ModelAttribute("dateString") String date, Model model, Principal principal) throws ParseException {
		
		if(result.hasErrors()) {
			if(date.isEmpty()) {
				model.addAttribute("dateError", true);
			}
			return "appointment";
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date date1 = format1.parse(date);
		appointment.setDate(date1);
		Optional<User> user = userService.findByUsername(principal.getName());
		if (user.isPresent()) {
			appointment.setUser(user.get());
			appointmentService.createAppointment(appointment);
			return "redirect:/userFront";
		} else {
			return "appointment";
		}
	}

}
