package com.userfront.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userfront.utils.validation.target.EmailValidation;
import com.userfront.utils.validation.target.Phone;

@Entity
public class Recipient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty(message = "you must enter a name !")
	private String name;
	
	@EmailValidation(message = "you must enter a valid email !")
	private String email;
	@Phone(message = "you must enter a valid number phone!")
	private String phone;
	@NotEmpty(message = "you must enter an account !")
	private String accountNumber;
	@NotEmpty(message = "you must enter a discription !")
	private String description;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

}
