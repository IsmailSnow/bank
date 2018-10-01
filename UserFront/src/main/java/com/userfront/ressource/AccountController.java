package com.userfront.ressource;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
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

import com.userfront.dao.UserDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.SavingsTransaction;
import com.userfront.domain.User;
import com.userfront.domain.dto.DepositDto;
import com.userfront.service.AccountService;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {

	private static final String SAVINGS = "Savings";

	private static final String PRIMARY = "Primary";

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserDao userDao;

	@RequestMapping("/primaryAccount")
	public String primaryAccount(Principal principal, Model model) {

		Optional<User> user = userService.findByUsername(principal.getName());
		if (user.isPresent()) {
			List<PrimaryTransaction> primaryTransactionList = user.get().getPrimaryAccount()
					.getPrimaryTransactionList();
			PrimaryAccount primaryAccount = user.get().getPrimaryAccount();
			model.addAttribute("primaryAccount", primaryAccount);
			model.addAttribute("primaryTransactionList", primaryTransactionList);
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
			List<SavingsTransaction> savingsTransactionList = user.get().getSavingsAcount().getSavingsTransactionList();
			model.addAttribute("savingsAccount", savingsAccount);
			model.addAttribute("savingsTransactionList", savingsTransactionList);
			return "savingsAccount";
		} else {
			SecurityContextHolder.clearContext();
			return "redirect:/index";
		}
	}

	@RequestMapping(value = "/deposit", method = RequestMethod.GET)
	public String deposit(Model model) {
		DepositDto dto = new DepositDto();
		dto.setAccountType("");
		dto.setAmount("");
		model.addAttribute("dto", dto);
		return "deposit";
	}

	@RequestMapping(value = "deposit", method = RequestMethod.POST)
	public String depositPost(@ModelAttribute("dto") @Valid DepositDto dto, BindingResult result, Model model,
			Principal principal) {
		if (result.hasErrors()) {
			// ajouter le mesasge d'erreur ici
			return "deposit";
		}
		accountService.deposit(dto.getAccountType(), Double.parseDouble(dto.getAmount()), principal);
		return "redirect:/home";
	}

	@RequestMapping(value = "/withdraw", method = RequestMethod.GET)
	public String withDraw(Model model) {
		DepositDto dto = new DepositDto();
		dto.setAccountType("");
		dto.setAmount("");
		model.addAttribute("dto", dto);
		return "withdraw";
	}

	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public String withdrawPOST(@ModelAttribute("dto") @Valid DepositDto dto, BindingResult result, Model model,
			Principal principal) {
		if (result.hasErrors()) {
			return "withdraw";
		}

		BigDecimal primaryAccountBalance = BigDecimal.ZERO;
		BigDecimal savingsAccountBalance = BigDecimal.ZERO;
		Optional<User> user = userDao.findByUsername(principal.getName());
		if (user.isPresent()) {
			primaryAccountBalance = primaryAccountBalance.add(user.get().getPrimaryAccount().getAccountBalance());
			savingsAccountBalance = savingsAccountBalance.add(user.get().getSavingsAcount().getAccountBalance());
		}
		if (dto.getAccountType().equals(SAVINGS)
				&& (BigDecimal.valueOf(Double.parseDouble(dto.getAmount())).compareTo(savingsAccountBalance) > 0)) {
			model.addAttribute("amountError", true);
			return "withdraw";
		}
		if (dto.getAccountType().equals(PRIMARY)
				&& (BigDecimal.valueOf(Double.parseDouble(dto.getAmount())).compareTo(primaryAccountBalance) > 0)) {
			model.addAttribute("amountError", true);
			return "withdraw";
		}

		accountService.withdraw(dto.getAccountType(), Double.parseDouble(dto.getAmount()), principal);
		return "redirect:/home";

	}

}
