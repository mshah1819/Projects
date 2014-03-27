package services;

import java.sql.SQLException;

import model.Preference;
import model.Status;
import model.User;
import databaseConnection.DataAccessObject;

public class LoginLogoutService {
	
	
	/**
	 * Sign In Function it will take username and password as parameters and
	 * will return a string with validation "true_1 || true_2 || true_3" // any
	 * one can populate as after delimiter the value denotes the type of //
	 * customer 1)Administrator 2)Premium Member and 3)Simple User // for
	 * driving the jsp to their respective content in the jsp. // returning only
	 * "false" will denote invalid login no such loginid exists // in DB //
	 * returning "Invalid Username" means username is not correct and is not //
	 * upto the username policy. // returning "Invalid Password" means password
	 * is not correct and is not // upto the password policy.
	 * 
	 * @param username
	 * @param password
	 * @return User
	 * @throws SQLException
	 */
	public User signIn(String username, String password) throws SQLException {
		User user = new User();
		Preference preference = new Preference();
		Status status = new Status();
		user.setPreference(preference);
		user.setStatus(status);
		user.setErrorMsg("valid");

		// Validation begins
		if (!Validation.validAlphaNumericString(username)) {
			user.setErrorMsg("Invalid Login ID");
			System.out.println(user.getErrorMsg());
		} else {
			user.setLoginId(username);
		}

		if (!Validation.validPasswordString(password)) {
			user.setErrorMsg("Invalid Password");
			System.out.println(user.getErrorMsg());
		} else {
			user.setPassword(password);
		}
		// Validation ends

		if (("valid").equals(user.getErrorMsg())) {
			System.out
					.println("Username and password is valid! Authenticating....");
			DataAccessObject dao = new DataAccessObject();
			user = dao.authenticateSignIn(user);
		}

		if (user.getId() != 0) {
			if (!(user.getLoginId() == null)) {
				if (user.getInvalidLogins() <= 10
						&& user.getStatus().getStatusId() == 1) {
					if ((user.getLoginId().equals(username))
							&& (user.getPassword().equals(password))) {
						DataAccessObject dao = new DataAccessObject();
						user = dao.getUserObject(user.getId());
						System.out.println("sub fee on login:"+user.getSubscriptionFee());
						user.setErrorMsg(constants.ConstantData.SUCCESS);
					} else {
						DataAccessObject dao = new DataAccessObject();
						dao.updateInvalidLogin(username);
						user.setErrorMsg("Invalid Combination (Try Again with correct Username/password)");
						System.out.println("Error msg set : "
								+ user.getErrorMsg());
					}
				}
			}
		} else {
			user.setErrorMsg("Invalid Combination (Try Again with correct Username/password)");
		}
		return user;
	}

	public String[] listState(String country) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.listState(country);
	}

	public String[] listCountry() throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.listCountry();
	}

	public String[] listCity(String state) throws SQLException {
		DataAccessObject dao = new DataAccessObject();
		return dao.listCity(state);
	}

	/**
	 * Inserts the new User data
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public String signUp(User user) throws SQLException {
		String state = "false";
		boolean error = false;
		
		if (user.getSalutation() != null)
			if (!Validation.validAlphaString(user.getSalutation())) {
				System.out.println("Invalid Salutation");
				state = state.concat(" Invalid Salutation");
				error = true;
			}

		if (!Validation.validAlphaString(user.getFirstName())) {
			System.out.println("Invalid First Name");
			state = state.concat(" Invalid First name");
			error = true;
		}

		if (user.getMiddleName() != null) {
			System.out.println(user.getMiddleName());
			if (!Validation.validAlphaString(user.getMiddleName())) {
				System.out.println("Invalid Middle Name");
				state = state.concat(" Invalid Middle name");
				error = true;
			}
		}

		if (!Validation.validAlphaString(user.getLastName())) {
			System.out.println("Invalid LastName");
			state = state.concat(" Invalid Last name");
			error = true;
		}

		if (!Validation.validAlphaString(user.getDisplayName())) {
			System.out.println("Invalid Display Name");
			state = state.concat(" Invalid Display name");
			error = true;
		}

		if (!Validation.validDateFormatString(user.getDob())) {
			System.out.println("Invalid Date of Birth(dd/mm/yyyy)");
			state = state
					.concat(" Invalid date of birth(Valid Format - dd/mm/yyyy)");
			error = true;
		}
		if (user.getSex() != null) {
			if (!Validation.validAlphaString(user.getSex())) {
				System.out.println("Invalid Gender/Sex");
				state = state.concat(" Invalid Gender/Sex");
				error = true;
			}
		} else {
			state = state.concat(" Invalid Gender/Sex");
			error = true;
		}

		if (!Validation.validEmailString(user.getEmailId())) {
			System.out.println("Invalid Email ID");
			state = state.concat(" Invalid Email ID");
			error = true;
		}

		if (!Validation.validAlphaNumericString(user.getLoginId())) {
			System.out.println("Invalid LoginId");
			state = state.concat(" Invalid Login ID");
			error = true;
		}
		if (!Validation.validPasswordString(user.getPassword())) {
			System.out.println("Invalid Password");
			state = state.concat(" Invalid Password");
			error = true;
		}

		DataAccessObject dao = new DataAccessObject();
		user.getPreference().setPreferenceId(
				dao.getpreferenceID(user.getPreference()));
		user.getAddress().getCountry()
				.setCountryId(dao.getCountryID(user.getAddress().getCountry()));
		user.getAddress()
				.getState()
				.setStateId(
						dao.getStateID(user.getAddress().getState(), user
								.getAddress().getCountry()));
		user.getAddress()
				.getCity()
				.setCityId(
						dao.getCityID(user.getAddress().getState(), user
								.getAddress().getCity(), user.getAddress()
								.getCountry()));

		if (user.getPreference().getPreferenceId() == 2) {
			if (!Validation.validSSNFormatString(user.getSsnId())) {
				System.out.println("Invalid SSN");
				state = state.concat(" Invalid SSN");
				error = true;
			}
		} else
			user.setSsnId(null);

		if (!Validation.validAddressString(user.getAddress().getAddrLine1())) {
			System.out.println("Invalid Address Line 1");
			state = state.concat(" Invalid Address Line 1");
			error = true;
		}

		if (user.getAddress().getAddrLine2() != null) {
			if (!Validation
					.validAddressString(user.getAddress().getAddrLine2())) {
				System.out.println("Invalid Address Line 2");
				state = state.concat(" Invalid Address Line 2");
				error = true;
			}
		} else
			user.getAddress().setAddrLine2(null);

		if (user.getAddress().getAddrLine3() == null) {
			if (!Validation
					.validAddressString(user.getAddress().getAddrLine3())) {
				System.out.println("Invalid Address Line 3");
				state = state.concat(" Invalid Address Line 3");
				error = true;
			}
		} else
			user.getAddress().setAddrLine3(null);

		if (!Validation.validNumericString(user.getAddress().getTelNo())) {
			System.out.println("Invalid Phone Number");
			state = state.concat(" Invalid Phone Number");
			error = true;
		}

		if (!Validation.validZipString(String.valueOf(user.getAddress()
				.getZipCode()))) {
			System.out.println("Invalid ZIP");
			state = state.concat(" Invalid ZIP");
			error = true;
		}
		
		boolean isValid = dao.paymentGatewayCheck(user);
		if (!isValid){
			state = state.concat(" Invalid Card Details-Bank have declined your transaction");
			error = true;
		}
		
		if (error)
			return state;
		else {
			dao.SignUp(user);
			state = "true";
		}
		return state;
	}

}
