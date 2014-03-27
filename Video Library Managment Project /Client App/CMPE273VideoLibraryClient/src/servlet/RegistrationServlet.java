package servlet;

import java.io.IOException;
import model.City;
import model.Payment;
import model.State;
import model.Country;
import model.Preference;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import model.Address;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = new User();
		Address address = new Address();
		City city = new City();
		State state = new State();
		Country country = new Country();
		Preference preference = new Preference();
		Payment payment = new Payment();
		System.out.println(preference.getPreferenceType() + "Preference Type");
		try {
			if (!(request.getParameter("salutation").equals("--[Select One]--")))
				user.setSalutation(request.getParameter("salutation"));

			if (!(request.getParameter("first_name") == null))
				user.setFirstName(request.getParameter("first_name"));

			if (!((request.getParameter("middle_name") == null) || (request
					.getParameter("middle_name").isEmpty())))
				user.setMiddleName(request.getParameter("middle_name"));

			if (!(request.getParameter("last_name") == null))
				user.setLastName(request.getParameter("last_name"));

			if (!(request.getParameter("display_name") == null))
				user.setDisplayName(request.getParameter("display_name"));

			if (!(request.getParameter("SnapHost_Calendar") == null))
				user.setDob(request.getParameter("SnapHost_Calendar"));

			if (!(request.getParameter("sex") == null))
				user.setSex(request.getParameter("sex"));

			if (!(request.getParameter("add_1") == null))
				address.setAddrLine1(request.getParameter("add_1"));

			if (!(request.getParameter("add_2") == null))
				address.setAddrLine2(request.getParameter("add_2"));

			if (!(request.getParameter("add_3") == null))
				address.setAddrLine3(request.getParameter("add_3"));

			if (!(request.getParameter("contact_num") == null))
				address.setTelNo(request.getParameter("contact_num"));

			if (!((request.getParameter("city") == null) || (request
					.getParameter("city").isEmpty())))
				city.setCityName(request.getParameter("city"));
			address.setCity(city);

			if (!((request.getParameter("state") == null) || (request
					.getParameter("state").isEmpty())))
				state.setStateName(request.getParameter("state"));
			address.setState(state);

			if (!((request.getParameter("country") == null) || (request
					.getParameter("country").isEmpty())))
				country.setCountryName(request.getParameter("country"));
			address.setCountry(country);

			if (!((request.getParameter("zipcode") == null) || (request
					.getParameter("zipcode").isEmpty())))
				address.setZipCode((String) request.getParameter("zipcode"));

			if (!(request.getParameter("login_id") == null))
				user.setLoginId(request.getParameter("login_id"));

			if (!(request.getParameter("email_id") == null))
				user.setEmailId(request.getParameter("email_id"));

			if (!(request.getParameter("password") == null))
				user.setPassword(request.getParameter("password"));

			if (!(request.getParameter("userType") == null))
				preference.setPreferenceType(request.getParameter("userType"));
			user.setPreference(preference);
			user.setAddress(address);

			services.ServiceProxy proxy = new services.ServiceProxy();
			proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
			System.out.println(preference.getPreferenceType()
					+ ":::::Preference Type");
			if (preference.getPreferenceType() != null) {
				if (preference.getPreferenceType().equals("Simple User")) {
					System.out.println("Simple User");
					String stateString = proxy.signUp(user);
					System.out.println(stateString);
					if (stateString == null)
						stateString = "false Invalid entry";
					if (stateString.contains("false")) {
						String st = stateString.substring(5);
						st = st.replace(" Invalid", "\\nInvalid");
						RequestDispatcher dispatcher = getServletContext()
								.getRequestDispatcher(
										"/LibraryJSP/Registration.jsp?result="
												+ st);
						request.setAttribute("result", st);
						dispatcher.forward(request, response);
					} else {
						RequestDispatcher dispatcher = getServletContext()
								.getRequestDispatcher(
										"/LibraryJSP/Login.jsp?result="
												+ stateString);
						request.setAttribute("result", stateString);
						dispatcher.forward(request, response);
					}
				}
				if (preference.getPreferenceType().equals("Premium User")) {
					System.out.println("Premium User");
					String stateString = "false";
					int flag = 0;
					if ((request.getParameter("ssn1") != null)
							&& (request.getParameter("ssn2") != null)
							&& (request.getParameter("ssn3") != null)) {
						System.out
								.println("Registration inside if condition 1");
						user.setSsnId((request.getParameter("ssn1"))
								.concat("-")
								.concat(request.getParameter("ssn2"))
								.concat("-")
								.concat(request.getParameter("ssn3")));
						flag = 1;
					} else
						stateString = stateString.concat(" Invalid SSN");
					if ((request.getParameter("card_one") != null)
							&& (request.getParameter("card_two") != null)
							&& (request.getParameter("card_three") != null)
							&& (request.getParameter("card_four") != null)
							&& (request.getParameter("card_name") != null)
							&& (request.getParameter("card_cvv") != null)
							&& (request.getParameter("month") != null)
							&& (request.getParameter("year") != null)) {
						System.out
								.println("Registration inside if condition 2");
						payment.setCardNumOne(request.getParameter("card_one"));
						payment.setCardNumTwo(request.getParameter("card_two"));
						payment.setCardNumThree(request
								.getParameter("card_three"));
						payment.setCardNumFour(request
								.getParameter("card_four"));
						payment.setCardCvv(request.getParameter("card_cvv"));
						payment.setCardExpDate(request.getParameter("month")
								.concat("/")
								.concat(request.getParameter("year")));
						payment.setNameOnCard(request.getParameter("card_name"));
						user.setPayment(payment);
						flag = flag + 1;

					} else
						stateString = stateString
								.concat(" Invalid Card Details");

					if (flag == 2) {
						System.out.println("Registration inside  condition 1");
						stateString = proxy.signUp(user);
						if (stateString.contains("Invalid")) {
							System.out
									.println("Registration inside if condition 4");
							String st = stateString.substring(5);
							st = st.replace(" Invalid", "\\nInvalid");
							RequestDispatcher dispatcher = getServletContext()
									.getRequestDispatcher(
											"/LibraryJSP/Registration.jsp?result="
													+ st);
							request.setAttribute("result", st);
							dispatcher.forward(request, response);
						} else {
							RequestDispatcher dispatcher = getServletContext()
									.getRequestDispatcher(
											"/LibraryJSP/Login.jsp?result="
													+ stateString);
							request.setAttribute("result", stateString);
							dispatcher.forward(request, response);
						}
					} else {
						System.out
								.println("Registration inside else condition 1");
						String st = stateString.substring(5);
						st = st.replace(" Invalid", "\\nInvalid");
						RequestDispatcher dispatcher = getServletContext()
								.getRequestDispatcher(
										"/LibraryJSP/Registration.jsp?result="
												+ st);
						request.setAttribute("result", st);
						dispatcher.forward(request, response);
					}
				}
				if (preference.getPreferenceType().equals("Administrator")) {
					String res = proxy.signUp(user);
					if (res.contains("Invalid")) {
						String st = res.substring(5);
						st = st.replace(" Invalid", "\\nInvalid");
						RequestDispatcher dispatcher = getServletContext()
								.getRequestDispatcher(
										"/LibraryJSP/Registration.jsp?result="
												+ st);
						request.setAttribute("result", st);
						dispatcher.forward(request, response);
					} else {
						res = res.concat("Admin");
						RequestDispatcher dispatcher = getServletContext()
								.getRequestDispatcher(
										"/LibraryJSP/Login.jsp?result=" + res);
						request.setAttribute("result", res);
						dispatcher.forward(request, response);
					}

				}
			} else {
				String res = proxy.signUp(user);
				res = res.concat(" Invalid Preference Type");
				String st = res.substring(5);
				st = st.replace(" Invalid", "\\nInvalid");
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher(
								"/LibraryJSP/Registration.jsp?result=" + st);
				request.setAttribute("result", st);
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[] listCountry() {
		String[] country = null;
		try {
			services.ServiceProxy proxy = new services.ServiceProxy();
			proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");

			country = proxy.listCountry();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Size of the returned countrylist: "+country.length);
		return country;
	}

	public static String[] listState(String country) {
		String[] state = null;
		try {
			services.ServiceProxy proxy = new services.ServiceProxy();
			proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");

			state = proxy.listState(country);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Size of the returned statelist: "+state.length);
		return state;
	}

	public static String[] listCity(String state) {
		String[] city = null;
		try {
			services.ServiceProxy proxy = new services.ServiceProxy();
			proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
			
			city = proxy.listCity(state);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Size of the returned citylist: "+city.length);
		return city;
	}

}
