package com.userfront.domain;

import java.math.BigDecimal;
import java.util.Date;

public class PrimaryTransaction {

	private Long id;
	private Date date;
	private String description;
	private String type;
	private String status;
	private double amout;
	private BigDecimal availableBalance;

	private PrimaryAccount primaryAccount;

	public PrimaryTransaction() {
		super();
	}

	public PrimaryTransaction(Date date, String description, String type, String status, double amout,
			BigDecimal availableBalance, PrimaryAccount primaryAccount) {
		super();
		this.date = date;
		this.description = description;
		this.type = type;
		this.status = status;
		this.amout = amout;
		this.availableBalance = availableBalance;
		this.primaryAccount = primaryAccount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getAmout() {
		return amout;
	}

	public void setAmout(double amout) {
		this.amout = amout;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public PrimaryAccount getPrimaryAccount() {
		return primaryAccount;
	}

	public void setPrimaryAccount(PrimaryAccount primaryAccount) {
		this.primaryAccount = primaryAccount;
	}

}
