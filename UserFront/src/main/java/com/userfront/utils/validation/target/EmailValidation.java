package com.userfront.utils.validation.target;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.userfront.utils.validation.EmailValidator;


@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidation {
 
     
    String message() default "{Email}";
     
    Class<?>[] groups() default {};
     
    Class<? extends Payload>[] payload() default {};
      
}