package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MovieCart;
import model.Payment;
import model.User;

/**
 * Servlet implementation class Login
 */
public class AddBalanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	services.ServiceProxy proxy = new services.ServiceProxy();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddBalanceServlet() {
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
		User user = (User) request.getSession().getAttribute("user");
		float amountToAdd = 0;
		Payment payment = new Payment();

		if ((validNumericString(request.getParameter("amountToAdd")) && (request
				.getParameter("amountToAdd") != null))
				&& (validNumericString(request.getParameter("card_one")) && (request
						.getParameter("card_one") != null))
				&& (validNumericString(request.getParameter("card_two")) && (request
						.getParameter("card_two") != null))
				&& (validNumericString(request.getParameter("card_three")) && (request
						.getParameter("card_three") != null))
				&& (validNumericString(request.getParameter("card_four")) && (request
						.getParameter("card_four") != null))
				&& (validNumericString(request.getParameter("card_cvv")) && (request
						.getParameter("card_cvv") != null))

		) {

			// }
			//
			//
			//
			// if((request.getParameter("amountToAdd") != null) &&
			// (request.getParameter("amountToAdd") != "")){
			//
			// if ((request.getParameter("card_one") != null)
			// && (request.getParameter("card_two") != null)
			// && (request.getParameter("card_three") != null)
			// && (request.getParameter("card_four") != null)
			// && (request.getParameter("card_name") != null)
			// && (request.getParameter("card_cvv") != null)
			// && (request.getParameter("month") != null)
			// && (request.getParameter("year") != null)
			// && (request.getParameter("amountToAdd") != null)){
			System.out.println("Registration inside if condition 2");
			amountToAdd = Float.parseFloat(request.getParameter("amountToAdd"));
			payment.setCardNumOne(request.getParameter("card_one"));
			payment.setCardNumTwo(request.getParameter("card_two"));
			payment.setCardNumThree(request.getParameter("card_three"));
			payment.setCardNumFour(request.getParameter("card_four"));
			payment.setCardCvv(request.getParameter("card_cvv"));
			payment.setCardExpDate(request.getParameter("month").concat("/")
					.concat(request.getParameter("year")));
			payment.setNameOnCard(request.getParameter("card_name"));
			user.setPayment(payment);
			proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
			boolean isValid = proxy.paymentGatewayCheck(user);
			if (isValid) {
				proxy.addBalance(user.getId(), amountToAdd);
				request.setAttribute("errorMsg",
						"Amount Successfully added to your balace!");
			} else {
				request.setAttribute("errorMsg",
						"Invalid Card Details-Bank have declined your transaction!");
			}
		} else {
			request.setAttribute("errorMsg", "Invalid Data");
		}

		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("AddBalance.jsp");
		requestDispatcher.forward(request, response);
	}

	public boolean validNumericString(String input) {
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

}