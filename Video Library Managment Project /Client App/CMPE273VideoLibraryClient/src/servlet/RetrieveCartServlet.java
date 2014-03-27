package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Movie;
import model.MovieCart;
import model.User;

/**
 * Servlet implementation class Login
 */
public class RetrieveCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	services.ServiceProxy proxy = new services.ServiceProxy();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RetrieveCartServlet() {
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
		MovieCart[] cartData = null;
		Movie[] movieDataForCart =null;
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
			cartData = proxy.retrieveCart(user.getId());
			if(cartData != null){
			request.setAttribute("cartData", cartData);
			movieDataForCart = proxy.retrieveMovieDataForCart(cartData);
			request.setAttribute("movieDataForCart", movieDataForCart);
			//response.sendRedirect("Cart.jsp");
			}
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("Cart.jsp");
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
