package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BillingHistory;
import model.User;

/**
 * Servlet implementation class BillingHistoryServlet
 */
@WebServlet("/BillingHistoryServlet")
public class BillingHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BillingHistoryServlet() {
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
		BillingHistory[] billArray = null;
		services.ServiceProxy proxy = new services.ServiceProxy();
		proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
		User currentUser = (User) request.getSession().getAttribute("user");
		if (currentUser.getPreference().getPreferenceId() != 3) {
			billArray = proxy.fetchBillingHistory(currentUser);
			System.out.println("Bill array obtained for user :"
					+ currentUser.getId() + " of size " + billArray.length);

		} else {
			User user = new User();
			if (request.getParameter("hiddenBillingId") != null) {
				user.setId(Integer.parseInt(request
						.getParameter("hiddenBillingId")));
				billArray = proxy.fetchBillingHistory(user);
				System.out.println("Bill array obtained for user :"
						+ user.getId() + " of size " + billArray.length);

			}
		}
		if (billArray != null) {

			request.setAttribute("fetchedBillingHistory", billArray);
		} else {
			request.setAttribute("errorMsg", "User has no history!");
		}

		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("BillingHistory.jsp");
		requestDispatcher.forward(request, response);

	}

}
