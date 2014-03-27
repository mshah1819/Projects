package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MovieCart;
import model.User;

/**
 * Servlet implementation class Login
 */
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	services.ServiceProxy proxy = new services.ServiceProxy();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddToCartServlet() {
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
		try {
			String successMsg = "Movie/s added successfully to your cart!";
			String errorMsg = "";
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			String[] selectedMovies = request.getParameterValues("checkboxId");
			System.out.println("Movies selected size:: "
					+ selectedMovies.length);
			proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
			System.out.println("Going to add the movies: "
					+ selectedMovies.toString());
			MovieCart[] movieCartArray = new MovieCart[selectedMovies.length];
			MovieCart[] returnMovieCartArray = new MovieCart[selectedMovies.length];
			for (int i = 0; i < movieCartArray.length; i++) {
				MovieCart movieCart = new MovieCart();
				movieCart.setMovieId(Integer.parseInt(selectedMovies[i]));
				movieCart.setUserId(user.getId());
				// System.out.println("Cart item "+i+" Movie: "+mov);
				movieCartArray[i] = movieCart;
			}
			returnMovieCartArray = proxy.addToCart(movieCartArray);
			if (returnMovieCartArray != null) {

				for (int i = 0; i < returnMovieCartArray.length; i++) {
					System.out.println("Error message for " + i
							+ returnMovieCartArray[i].getErrorMsg());
					String temperrorMsg = "Error for Movie ID >"
							+ returnMovieCartArray[i].getMovieId() + "::"
							+ returnMovieCartArray[i].getErrorMsg();
					if (temperrorMsg.contains("Invalid")) {
						errorMsg = temperrorMsg.concat(" \\n " + temperrorMsg);
					}
					System.out.println("Final error message in cart: "
							+ errorMsg);
				}

			}
			if (errorMsg != null){
				if (!errorMsg.contains("Invalid")) {
					System.out.println("Setting the success msg in request");
					request.setAttribute("errorMsg", successMsg);
				} else {
					System.out.println("Setting the error msg in request");
					request.setAttribute("errorMsg", errorMsg);
				}
			}	
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/LibraryJSP/MovieDetails.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
