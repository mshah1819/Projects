package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.ServiceProxy;

import model.Address;
import model.City;
import model.User;

/**
 * Servlet implementation class UserCriteriaSearchServlet
 */
@WebServlet("/UserCriteriaSearchServlet")
public class UserCriteriaSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserCriteriaSearchServlet() {
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
		int prefId = 0;
		String displayName = request.getParameter("displayName");
		String loginId = request.getParameter("loginId");
		String emailId = request.getParameter("emailId");
		String prefType = request.getParameter("Pref");
		String cityName = request.getParameter("city");
		String zipCode = request.getParameter("zipcode");
		// String subscriptionRenewal =
		// request.getParameter("subscriptionRenewal");
		if (prefType.equals("Simple Customer")) {
			prefId = 1;
		} else if (prefType.equals("Premium Member")) {
			prefId = 2;
		}
		User user = new User();
		Address address = new Address();
		City city = new City();
		if(cityName.length() != 0){
			city.setCityName(cityName);
		}
		address.setCity(city);
		address.setZipCode(zipCode);
		user.setAddress(address);
		user.setPreferenceId(prefId);
		if (loginId.length() != 0)
			user.setLoginId(loginId);
		else
			user.setLoginId("");
		if (emailId.length() != 0)
			user.setEmailId(emailId);
		else
			user.setEmailId("");
		if (displayName.length() != 0)
			user.setDisplayName(displayName);
		else
			user.setDisplayName("");
		// if (subscriptionRenewal.length()!=0)
		// user.setSubscriptionRenewal(subscriptionRenewal);
		// else
		// user.setSubscriptionRenewal("");
		ServiceProxy proxy = new ServiceProxy();
		proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
		User[] userList = proxy.userSearchCriteria(user);
		if (userList != null) {
			System.out.println("Userlist fetched size: " + userList.length);
			request.setAttribute("userList", userList);
			RequestDispatcher rd = request
					.getRequestDispatcher("UserCriteriaSearch.jsp");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request
					.getRequestDispatcher("UserCriteriaSearch.jsp");
			rd.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}