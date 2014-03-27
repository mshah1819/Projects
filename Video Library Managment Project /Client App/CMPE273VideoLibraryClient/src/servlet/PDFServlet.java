package servlet;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BillingHistory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException{
		super.init(config);
	}
	
	public void doGet(HttpServletRequest request, 
			HttpServletResponse response) 
			throws ServletException, IOException{
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response) 
			throws ServletException, IOException{
		
		response.setContentType("application/pdf"); // Code 1
		Document document = new Document();
		try{
			BillingHistory[] fetchedBillingHistory = (BillingHistory[]) request.getSession().getAttribute("fetchedBillingHistory");
			PdfWriter.getInstance(document, 
				response.getOutputStream()); // Code 2
			document.open();
			
			// Code 3
			PdfPTable table = new PdfPTable(4);
			table.addCell("Movie Name");
			table.addCell("Transaction Date");
			table.addCell("Transaction Type");
			table.addCell("Transaction Amount");
			if(fetchedBillingHistory != null)
				for(int i = 0; i< fetchedBillingHistory.length;i++){
					table.addCell(fetchedBillingHistory[i].getMovieName());
					table.addCell(fetchedBillingHistory[i].getMovieAddedOn());
					table.addCell(fetchedBillingHistory[i].getMovieState());
					table.addCell(Float.toString(fetchedBillingHistory[i].getMovieAmountPaid()));
				}
			// Code 4
			document.add(table);		
			document.close();
		}catch(DocumentException e){
			e.printStackTrace();
		}
	}
	
}

