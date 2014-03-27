package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Movie;

/**
 * Servlet implementation class MyProfileServlet
 */
@WebServlet("/MyProfileServlet")
public class UpdateMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateMovieServlet() {
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
		System.out.println("hi");
		String action = null;
		String buttonType = "update";
		Movie movie = new Movie();
		// if (request.getParameterValues("checkboxId") != null) {
		// String[] arr = request.getParameterValues("checkboxId");
		// System.out.println(":::::::::::::::::" + arr[0]);
		movie.setMovieId(Integer.parseInt(request
				.getParameter("hiddenUpdateId")));
		services.ServiceProxy proxy = new services.ServiceProxy();
		proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
		movie = proxy.userMovieView(movie);

		SimpleDateFormat formatter, FORMATTER;
		formatter = new SimpleDateFormat("yyyy-mm-dd");

		Date date = null;
		try {
			date = formatter.parse(movie.getReleaseDate().substring(0, 10));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FORMATTER = new SimpleDateFormat("dd/mm/yyyy");

		String newDate = FORMATTER.format(date);
		System.out.println("NewDate-->" + newDate);
		movie.setReleaseDate(movie.getReleaseDate().replace("-", "/"));
		movie.setReleaseDate(newDate);

		HttpSession session = request.getSession();
		session.setAttribute("movie", movie);

		action = "MovieDisplay.jsp?result=" + buttonType;
		request.setAttribute("result", buttonType);
		RequestDispatcher rd = request.getRequestDispatcher(action);
		rd.forward(request, response);

	}

}
