package com.userfront.domain.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.userfront.domain.Recipient;

public class RecipientDto {

	@NotEmpty(message = "please choose a recipient name !.")
	private String recipientName;
	@NotEmpty(message = "please choose an account name !.")
	private String accountType;
	@NotEmpty(message="please choose an amout to send !.")
	private String amount;
	
	private List<String> recipientList;

	public RecipientDto() {
		super();
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public List<String> getRecipientList() {
		return recipientList;
	}

	public void setRecipientList(List<String> recipientList) {
		this.recipientList = recipientList;
	}
	

}
