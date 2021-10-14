package by.htp.store.service.validation;

import java.util.regex.Pattern;

import by.htp.store.bean.Address;
import by.htp.store.bean.Review;
import by.htp.store.bean.UserInfo;

public class Validator {

	private static final Pattern namePattern = Pattern.compile("([а-яёА-ЯЁ]|[a-zA-Z]){2,15}");
	private static final Pattern surnamePattern = Pattern.compile("([а-яёА-ЯЁ]|[a-zA-Z]){2,15}");
	private static final Pattern emailPattern = Pattern.compile("\\w+@[\\w]+[\\.][a-zA-Z]+$");
	private static final Pattern passwordPattern = Pattern
			.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{6,12}$");
	private static final Pattern phonePattern = Pattern.compile("(\\+)\\d{12}");

	public static boolean emailAndPasswordValid(String email, String password) {

		boolean isValid = (emailPattern.matcher(email).matches() && passwordPattern.matcher(password).matches());

		return isValid;
	}

	public static boolean userDataValid(UserInfo userInfo, String password) {

		boolean isValid = (namePattern.matcher(userInfo.getName()).matches()
				&& surnamePattern.matcher(userInfo.getSurname()).matches()
				&& emailPattern.matcher(userInfo.getEmail()).matches() && passwordPattern.matcher(password).matches()
				&& phonePattern.matcher(userInfo.getPhone()).matches());

		return isValid;
	}

	public static boolean userDataValid(UserInfo userInfo) {

		boolean isValid = (namePattern.matcher(userInfo.getName()).matches()
				&& surnamePattern.matcher(userInfo.getSurname()).matches()
				&& emailPattern.matcher(userInfo.getEmail()).matches()
				&& phonePattern.matcher(userInfo.getPhone()).matches());

		return isValid;

	}

	public static boolean reviewProductValid(Review review) {

		if (review.getRating() < 1 || review.getRating() > 5 || review.getText() == null
				|| review.getText().isEmpty()) {

			return true;
		}
		return false;

	}

	public static boolean addressValid(Address address) {

		if (address.getCity() == null || address.getCity().isEmpty() || address.getStreet() == null
				|| address.getStreet().isEmpty() || address.getNumberHouse() == null
				|| address.getNumberHouse().isEmpty()) {

			return true;

		}
		return false;

	}
}
