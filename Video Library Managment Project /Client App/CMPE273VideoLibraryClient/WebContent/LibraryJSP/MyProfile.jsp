<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="model.MovieCategory"
	import="model.Movie"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="Common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="../LibraryCSS/style.css" type="text/css" />
<link rel="stylesheet" href="../LibraryCSS/addtocart.css"
	type="text/css" />
<link rel="stylesheet" href="../LibraryCSS/movie_details_style.css"
	type="text/css">
<style type="text/css">
body {
	text-align: center;
}
</style>
</head>
<body>

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
		Movie[] boughtMovies = null;
		boughtMovies = (Movie[]) request.getAttribute("boughtMovies");
	%>
	<br />

	<nav>
	<ul class="fancyNav">

		<li><form id="BillHistoryForm" action="BillingHistory"
				method="post">
				<a href="javascript:;"
					onclick="javascript: document.getElementById('BillHistoryForm').submit();">My
					Bill History</a>
			</form></li>
		<li><form id="UpdateMyProfileForm" action="UpdateMyProfile"
				method="post">
				<a href="javascript:;"
					onclick="javascript: document.getElementById('UpdateMyProfileForm').submit();">Update
					My Profile</a>
			</form></li>

	</ul>
	</nav>
	<div class="Details_Div_Registration"
		style="padding-left: 350px; padding-top: 5px;">
		<table>
			<tr>
				<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Name&nbsp;&nbsp;</td>
				<td><input size="200px" class="idleField" type="text"
					readonly="readonly"
					value="<%=user.getFirstName() + " " + user.getLastName()%>" /></td>

				<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Member
					type&nbsp;&nbsp;</td>
				<td><input class="idleField" type="text" readonly="readonly"
					value="<%=user.getPreference().getPreferenceType()%>" /></td>
			</tr>
			<tr>
				<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Date of
					Birth&nbsp;&nbsp;</td>
				<td><input class="idleField" type="text" readonly="readonly"
					value="<%=user.getDob()%>" /></td>
				<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Sex&nbsp;&nbsp;</td>
				<td><input class="idleField" type="text" disabled="true"
					value="<%=user.getSex()%>" /></td>
			</tr>
			<tr>
				<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Address&nbsp;&nbsp;</td>
				<td><input class="idleField" type="text" readonly="readonly"
					value="<%=user.getAddress().getAddrLine1()%>" /></td>
				<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;City&nbsp;&nbsp;</td>
				<td><input class="idleField" type="text" readonly="readonly"
					value="<%=user.getAddress().getCity().getCityName()%>"></td>
			</tr>
			<tr>
				<td valign="bottom"></td>
				<td><input class="idleField" type="text" readonly="readonly"
					value="<%=user.getAddress().getAddrLine2()%>" /></td>
				<td valign="bottom">State&nbsp;&nbsp;</td>
				<td><input class="idleField" type="text" readonly="readonly"
					value="<%=user.getAddress().getState().getStateName()%>"></td>
			</tr>
			<tr>
				<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;ZipCode&nbsp;&nbsp;</td>
				<td><input class="idleField" name="zipcode" value=""
					type="text" readonly="readonly" /></td>
				<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Country&nbsp;&nbsp;</td>
				<td><input class="idleField" type="text" readonly="readonly"
					value="<%=user.getAddress().getCountry().getCountryName()%>"></td>
			</tr>
			<tr>

				<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Email
					ID&nbsp;&nbsp;</td>
				<td><input class="idleField" type="text" readonly="readonly"
					value="<%=user.getEmailId()%>" /></td>
				<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Contact Number</td>
				<td><input class="idleField" name="contact_num" value=""
					type="text" readonly="readonly" /></td>
			</tr>
			<% if(user.getPreference().getPreferenceId() == 1){ %>
			<tr>
				<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Balance&nbsp;&nbsp;</td>
				<td><input class="idleField" type="text" readonly="readonly"
					value="<%=user.getSubscriptionFee()%>" /></td>
			</tr>
			<% } %>
		</table>
	</div>



	<div class="Details_Div_Registration"
		style="padding-left: 250px; padding-top: 5px;">
		<table width="80%" style="margin-left: 20px; margin-top: 50px;">
			<thead>
				<th scope=col>Play</th>
				<th>Movie Name</th>
				<th>Production</th>
				<th>Release Date</th>
				<th>Movie Type</th>
				<th>Price</th>
				<th>Return</th>
			</thead>
			<tbody>
				<%
					Movie[] fetchedMovieDetails = (Movie[]) request
							.getAttribute("boughtMovies");
					int i = 0;
					if (fetchedMovieDetails != null) {
						System.out.println(fetchedMovieDetails[0].getMovieName());
						for (i = 0; i < fetchedMovieDetails.length; i++) {
							// This IF loop will exclude all those movie records which have 0 available copies
							if (!((fetchedMovieDetails[i].getAvailableCopies() == 0))) {
								String releasedDate = fetchedMovieDetails[i]
										.getReleaseDate().subSequence(0, 10).toString();
				%>
				<tr
					style="border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7; background: #fff; padding: 6px 6px 6px 12px; color: #4f6b72;">
					<td style="text-align: center;">
						<form id="PlayMovie" action="PlayMovie" method="post">
							<div class="form-row">
								<input type="hidden" name="hiddenPlayId"
									value="<%=fetchedMovieDetails[i].getMovieId() %>"> <input
									type="image" src="../LibraryIMG/playButton.png" alt="submit">
							</div>
						</form>
					</td>
					<td style="text-align: center;" scope="row"><%=fetchedMovieDetails[i].getMovieName()%></td>
					<td style="text-align: center;"><%=fetchedMovieDetails[i].getProdBanner()%></td>
					<!-- <td style = "text-align:center;"><%//=fetchedMovieDetails[i].getReleaseDate()%></td> -->
					<td style="text-align: center;"><%=releasedDate%></td>
					<td style="text-align: center;"><%=fetchedMovieDetails[i].getMovieCategory()
								.getMovieCategory()%></td>
					<td style="text-align: center;"><%=fetchedMovieDetails[i].getRentAmount()%></td>
					<td style="text-align: center;">
						<form id="ReturnMovie" action="ReturnMovie" method="post">
							<div class="form-row">
								<input type="hidden" name="hiddenReturnId"
									value="<%=fetchedMovieDetails[i].getMovieId()%>"> <input
									type="image" src="../LibraryIMG/returnButton.png" alt="submit"
									onclick="return confirmDelete();">
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
					<td colspan="7" style="text-align: center;" scope="row">There
						are no records to display..</td>
				</tr>
				<%
					}
				%>

			</tbody>
		</table>
	</div>
</body>
<script>
		function confirmDelete() {
			var answer = confirm("Are you sure you want to return the movie?")
			if (answer) {
				return true;
			} else {
				return false;
			}
		}
	</script>
</html>