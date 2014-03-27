package services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.jws.WebService;

@WebService
// Alphabetic string only no numeric value allowed like name first name , last
// name ...
public class Validation {
	protected static boolean validAlphaString(String input) {
		boolean state = false;
		String rg = "^[A-Za-z]{2,15}$";
		try {
			java.util.regex.Pattern pt = java.util.regex.Pattern.compile(rg);
			if (input != null)
				if (pt.matcher(input).matches()) {
					state = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}
	
	public static boolean validNameString(String input) {
			boolean state = false;
			String rg = "^([ \u00c0-\u01ffa-zA-Z'])+$";
			try {
				java.util.regex.Pattern pt = java.util.regex.Pattern.compile(rg);
				if (input != null)
					if (pt.matcher(input).matches()) {
						state = true;
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return state;
		}
	protected static boolean validExpiryDateString(String input) {
		boolean state = false;
		String rg = "^((0[1-9])|(1[0-2]))\\/(\\d{4})$";
		try {
			java.util.regex.Pattern pt = java.util.regex.Pattern.compile(rg);
			if (input != null)
				if (pt.matcher(input).matches()) {
					state = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}
	// Numeric string with integer value only decimal values not allowed
	public static boolean validNumericString(String input) {
		boolean state = false;
		String rg = "^[0-9]+$";
		try {
			java.util.regex.Pattern pt = java.util.regex.Pattern.compile(rg);
			if (input != null)
				if (pt.matcher(input).matches()) {
					state = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	// Alphanumeric string for user-name..... upper-case not allowed at least
	// one alphabet and should start with alphabet.
	protected static boolean validAlphaNumericString(String input) {
		boolean state = false;
		String rg = "^\\d*[a-z][a-z0-9]*$";
		try {
			java.util.regex.Pattern pt = java.util.regex.Pattern.compile(rg);
			if (input != null)
				if (pt.matcher(input).matches()) {
					state = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	protected static boolean validZipString(String input) {
		boolean state = false;
		String rg = "^\\d{5}(-\\d{4})?$";
		try {
			java.util.regex.Pattern pt = java.util.regex.Pattern.compile(rg);
			if (input != null)
				if (pt.matcher(input).matches()) {
					state = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	protected static boolean validAddressString(String input) {
		boolean state = false;
		String rg = "^\\d*[A-Za-z\\s][A-Za-z0-9\\s]*$";
		try {
			java.util.regex.Pattern pt = java.util.regex.Pattern.compile(rg);
			if (input != null)
				if (pt.matcher(input).matches()) {
					state = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	// Password with one Upper-case (A-Z) character one Lower-case (a-z)
	// character one Number (0-9) and at least one special character (@,#,$,%)
	protected static boolean validPasswordString(String input) {
		boolean state = false;
		String rg = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
		try {
			java.util.regex.Pattern pt = java.util.regex.Pattern.compile(rg);
			if (input != null)
				if (pt.matcher(input).matches()) {
					state = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	protected static boolean validEmailString(String input) {
		boolean state = false;
		String rg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		try {
			java.util.regex.Pattern pt = java.util.regex.Pattern.compile(rg);
			if (input != null)
				if (pt.matcher(input).matches()) {
					state = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	// Date format check dd/mm/yyyy

	public static boolean validDateFormatString(String input) {
		boolean state = false;
		String rg = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
		try {
			java.util.regex.Pattern pt = java.util.regex.Pattern.compile(rg);
			if (input != null)
				if (pt.matcher(input).matches()) {
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"dd/MM/yyyy");
					Date convertedDate = dateFormat.parse(input);
					System.out.println("converted date format" + convertedDate);
					Calendar currentDate = Calendar.getInstance();
					Date dateNow = currentDate.getTime();
					System.out.println("Current Date " + currentDate);
					if (convertedDate.before(dateNow)) {
						state = true;
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	// SSN Format Check XXX-XX-XXXX
	protected static boolean validSSNFormatString(String input) {
		boolean state = false;
		String rg = "^(\\d{3}-?\\d{2}-?\\d{4}|XXX-XX-XXXX)$";
		try {
			java.util.regex.Pattern pt = java.util.regex.Pattern.compile(rg);
			if (input != null)
				if (pt.matcher(input).matches()) {
					state = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}
	
	public static boolean validDecimalFormatString(String input) {
		boolean state = false;
		String rg = "^[0-9]\\d*(\\.\\d{0,2}+)?$";
		try {
			java.util.regex.Pattern pt = java.util.regex.Pattern.compile(rg);
			if (input != null)
				if (pt.matcher(input).matches()) {
					state = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}
//	public static void main(String[] args){
//		System.out.println(validDecimalFormatString("zxd"));
//	}
}