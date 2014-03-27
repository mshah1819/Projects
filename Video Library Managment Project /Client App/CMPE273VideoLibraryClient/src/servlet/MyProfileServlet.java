package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Movie;
import model.User;

/**
 * Servlet implementation class MyProfileServlet
 */
@WebServlet("/MyProfileServlet")
public class MyProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyProfileServlet() {
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
		User user = (User) request.getSession().getAttribute("user");
		services.ServiceProxy proxy = new services.ServiceProxy();
		proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
		Movie[] boughtMovies = proxy.getUserMoviesBought(user.getId());
		System.out.println("Sub fee: "+user.getSubscriptionFee());
		if(boughtMovies != null){
		if(boughtMovies.length != 0){
			System.out.println("boughtMovies size: "+boughtMovies.length);
			 request.setAttribute("boughtMovies", boughtMovies);
		}
		}
		 RequestDispatcher requestDispatcher = request
		 .getRequestDispatcher("MyProfile.jsp");
		 requestDispatcher.forward(request, response);
	}

}
