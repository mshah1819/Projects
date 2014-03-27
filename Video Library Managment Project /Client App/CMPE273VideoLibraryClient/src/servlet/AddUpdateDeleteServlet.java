package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Movie;
import model.MovieCategory;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class AddUpdateDeleteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddUpdateDeleteServlet() {
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
		services.ServiceProxy proxy = new services.ServiceProxy();
		proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
		String action = "", errorState = "";
		boolean error = false;
		Movie movie = new Movie();
		MovieCategory movieCat = new MovieCategory();
		String inpName = request.getParameter("movie_name");
		String inpType = request.getParameter("movie_type");
		String inpTotCop = request.getParameter("movie_tc");
		String inpProd = request.getParameter("movie_production");
		String inpRentAmt = request.getParameter("movie_ra");
		String inpAvlCop = request.getParameter("movie_ac");
		String inpRdDate = request.getParameter("SnapHost_Calendar");

		try {

			// Movie name
			if (!((inpName == null) || (inpName == ""))) {

				String rg = "^([ \u00c0-\u01ffa-zA-Z'])+$";
				java.util.regex.Pattern pt = java.util.regex.Pattern
						.compile(rg);
				if (pt.matcher(inpName).matches()) {
					movie.setMovieName(inpName);
				} else {
					errorState = errorState.concat("Invalid Name");
					error = true;
				}
			} else {
				errorState = errorState.concat("Invalid Name");
				error = true;
			}

			// Movie type
			if (!((inpType == null) || (inpType == ""))) {

				System.out.println("movie type not null");
				String rg = "^([ \u00c0-\u01ffa-zA-Z'])+$";
				java.util.regex.Pattern pt = java.util.regex.Pattern
						.compile(rg);
				System.out.println("Test2");
				if (pt.matcher(inpType).matches()) {
					System.out.println("Test3");
					movieCat.setMovieCategory(inpType);
					movie.setMovieCategory(movieCat);
				} else {
					errorState = errorState.concat("Invalid Category");
					error = true;
				}

			} else {
				errorState = errorState.concat("Invalid Category");
				error = true;
			}

			// Total copies
			if (!((inpTotCop == null) || (inpTotCop == ""))) {

				String rg = "^[0-9]+$";
				java.util.regex.Pattern pt = java.util.regex.Pattern
						.compile(rg);
				if (pt.matcher(inpTotCop).matches()) {
					movie.setTotalCopies(Integer.parseInt(inpTotCop));
				} else {
					errorState = errorState.concat("Invalid Total Copies");
					error = true;
				}

			} else {
				errorState = errorState.concat("Invalid Total Copies");
				error = true;
			}

			// movie prod
			if (!((inpProd == null) || (inpProd == ""))) {
				String rg = "^([ \u00c0-\u01ffa-zA-Z'])+$";
				java.util.regex.Pattern pt = java.util.regex.Pattern
						.compile(rg);
				if (pt.matcher(inpProd).matches()) {
					movie.setProdBanner(inpProd);
				} else {
					errorState = errorState.concat("Invalid Production");
					error = true;
				}

			} else {
				errorState = errorState.concat("Invalid Production");
				error = true;
			}

			// rent amount
			System.out.println("testRent Amount: " + inpRentAmt);
			if (!((inpRentAmt == null) || (inpRentAmt == ""))) {
				String rg = "^[0-9]\\d*(\\.\\d{0,2}+)?$";
				java.util.regex.Pattern pt = java.util.regex.Pattern
						.compile(rg);
				if (pt.matcher(inpRentAmt).matches()) {
					System.out.println("matches pattern");
					movie.setRentAmount(Float.parseFloat(inpRentAmt));

				} else {
					errorState = errorState.concat("Invalid Rent Amount");
					error = true;
				}
			} else {
				errorState = errorState.concat("Invalid Rent Amount");
				error = true;
			}

			System.out.println("error state after rent amount check: "
					+ errorState);

			// Movie available copies
			if (!((inpAvlCop == null) || (inpAvlCop == ""))) {
				String rg = "^[0-9]+$";
				java.util.regex.Pattern pt = java.util.regex.Pattern
						.compile(rg);
				if (pt.matcher(inpAvlCop).matches()) {
					movie.setAvailableCopies(Integer.parseInt(inpAvlCop));
				} else {
					errorState = errorState.concat("Invalid Available Copies");
					error = true;
				}
			}

			// calender
			if (!((inpRdDate == null) || (inpRdDate == ""))) {
				String rg = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
				java.util.regex.Pattern pt = java.util.regex.Pattern
						.compile(rg);
				if (pt.matcher(inpRdDate).matches()) {
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"dd/MM/yyyy");
					Date convertedDate = dateFormat.parse(inpRdDate);
					System.out.println("converted date format" + convertedDate);
					Calendar currentDate = Calendar.getInstance();
					Date dateNow = currentDate.getTime();
					System.out.println("Current Date " + currentDate);
					if (convertedDate.before(dateNow)) {
						System.out.println(movie.getReleaseDate());
						movie.setReleaseDate(inpRdDate);
						System.out.println(movie.getReleaseDate());
					} else {
						errorState = errorState
								.concat("Invalid date format and should be less than today's date");
						error = true;
					}
				} else {
					errorState = errorState
							.concat("Invalid Released date format(dd/mm/yyyy)");
					error = true;
				}
			} else {
				errorState = errorState.concat("Invalid Released date");
				error = true;
			}

			System.out.println("Error before final check:" + error);
			System.out.println("Errorstate before final check:" + errorState);
			if (error) {
				movie.setErrorMsg(errorState);
				action = movie.getErrorMsg();
				System.out.println(action);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/LibraryJSP/MovieDisplay.jsp?action="
								+ action);
				request.setAttribute("action", action);
				System.out.println("action::" + action);
				System.out.println("Request:::" + request
						+ "::::::::response:::" + response
						+ ":::::::: Dispatcher:::::" + dispatcher);
				dispatcher.forward(request, response);
			} else {
				if ((inpAvlCop == "") || (inpAvlCop == null)) {

					movie = proxy.adminInsertMovie(movie);
					System.out.println(movie.getErrorMsg());
					if (movie.getErrorMsg().contains("Invalid")) {
						action = movie.getErrorMsg();
						System.out.println(action);
						RequestDispatcher dispatcher = getServletContext()
								.getRequestDispatcher(
										"/LibraryJSP/MovieDisplay.jsp?action="
												+ action);
						request.setAttribute("action", action);
						dispatcher.forward(request, response);
					} else {
						action = "Movie Added Successfully";
						request.setAttribute("errorMsg", action);
						RequestDispatcher dispatcher = getServletContext()
								.getRequestDispatcher(
										"/LibraryJSP/MovieCriteriaSearch.jsp");
						dispatcher.forward(request, response);
					}
				} else {
					movie = proxy.adminUpdateMovie(movie);
					if (movie.getErrorMsg() != null) {
						action = movie.getErrorMsg();
						System.out.println(action);
						RequestDispatcher dispatcher = getServletContext()
								.getRequestDispatcher(
										"/LibraryJSP/MovieDisplay.jsp?action="
												+ action);
						request.setAttribute("action", action);
						dispatcher.forward(request, response);
					} else
						action = "Successfully updated";
					request.setAttribute("errorMsg", action);
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher(
									"/LibraryJSP/MovieCriteriaSearch.jsp");
					dispatcher.forward(request, response);
				}
			}
			// RequestDispatcher dispatcher =
			// getServletContext().getRequestDispatcher("/LibraryJSP/MovieDetails.jsp?action="+action);
			// request.setAttribute("action", action);
			// dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
