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

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.domain.dto.DepositDto;
import com.userfront.service.AccountService;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;

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
	
	@RequestMapping(value="/deposit",method=RequestMethod.GET)
	public String deposit(Model model) {
		DepositDto dto = new DepositDto();
		dto.setAccountType("");
		dto.setAmount("");
		model.addAttribute("dto",dto);
		return "deposit";
	}
	
	@RequestMapping(value="deposit",method=RequestMethod.POST)
	public String depositPost(@ModelAttribute("dto") @Valid DepositDto dto,BindingResult result,Model model,Principal principal) {
		if(result.hasErrors()) {
	        // ajouter le mesasge d'erreur ici 
			return "deposit";
		}
		accountService.deposit(dto.getAccountType(),Double.parseDouble(dto.getAmount()),principal);
		return "redirect:/userFront";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
