package com.userfront.ressource;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserService userService;

	@RequestMapping("/primaryAccount")
	public String primaryAccount(Principal principal, Model model) {
		Optional<User> user = userService.findByUsername(principal.getName());
		if (user.isPresent()) {
			PrimaryAccount primaryAccount = user.get().getPrimaryAccount();
			model.addAttribute("primaryAccount", primaryAccount);
			return "primaryAccount";
		} else {
			SecurityContextHolder.clearContext();
			return "redirect:/index";
		}

	}

	@RequestMapping("/savingsAccount")
	public String savingsAccount(Model model, Principal principal) {
		Optional<User> user = userService.findByUsername(principal.getName());
		if (user.isPresent()) {
			SavingsAccount savingsAccount = user.get().getSavingsAcount();
			model.addAttribute("savingsAccount", savingsAccount);
			return "savingsAccount";
		} else {
			SecurityContextHolder.clearContext();
			return "redirect:/index";
		}
	}

}
