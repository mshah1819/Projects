<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="model.MovieCategory"
	import="servlet.RegistrationServlet" import="model.Movie"%>
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
	<%!String[] cityList, stateList;
	public String[] citylist() {
		String[] cityList = null;
		int i = 0, j = 0;
		RegistrationServlet rg = new RegistrationServlet();
		cityList = rg.listCity("California");
		return cityList;
	}
	%>

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
	<br />
	<div align="center" class="Details_Div_Registration"
		style="padding-left: 0px; padding-top: 5px;">
		<%
			cityList = citylist();
		%>
		<b> Enter your search criteria!</b> <br>
		<form id="SearchUserForm" method="post" action="SearchUser"
			onSubmit="return validateForm()">
			<table align="center">
				<tr>
					<td>Display Name:</td>
					<td><input class="idleField" type="text" name="displayName" />&nbsp;&nbsp;</td>
					<td>User type:</td>
					<td><select class="idleField" name="Pref">
							<option>All Members</option>
							<option>Simple Customer</option>
							<option>Premium Member</option>
					</select></td>
				</tr>
				<tr>
					<td>Login id:</td>
					<td><input class="idleField" type="text" name="loginId" /></td>
					<td>Email id:</td>
					<td><input class="idleField" type="text" name="emailId" /></td>
				</tr>
				<tr>
					<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;City:&nbsp;&nbsp;</td>
					<td><select class="idleField" name="city">
							<option value=""> </option>
							<%
								for (int k = 0; k < cityList.length; k++) {
							%>
							<option value="<%=cityList[k]%>"><%=cityList[k]%></option>
							<%
								}
							%>
					</select></td>
					<td>Zipcode:</td>
					<td><input class="idleField" type="text" name="zipcode" /></td>
				</tr>

			</table>
			<div class="form-row">
				<nav>
				<ul class="fancyNav">
					<li><a href="javascript:;"
						onclick="javascript: document.getElementById('SearchUserForm').submit();">Search</a>
					</li>
				</ul>
				</nav>
			</div>
		</form>
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
	<div align="center" class="Details_Div_Registration">
		<table width="80%" style="margin-left: 20px; margin-top: -30px;">
				<thead>
					<th scope=col>Name</th>
					<th>Sex</th>
					<th>Membership ID</th>
					<th>Member Type</th>
					<th>Bills</th>
					<th>Delete</th>

				</thead>
				<tbody>
					<%
						User[] fetchedUserDetails = (User[]) request
								.getAttribute("userList");
						int i = 0;
						if (fetchedUserDetails != null) {
							//System.out.println(fetchedMovieDetails[0].getMovieName());
							for (i = 0; i < fetchedUserDetails.length; i++) {
								// This IF loop will exclude all those movie records which have 0 available copies
					%>
					<tr
						style="border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7; background: #fff; padding: 6px 6px 6px 12px; color: #4f6b72;">
						<td style="text-align: center;" scope="row"><%=fetchedUserDetails[i].getFirstName()%>
							<%=fetchedUserDetails[i].getLastName()%></td>
						<td style="text-align: center;"><%=fetchedUserDetails[i].getSex()%></td>
						<td style="text-align: center;"><%=fetchedUserDetails[i].getMembershipId()%></td>
						<%
							if (fetchedUserDetails[i].getPreferenceId() == 1) {
						%>
						<td style="text-align: center;">Simple Customer</td>
						<%
							} else if (fetchedUserDetails[i].getPreferenceId() == 2) {
						%>
						<td style="text-align: center;">Premium Member</td>
						<%
							}
						%>
						<td style="text-align: center;">
							<form id="BillHistory" method="post" action="BillingHistory">
								<div class="form-row">
									<input type="hidden" name="hiddenBillingId"
										value="<%=fetchedUserDetails[i].getId()%>"> <input
										type="image" src="../LibraryIMG/updateButton.png" alt="submit"">
								</div>
							</form>
						</td>
						<td style="text-align: center;">
							<form id="DeleteUser" action="DeleteUser" method="post">
								<div class="form-row">
									<input type="hidden" name="hiddenDeleteId"
										value="<%=fetchedUserDetails[i].getId()%>"> <input
										type="image" src="../LibraryIMG/deleteButton.png" alt="submit"
										onclick="return confirmDelete();">
								</div>
							</form>
						</td>

					</tr>
					<%
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
	<script>
		function confirmDelete() {
			var answer = confirm("Are you sure you want to delete?")
			if (answer) {
				return true;
			} else {
				return false;
			}
		}
	</script>


</body>
</html>