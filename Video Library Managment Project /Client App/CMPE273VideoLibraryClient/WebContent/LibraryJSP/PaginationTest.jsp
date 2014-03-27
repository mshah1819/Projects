<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Movie"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<form action = "PaginationTest">
	<input type="submit" value="test" />
	
	<% 
		Movie[] movieArray = (Movie[])request.getAttribute("movieArray");
		int noOfPages = 10;//Integer.parseInt((String)request.getAttribute("noOfPages"));
		int currentPage = 1;//(Integer)request.getAttribute("currentPage");
	%>
	</form>
	
	<table border="1" cellpadding="5" cellspacing="5">
	<tr>
	<th>Movie ID</th>
	</tr>
	
<tr><% for(int i=0; i<movieArray.length;i++) { %><td><%= movieArray[i].getMovieId() %></>i</a></td><% } %></tr>	
	
	</table>
	
	   
	
    <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
    <table border="1" cellpadding="5" cellspacing="5">
<tr><% for(int i=1; i<=noOfPages;i++) { %><td><a href="#"><%= i %></a></td><% } %></tr>

</table>
</body>
</html>