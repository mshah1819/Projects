package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Movie;
import model.User;

/**
 * Servlet implementation class Login
 */
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	services.ServiceProxy proxy = new services.ServiceProxy();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckoutServlet() {
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
		String state = "";
		try {
			User user = (User) request.getSession().getAttribute("user");
			String[] selectedMovies = request.getParameterValues("checkboxId");
			proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
			Movie[] movieArray = new Movie[selectedMovies.length];
			for (int i = 0; i < movieArray.length; i++) {
				Movie movie = new Movie();
				movie.setMovieId(Integer.parseInt(selectedMovies[i]));
				movieArray[i] = movie;
			}
			System.out.println("MovieArray length to send: "
					+ movieArray.length);
			Movie[] returnMovieArray = proxy.checkOutMovie(movieArray, user);
			System.out.println("the returned array size::"
					+ returnMovieArray.length);

			for (int i = 0; i < returnMovieArray.length
					&& returnMovieArray[i].getErrorMsg() != null; i++) {
				System.out.println("Error message for movie :"
						+ returnMovieArray[i].getMovieId() + " is: "
						+ returnMovieArray[i].getErrorMsg());
				if (returnMovieArray[i].getErrorMsg() != "") {
					state = state.concat(returnMovieArray[i].getErrorMsg())
							.concat(" \\n");
				}
			}
			if (!("").equals(state)) {
				request.setAttribute("state", state);
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("RetrieveCart");
				requestDispatcher.forward(request, response);
			} else {
				request.setAttribute("errorMsg",
						"Movies Successfully added to your account");
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("MyProfile");
				requestDispatcher.forward(request, response);
			}

			// response.sendRedirect("AddedToAccount.jsp");

			System.out.println("return checkout movie array:" + returnMovieArray[0].getErrorMsg());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
