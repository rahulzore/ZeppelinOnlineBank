package com.rahul.zeppelinonlinebank.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AmountValidator implements ConstraintValidator<AmountConstraint, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		return value.matches("[0-9]+") && value!=null;
	}

}
