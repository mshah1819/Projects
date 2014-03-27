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
 * Servlet implementation class AddMovieServlet
 */
public class PaginationTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PaginationTestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
        int recordsPerPage = 20;
        if(request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        services.ServiceProxy proxy = new services.ServiceProxy();
		proxy.setEndpoint("http://localhost:8080/CMPE273VideoLibrary/services/Service");
		Movie[] movieArray = proxy.testPagination((page-1)*recordsPerPage,
                recordsPerPage);;
                
		int totalNoOfRecords = proxy.getTotalNoOfRecords();
        int noOfPages = (int) Math.ceil(totalNoOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("movieArray", movieArray);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        System.out.println("idArray size::"+movieArray.length);
        System.out.println("total records: "+totalNoOfRecords);
		System.out.println("First movie ID in the array:"+movieArray[0].getMovieId());
		System.out.println("no of pages:"+noOfPages);
		RequestDispatcher rd = request.getRequestDispatcher("PaginationTest.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
