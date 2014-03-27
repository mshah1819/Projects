<%@page import="servlet.FetchMovieCategoryServlet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="model.MovieCategory"
	import="model.Movie"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="Common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
body {
	text-align: center;
}
</style>
</head>
<body>
	<br />
	<%
		if (request.getAttribute("errorMsg") != null
				&& request.getAttribute("errorMsg") != "") {
	%>
	<div align="center">
		<%
			out.println(request.getAttribute("errorMsg"));
		%>
	</div>
	<%
		}
	%>
	<%
		MovieCategory[] movieCategoryArray = null;
		if ((request.getSession().getAttribute("movieCategoryArray") == null)) {
			movieCategoryArray = FetchMovieCategoryServlet
					.fetchMovieCategory();
			request.getSession().setAttribute("movieCategoryArray",
					movieCategoryArray);
		} else {
			movieCategoryArray = (MovieCategory[]) request.getSession()
					.getAttribute("movieCategoryArray");
		}
	%>
	<br />
	<div align="center" class="Details_Div_Registration"
		style="padding-left: -50px; padding-top: 5px;">
		<div>
			<nav>
			<ul class="fancyNav">
				<li><form id="X" method="post" action="PlusMovie">
						<a href="javascript:;"
							onclick="javascript: document.getElementById('X').submit();">Add
							Movie[+]</a>
					</form></li>
			</ul>
			</nav>
		</div>
		<div>
			<br /> <b> Enter your search criteria!</b> <br>
			<form id="SearchMovieForm" method="post" action="SearchMovie"
				onSubmit="return validateForm()">
				<table align="center">
					<tr>
						<td>Movie Name:</td>
						<td><input class="idleField" type="text" name="movieName" />&nbsp;&nbsp;&nbsp;</td>
						<td>Production Banner:&nbsp;&nbsp;&nbsp;</td>
						<td><input class="idleField" type="text"
							name="productionName" /></td>
						<td>Release Date:</td>
						<td><input class="idleField" type="text" name="releaseDate" /></td>
					</tr>
					<tr>
						<td>Movie Category:</td>
						<td><select class="idleField" name="category">
								<%
									if (movieCategoryArray != null) {
										for (int i = 0; i < movieCategoryArray.length; i++) {
								%>
								<option>
									<%
										out.print(movieCategoryArray[i].getMovieCategory());
									%>
								</option>
								<%
									}
									}
								%>
						</select></td>

						<td>Available copies > :</td>
						<td><input class="idleField" type="text"
							name="availableCopies" />&nbsp;&nbsp;&nbsp;</td>
						<td></td>
						<td>
							<div align="right">
								<nav>
								<ul class="fancyNav">
									<li><a href="javascript:;"
										onclick="javascript: document.getElementById('SearchMovieForm').submit();">Search</a>
									</li>

								</ul>
								</nav>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<script type="text/javascript">
		function validateForm() {
			var x = document.forms["SearchMovieForm"]["availableCopies"].value;
			if (isNaN(x)) {
				alert("Invalid Available copies.\n\nOnly numbers are allowed.");
				document.forms["SearchMovieForm"]["availableCopies"].focus();
				return false;
			}
		};
	</script>
	<div align="center" class="Details_Div_Registration"
		style="padding-left: -50px; padding-top: 0px;">
		<table width="80%" style="margin-left: 20px; margin-top: 50px;">
			<thead>
				<th scope=col>Movie Name</th>
				<th>Production</th>
				<th>Release Date</th>
				<th>Movie Type</th>
				<th>Available Copies</th>
				<th>Price</th>
				<th>Update</th>
				<th>Delete</th>

			</thead>
			<tbody>
				<%
					Movie[] fetchedMovieDetails = (Movie[]) request
							.getAttribute("movieList");
					int i = 0;
					if (fetchedMovieDetails != null) {
						//System.out.println(fetchedMovieDetails[0].getMovieName());
						for (i = 0; i < fetchedMovieDetails.length; i++) {
							System.out.println("avl copy: "
									+ fetchedMovieDetails[i].getAvailableCopies());
							// This IF loop will exclude all those movie records which have 0 available copies
							if (!((fetchedMovieDetails[i].getAvailableCopies() == 0))) {
								String releasedDate = fetchedMovieDetails[i]
										.getReleaseDate().subSequence(0, 10).toString();
				%>
				<tr
					style="border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7; background: #fff; padding: 6px 6px 6px 12px; color: #4f6b72;">
					<td style="text-align: center;" scope="row"><%=fetchedMovieDetails[i].getMovieName()%></td>
					<td style="text-align: center;"><%=fetchedMovieDetails[i].getProdBanner()%></td>
					<!-- <td style = "text-align:center;"><%//=fetchedMovieDetails[i].getReleaseDate()%></td> -->
					<td style="text-align: center;"><%=releasedDate%></td>
					<td style="text-align: center;"><%=fetchedMovieDetails[i].getMovieCategory()
								.getMovieCategory()%></td>
					<td style="text-align: center;"><%=fetchedMovieDetails[i].getAvailableCopies()%></td>
					<td style="text-align: center;"><%=fetchedMovieDetails[i].getRentAmount()%></td>
					<td style="text-align: center;">
						<form id="UpdateForm" action="UpdateMovie" method="post">
							<div class="form-row">
								<input type="hidden" name="hiddenUpdateId"
									value="<%=fetchedMovieDetails[i].getMovieId()%>"> <input
									type="image" src="../LibraryIMG/updateButton.png" alt="submit">
							</div>
						</form>
					</td>
					<td style="text-align: center;">
						<form id="DeleteMovieForm" action="DeleteMovie" method="post">
							<div class="form-row">
								<input type="hidden" name="hiddenDeleteId"
									value="<%=fetchedMovieDetails[i].getMovieId()%>"> <input
									type="image" src="../LibraryIMG/deleteButton.png" alt="submit" onclick="return confirmDelete();">
							</div>
						</form>
					</td>
				</tr>
				<%
					}
						}
					} else {
				%>
				<tr
					style="border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7; background: #fff; padding: 6px 6px 6px 12px; color: #4f6b72;">
					<td colspan="8" style="text-align: center;" scope="row">There
						are no records to display..</td>
				</tr>
				<%
					}
				%>

			</tbody>
		</table>
	</div>
<script>
function confirmDelete(){
	var answer = confirm("Are you sure you want to delete?")
	if (answer){
		return true;
	}
	else{
		return false;
	}
}
</script>

</body>
</html>