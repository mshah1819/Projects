package servlet;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MovieCategory;
import services.ServiceProxy;

/**
 * Servlet implementation class ListAllPremiumMembersServlet
 */
public class FetchMovieCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static ServiceProxy proxy = new ServiceProxy();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchMovieCategoryServlet() {
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
		
	}
	
	public static MovieCategory[] fetchMovieCategory() throws RemoteException{
		proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
	 	MovieCategory[] movieCategoryArray = null;
	 	movieCategoryArray = proxy.fetchMovieCategory();
	 	System.out.println("Movie category size array: :" +movieCategoryArray.length);
	 	return movieCategoryArray;
	}
}
