package com.userfront.ressource;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.userfront.domain.Recipient;
import com.userfront.domain.User;
import com.userfront.domain.dto.RecipientDto;
import com.userfront.domain.dto.TransferDto;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/transfer")
public class TransferController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/betweenAccounts", method = RequestMethod.GET)
	public String betweenAccounts(Model model) {
		TransferDto dto = new TransferDto();
		dto.setTransferFrom("");
		dto.setTransferTo("");
		dto.setAmount("");
		model.addAttribute("dto", dto);
		return "betweenAccount";
	}

	@RequestMapping(value = "/betweenAccounts", method = RequestMethod.POST)
	public String betweenAccountsPost(@ModelAttribute("dto") @Valid TransferDto dto, BindingResult result, Model model,
			Principal principal) throws Exception {

		if (result.hasErrors()) {
			return "betweenAccount";
		}
		Optional<User> retrievedUser = userService.findByUsername(principal.getName());

		if (!checkIfThereisEnoughMoney(dto.getTransferFrom(),dto.getAmount(), retrievedUser)) {
			model.addAttribute("invalidTransfer", true);
			return "betweenAccount";
		}

		if (retrievedUser.isPresent()) {
			User user = retrievedUser.get();
			transactionService.betweenAccountsTransfer(dto.getTransferFrom(), dto.getTransferTo(), dto.getAmount(),
					user.getPrimaryAccount(), user.getSavingsAcount());
			return "redirect:/home";
		} else {
			return "redirect:/index";
		}

	}

	@RequestMapping(value = "/recipient", method = RequestMethod.GET)
	public String recipient(Model model, Principal principal) {
		List<Recipient> recipientList = transactionService.findRecipientList(principal);
		Recipient recipient = new Recipient();
		model.addAttribute("recipientList", recipientList);
		model.addAttribute("recipient", recipient);
		return "recipient";
	}

	@RequestMapping(value = "/recipient/save", method = RequestMethod.POST)
	public String recipientPost(@ModelAttribute("recipient") @Valid Recipient recipient, BindingResult result,Model model,
			Principal principal) {

		if (result.hasErrors()) {
			return "recipient";
		}

		Optional<User> user = userService.findByUsername(principal.getName());
		if (user.isPresent()) {
			recipient.setUser(user.get());
			transactionService.saveRecipient(recipient);
			return "redirect:/transfer/recipient";
		} else {
			return "recipient";
		}

	}

	@RequestMapping(value = "/recipient/edit", method = RequestMethod.GET)
	public String recipientEdit(@RequestParam(value = "recipientName") String recipientName, Model model,
			Principal principal) {
		Recipient recipient = transactionService.findRecipientByName(recipientName);
		List<Recipient> recipientList = transactionService.findRecipientList(principal);
		model.addAttribute("recipientList", recipientList);
		model.addAttribute("recipient", recipient);
		return "recipient";
	}

	@RequestMapping(value = "/recipient/delete", method = RequestMethod.GET)
	@Transactional
	public String recipientDelete(@RequestParam(value = "recipientName") String recipientName, Model model,
			Principal principal) {

		transactionService.deleteRecipientByName(recipientName);
		List<Recipient> recipientList = transactionService.findRecipientList(principal);
		Recipient recipient = new Recipient();
		model.addAttribute("recipient", recipient);
		model.addAttribute("recipientList", recipientList);
		return "recipient";
	}

	@RequestMapping(value = "/toSomeoneElse", method = RequestMethod.GET)
	public String toSomeoneElse(Model model, Principal principal) {
		List<Recipient> recipientList = transactionService.findRecipientList(principal);
		RecipientDto dto = new RecipientDto();
		dto.setAmount("");
		dto.setRecipientList(recipientList.stream().map(Recipient::getName).collect(Collectors.toList()));
		model.addAttribute("dto", dto);
		return "toSomeoneElse";
	}

	@RequestMapping(value = "/toSomeoneElse", method = RequestMethod.POST)
	public String toSomeoneElsePost(@ModelAttribute("dto") @Valid RecipientDto dto,BindingResult result,Model model,
			Principal principal) {
		Optional<User> user = userService.findByUsername(principal.getName());
		
		if(result.hasErrors()) {
			return "toSomeoneElse";
		}
		if (!checkIfThereisEnoughMoney(dto.getAccountType(),dto.getAmount(), user)) {
			model.addAttribute("invalidTransfer", true);
			return "toSomeoneElse";
		}
		if (user.isPresent()) {
			Recipient recipient = transactionService.findRecipientByName(dto.getRecipientName());
			transactionService.toSomeoneElseTransfer(recipient, dto.getAccountType(), dto.getAmount(), user.get().getPrimaryAccount(),
					user.get().getSavingsAcount());
			return "redirect:/home";
		} else {
			return "toSomeoneElse";
		}
	}

	private boolean checkIfThereisEnoughMoney(String transferFrom,String amout, Optional<User> user) {
		BigDecimal balance = BigDecimal.ZERO;
		if (user.isPresent()) {
			if (transferFrom.equals("Primary")) {
				balance = balance.add(user.get().getPrimaryAccount().getAccountBalance());
			} else {
				balance = balance.add(user.get().getSavingsAcount().getAccountBalance());
			}
		}
		return balance.compareTo(BigDecimal.valueOf(Double.parseDouble(amout))) > 0;
	}

}
