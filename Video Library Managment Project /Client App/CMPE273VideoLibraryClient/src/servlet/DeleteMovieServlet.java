package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Movie;

/**
 * Servlet implementation class MyProfileServlet
 */
@WebServlet("/MyProfileServlet")
public class DeleteMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteMovieServlet() {
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
		String action = "";
		Movie movie = new Movie();
		movie.setMovieId(Integer.parseInt(request
				.getParameter("hiddenDeleteId")));
		services.ServiceProxy proxy = new services.ServiceProxy();
		proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
		boolean result = proxy.adminDeleteMovie(movie);
		if (result) {
			action = "Successfully deleted!";

		} else {
			action = "Deletion unsuccessful!";
		}
		request.setAttribute("errorMsg", action);
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/LibraryJSP/MovieCriteriaSearch.jsp");
		dispatcher.forward(request, response);

	}

}
