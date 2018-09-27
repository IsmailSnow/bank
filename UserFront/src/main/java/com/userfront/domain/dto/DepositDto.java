package com.userfront.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class DepositDto {

	@Min(value = 0L, message = "The amount must be greater than zero")
	private String amount;
	@NotEmpty(message="no account is selected, please select your account")
	private String accountType;

	public DepositDto() {
		super();
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

}
