package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class DecideActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DecideActionServlet() {
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
		String action = "";
		try{
		if (request.getParameter("Home") != null) {
				action = "Home.jsp";
			} else if (request.getParameter("Movies") != null) {
				action = "MovieDetails.jsp";
			} else if (request.getParameter("Users") != null) {
				action = "Home.jsp";
			} else if (request.getParameter("Revenue") != null) {
				action = "Revenue.jsp";
			} else if (request.getParameter("SignOut") != null) {
				action = "LogOut";
			} else if (request.getParameter("MyAccount") != null) {
				action = "MyAccount.jsp";
			} else if (request.getParameter("MyCart") != null) {
				action = "RetrieveCart";
			} else if (request.getParameter("Checkout") != null) {
				action = "Checkout";
			} else if (request.getParameter("AddBalance") != null) {
				action = "AddBalance.jsp";
			} else if (request.getParameter("SearchMovie") != null) {
				action = "MovieCriteriaSearch.jsp";
			} else if (request.getParameter("UpdateMovie") != null) {
				action = "UpdateMovie";
			} else if (request.getParameter("UpdateMovie") != null) {
				action = "UpdateMovie";
			} else if (request.getParameter("UpdateMovie") != null) {
				action = "UpdateMovie";
			} else if (request.getParameter("UpdateMovie") != null) {
				action = "UpdateMovie";
			} else if (request.getParameter("UpdateMovie") != null) {
				action = "UpdateMovie";
			} else if (request.getParameter("DeleteMovie") != null) {
				action = "DeleteMovie";
			} else if (request.getParameter("AddToCart") != null) {
				action = "AddToCart";
			} else if (request.getParameter("ProceedToPayment") != null) {
				action = "Payment.jsp";
			} else if (request.getParameter("ContinueShopping") != null) {
				action = "MovieDetails.jsp";
			} else if (request.getParameter("ListAllPremiumMembers") != null) {
				action = "ListAllPremiumMembers";
			} else if (request.getParameter("ListAllSimpleMembers") != null) {
				action = "ListAllSimpleMembers";
			} else if (request.getParameter("ListAllMembers") != null) {
				action = "ListAllMembers";
			} else if (request.getParameter("ListAllMovies") != null) {
				action = "ListAllMovies";
			} else if (request.getParameter("FetchMovieCategory") != null) {
				action = "FetchMovieCategory";
			} else if (request.getParameter("LogOut") != null) {
				action = "LogOut";
			} else if (request.getParameter("MovieDetails") != null) {
				action = "MovieDetails.jsp";
			}
			System.out.println("Setting the new action: " + action);
			RequestDispatcher rd = request.getRequestDispatcher(action);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
