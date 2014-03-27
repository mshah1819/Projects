<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="model.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" href="../LibraryCSS/style.css" type="text/css" />
<link rel="stylesheet" href="../LibraryCSS/addtocart.css"
	type="text/css" />
<link rel="stylesheet" href="../LibraryCSS/movie_details_style.css"
	type="text/css">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../LibraryCSS/style.css" />

</head>
<body style="background: url(../LibraryIMG/background.jpg) repeat-x scroll left top transparent"
	class="main">
	<%
		User user = (User) session.getAttribute("user");
	%>

	<div align="center">
		<!--  table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>

				<td width="10%"><img src="../LibraryIMG/movietape.jpg"
					width="70" height="70" /></td>
				<td align="left" width="90%"><font color="ffffff"
					face="WildWest" size=8>Hello <%=user.getDisplayName()%> !
						Welcome to the Video Library..
				</font></td>
			</tr>
		</table>-->
		<%
			if (user.getPreference().getPreferenceId() == 3) {
		%>

		<!-- <form method="post" action="DecideAction">
			<table>
				<tr>
					<td>
					<td><input type="submit" name="Home" value="Home"></td>
					<td><input type="submit" name="Movies" value="Movies"></td>
					<td><input type="submit" name="Users" value="Users"></td>
					<td><input type="submit" name="Revenue" value="Revenue"></td>
					<td><input type="submit" name="SearchUser" value="SearchUser"></td>
					<td><input type="submit" name="SearchMovie" value="SearchMovie"></td>
					<td><input type="submit" name="SignOut" value="Sign Out"></td>
				</tr>
			</table>
		</form>-->

		<nav>
		<ul class="fancyNav">

			<li><form id="HomeForm" action="Home.jsp" method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('HomeForm').submit();"
						class="homeIcon">Home</a>
				</form></li>				
			<li><a href="#">Welcome <%=user.getDisplayName() %></a>
				</li>	
			<li><form id="SearchMoviesForm" action="MovieCriteriaSearch.jsp"
					method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('SearchMoviesForm').submit();">Movies</a>
				</form></li>
			<li><form id="SearchUsersForm" action="UserCriteriaSearch.jsp" method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('SearchUsersForm').submit();">Users</a>
				</form></li>
			<li><form id="SignOutForm" action="LogOut" method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('SignOutForm').submit();">Sign
						Out</a>
				</form></li>

		</ul>
		</nav>

		<%
			} else if (user.getPreference().getPreferenceId() == 2) {
		%>
		<nav>
		<ul class="fancyNav">

			<li><form id="HomeForm" action="Home.jsp" method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('HomeForm').submit();"
						class="homeIcon">Home</a>
				</form></li>
			<li><form id="MyProfileForm" action="MyProfile" method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('MyProfileForm').submit();">My
						Profile</a>
				</form></li>
			<li><form id="MoviesForm" action="MovieDetails.jsp"
					method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('MoviesForm').submit();">Movies</a>
				</form></li>
			<li><form id="MyCartForm" action="RetrieveCart" method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('MyCartForm').submit();">My
						Cart</a>
				</form></li>
			<li><form id="SignOutForm" action="LogOut" method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('SignOutForm').submit();">Sign
						Out</a>
				</form></li>

		</ul>
		</nav>
		<%
			} else if (user.getPreference().getPreferenceId() == 1) {
		%>
		<nav>
		<ul class="fancyNav">

			<li><form id="HomeForm" action="Home.jsp" method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('HomeForm').submit();"
						class="homeIcon">Home</a>
				</form></li>
			<li><form id="MyProfileForm" action="MyProfile" method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('MyProfileForm').submit();">My
						Profile</a>
				</form></li>
			<li><form id="MoviesForm" action="MovieDetails.jsp"
					method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('MoviesForm').submit();">Movies</a>
				</form></li>
			<li><form id="MyCartForm" action="RetrieveCart" method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('MyCartForm').submit();">My
						Cart</a>
				</form></li>
			<li><form id="AddBalanceForm" action="AddBalance.jsp"
					method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('AddBalanceForm').submit();">Add
						Balance</a>
				</form></li>
			<li><form id="SignOutForm" action="LogOut" method="post">
					<a href="javascript:;"
						onclick="javascript: document.getElementById('SignOutForm').submit();">Sign
						Out</a>
				</form></li>

		</ul>
		</nav>
		<%
			}
		%>

	</div>
</body>
</html>