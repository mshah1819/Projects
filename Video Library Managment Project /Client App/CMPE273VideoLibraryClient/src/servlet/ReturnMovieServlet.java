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
 * Servlet implementation class ReturnMovieServlet
 */
@WebServlet("/ReturnMovieServlet")
public class ReturnMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnMovieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String errorMsg = "Failed!... Kindly contact administrator!";
		User user = (User)request.getSession().getAttribute("user");
		int returnMovieId = Integer.parseInt(request.getParameter("hiddenReturnId"));
		Movie[] returnMovie = new Movie[1];
		Movie movie = new Movie();
		movie.setMovieId(returnMovieId);
		System.out.println("Returning movie:"+returnMovieId);
		returnMovie[0]=movie;
		services.ServiceProxy proxy = new services.ServiceProxy();
		proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
		if(proxy.returnedMovie(returnMovie, user)){
			errorMsg = "Movie successfully returned!";
		}
		request.setAttribute("errorMsg", errorMsg);
		RequestDispatcher rd = request.getRequestDispatcher("MyProfile.jsp");
		rd.forward(request, response);
	}

}
