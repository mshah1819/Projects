<!DOCTYPE html>
<%@page import="servlet.RetrieveCartServlet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Movie"%>
<%@page import="model.MovieCart"%>

<%@include file="Common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" manifest="mycache.appcache">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>My Video Library</title>
<link rel="stylesheet" href="../LibraryCSS/style.css" type="text/css" />
<link rel="stylesheet" href="../LibraryCSS/addtocart.css"
	type="text/css" />

</head>

<body>
	<%
		String state = (String)request.getAttribute("state");
		if( (state != null) && (!("").equals(state))){
	%>
	<script>
	alert('<%=state%>');
	</script>

	<%
		}
			MovieCart[] movieCartArray = (MovieCart[]) request
			.getAttribute("cartData");
			Movie[] movieArray = (Movie[]) request
			.getAttribute("movieDataForCart");
			//for (int j = 0; j < movieArray.length; j++){
			//	System.out.println("id :"+movieArray[j].getMovieId());
			//}
	%>
	<div>
		<form method="post" action="DecideAction">
			<table width="80%" style="margin-left: 120px; margin-top: 50px;">

				<thead>
					<tr>
						<th scope=“col”>Movie</th>
						<th scope=“col”>Production</th>
						<th scope=“col”>Price</th>
						<th scope=“col”>Quantity</th>
						<th scope=“col”>Total</th>
						<th scope=“col”>Select</th>
						
					</tr>
				</thead>
				<%
					if(movieCartArray != null) {
				%>
				<tbody>
					<%
						for (int i = 0; i < movieCartArray.length; i++) {
										//Movie movieObj = movieArray[i];
										//MovieCart movieCartObj = movieCartArray[i];
										//System.out.println("MovieCart qty: " + movieCartObj.getQty());
										//System.out.println("Movie name: " + movieObj.getMovieName());
					%>
					<tr
						style="border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7; background: #fff; padding: 6px 6px 6px 12px; color: #4f6b72;">
						<td style="text-align: center;" scope=“row”><%=movieArray[i].getMovieName()%>
						</th>
						<td style="text-align: center;"><%=movieArray[i].getProdBanner()%></td>
						<td style="text-align: center;"><%=movieArray[i].getRentAmount()%></td>
						<td style="text-align: center;"><%=movieCartArray[i].getQty()%></td>
						<td style="text-align: center;"><%=movieCartArray[i].getQty()
						* movieArray[i].getRentAmount()%></td>
						<td style="text-align: center;"><input type="checkbox"
							name="checkboxId" value="<%=movieArray[i].getMovieId()%>"
							id="<%=movieArray[i].getMovieId()%>"
							onClick="AddToTempArrayForCheckout();"></td>
						

					</tr>
					<%
						}
					%>
					<tr></tr>
					<tr>
						<td colspan="2" style="text-align: center;"><INPUT
							TYPE="submit" name="Checkout" value="Add movies to Account"
							SRC="../LibraryIMG/proceed_to_payment_new.jpg" ALT="SUBMIT!">
						</td>
						<td colspan="2" style="text-align: center;"><INPUT
							TYPE="submit" name="MovieDetails" value="Continue Shopping"
							SRC="../LibraryIMG/continue_shopping.png" ALT="SUBMIT!"></td>
					</tr>
				</tbody>
				<%
					} else {
				%>
				<tr
					style="border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7; background: #fff; padding: 6px 6px 6px 12px; color: #4f6b72;">
					<td colspan="7" style="text-align: center;" scope="row">There
						are no records to display..</td>
				</tr>
				<%
					}
				%>
			</table>

		</form>
	</div>

	<script>
		function AddToTempArrayForCheckout() {
			var total = 0;
	<%ArrayList<Movie> movieArrayLstForCheckout = new ArrayList<Movie>(); 
		int j = 0;
			if(movieArray != null){
			for (j = 0; j < movieArray.length; j++) {%>
		var checkBoxId =
	<%=movieArray[j].getMovieId()%>
		;
			if (document.getElementById(checkBoxId).checked == true) {
				total = total + 1;
	<%movieArrayLstForCheckout.add(movieArray[j]);%>
		}
	<%}}%>
		
	<%if (user.getPreference().getPreferenceId() == 2) {%>
		if (total > 10) {
				alert("You can select only 10 movies at a time");
			}
	<%} else if (user.getPreference().getPreferenceId() == 1) {%>
		if (total > 2) {
				alert("You can select only 2 movies at a time");
			}
	<%}%>
		}
	</script>
</body>


</html>
