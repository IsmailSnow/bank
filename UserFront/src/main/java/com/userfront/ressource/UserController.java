package com.userfront.ressource;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.domain.User;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(Principal principal, Model model) {
		Optional<User> user = userService.findByUsername(principal.getName());
		if (user.isPresent()) {
			model.addAttribute("user", user.get());
			return "profile";
		} else {
			SecurityContextHolder.clearContext();
			return "index";
		}
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String profilePost(@ModelAttribute("user") @Valid User newUser,BindingResult result , Model model) {
        if(result.hasErrors()) {
        	return "profile";
        }
		Optional<User> user = userService.findByUsername(newUser.getUsername());
		if (user.isPresent()) {
			user.get().setUsername(newUser.getUsername());
			user.get().setUsername(newUser.getFirstName());
			user.get().setUsername(newUser.getLastName());
			user.get().setUsername(newUser.getEmail());
			user.get().setUsername(newUser.getPhone());
			model.addAttribute("user", user.get());
			userService.saveUser(user.get());
			return "profile";
		} else {
			return "index";
		}

	}

}
