package com.api.school.validator;

public interface MyValidator {
	// method to check phone number
	default boolean isValidPhoneNumber(String phone) {
		boolean bRet = true;
		
		//Phone Validator
		if ((phone.length() < 10) || (phone.startsWith("0")) || (phone.matches("[a-zA-Z]+"))) {
			bRet = false;
		} else {
			bRet = true;
		}
		return bRet;

	}

	//Name and Specialization Validator
	default boolean isValidName(String name) {
		boolean bRet = true;

		name = name.trim();
		if (name.equals("") || name.equals(" ") || (name.length() <= 0)) {
			bRet = false;
		}
		return bRet;
	}

	//Email Validator
	default boolean isValidEmail(String email) {
		boolean bRet = true;

		email = email.trim();

		if (email.equals("") || email.equals(" ") || email.length() <= 0 || !email.endsWith(".com") ||  !email.contains("@"))
		{
			bRet = false;
		}

		return bRet;
	}

}
