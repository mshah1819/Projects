package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Movie;
import model.MovieCategory;

/**
 * Servlet implementation class MovieCriteriaSearch
 */
@WebServlet("/MovieCriteriaSearch")
public class MovieCriteriaSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MovieCriteriaSearchServlet() {
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
		int availableCopies = 0;
		String categoryName;
		int categoryId = 0;
		services.ServiceProxy proxy = new services.ServiceProxy();
		proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
		String movieName = request.getParameter("movieName");
		String productionName = request.getParameter("productionName");
		String releaseDate = request.getParameter("releaseDate");
		categoryName = request.getParameter("category");
		MovieCategory[] movieCategoryArray = (MovieCategory[]) request
				.getSession().getAttribute("movieCategoryArray");
		System.out.println("Moviecategory array size = "
				+ movieCategoryArray.length);
		for (int i = 0; i < movieCategoryArray.length; i++) {
			if (movieCategoryArray[i].getMovieCategory() == categoryName) {
				categoryId = movieCategoryArray[i].getMovieCategoryId();
			}
		}
		if (!((request.getParameter("availableCopies") == null) || (request
				.getParameter("availableCopies").equals("")))) {
			availableCopies = Integer.parseInt(request.getParameter("availableCopies"));
		}
		
		Movie movie = new Movie();
		MovieCategory moviecategory = new MovieCategory();
		if (categoryId != 0) {
			moviecategory.setMovieCategoryId(categoryId);
		} else {
			moviecategory.setMovieCategoryId(categoryId);
		}
		if (availableCopies != 0) {
			movie.setAvailableCopies(availableCopies);
		} else {
			movie.setAvailableCopies(0);
		}
		if (movieName != null) {
			movie.setMovieName(movieName);
		} else {
			movie.setMovieName("");
		}
		if (productionName != null) {
			movie.setProdBanner(productionName);
		} else {
			movie.setProdBanner("");
		}
		if (releaseDate != null) {
			movie.setReleaseDate(releaseDate);
		} else {
			movie.setReleaseDate("");
		}
		movie.setMovieCategory(moviecategory);
		Movie[] movieList = proxy.movieCriteriaSearch(movie);
		
		if(movieList != null){
			request.setAttribute("movieList", movieList);
			RequestDispatcher rd = request
					.getRequestDispatcher("MovieCriteriaSearch.jsp");
			rd.forward(request, response);
        } else {
        	RequestDispatcher rd = request.getRequestDispatcher("MovieCriteriaSearch.jsp");
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
