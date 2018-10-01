package com.userfront.domain.dto;

import javax.validation.constraints.Min;

public class TransferDto {

	private String transferFrom;
	private String transferTo;
	@Min(value = 0L, message = "The amount must be greater than zero")
	private String amount;

	public TransferDto() {
		super();
	}

	public String getTransferFrom() {
		return transferFrom;
	}

	public void setTransferFrom(String transferFrom) {
		this.transferFrom = transferFrom;
	}

	public String getTransferTo() {
		return transferTo;
	}

	public void setTransferTo(String transferTo) {
		this.transferTo = transferTo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
