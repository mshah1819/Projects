package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

/**
 * Servlet implementation class Login
 */
// @WebServlet("/Login")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteUserServlet() {
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
		System.out.println("in doget loginservlet");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			int userId = Integer.parseInt(request.getParameter("hiddenDeleteId"));
			services.ServiceProxy proxy = new services.ServiceProxy();
			proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
			if(proxy.deleteUser(userId)){
				request.setAttribute("errorMsg", "User has been successfully deleted!");
			} else {
				request.setAttribute("errorMsg", "User deletion failed!");
			}
			
			RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("UserCriteriaSearch.jsp");
				requestDispatcher.forward(request, response);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
