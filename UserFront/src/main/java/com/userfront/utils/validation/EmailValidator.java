package com.userfront.utils.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.userfront.utils.validation.target.EmailValidation;

public class EmailValidator implements ConstraintValidator<EmailValidation, String> {

	@Override
	public boolean isValid(String email, ConstraintValidatorContext arg1) {
		// TODO Auto-generated method stub
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

}
