package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Movie;

/**
 * Servlet implementation class MovieDetail
 */
public class MovieDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MovieDetailsServlet() {
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
		Movie[] displayListMovies = null;
		try {
			services.ServiceProxy proxy = new services.ServiceProxy();
			proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
			displayListMovies = proxy.displayMovies(request
					.getParameter("char"));
			request.setAttribute("fetchedMovieListDetails", displayListMovies);
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/LibraryJSP/MovieDetails.jsp");
			// request.setAttribute("result", displayListMovies);
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Movie[] getLatestMovieDetails() {

		Movie[] latestMovieDetailsArray = null;
		try {
			services.ServiceProxy proxy = new services.ServiceProxy();
			proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
			latestMovieDetailsArray = proxy.getLatestMovies();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return latestMovieDetailsArray;
	}


}
