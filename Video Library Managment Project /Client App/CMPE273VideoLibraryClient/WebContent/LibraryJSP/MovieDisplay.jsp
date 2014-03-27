<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*"%>
<%@page import="model.*"%>
<%@ include file="Common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="../LibraryCSS/style.css" type="text/css" />
</head>
<script>
function insert(){
	<%session.setAttribute("buttonValue", "insert");%>
	document.file_upload.submit();
}
function update(){
	<%session.setAttribute("buttonValue", "update");%>
	document.file_upload.submit();
}
</script>


<%
	String st = (String) request.getAttribute("action");
	if (st != null) {
		if (st.contains("Invalid")) {
			st = st.replace("Invalid", "\\nInvalid");
%>
<script>
			alert("<%=st%>");</script>

<%
	}
	}
%>
<script>


function file(){
	<%String absolutePath = request.getServletContext().getRealPath("/");
			System.out.println("file absolute path in file upload servelet"
					+ absolutePath);%>	
}
</script>
<%
	Movie movie = (Movie) session.getAttribute("movie");
	if (movie != null)
		System.out.println(movie.getMovieName());
	String usertype = "";
	if (session != null) {

		int type = user.getPreference().getPreferenceId();
		System.out.println("UserPref in bulkupload jsp" + type);
		usertype = Integer.toString(type);
		System.out.println("UserPref in bulkupload jsp" + usertype);

	}
%>
<script>
	function hideDiv() {
		//alert("hi.....................");
		document.getElementById("single_movie_details").style.display = 'none';
		document.getElementById("bulk_upload").style.display = 'block';

	}



	function populateDiv(){
	<%String type = request.getParameter("result");
			System.out.println(type);
			System.out.println(":::::ButtonType:::::::::::" + type);
			if (type != null) {
				if (type.equals("update")) {
					System.out.println(":::::ButtonType:::::::::::" + type
							+ ":::::::::MovirObject:::::::::"
							+ movie.getMovieName());%>
		var movieName = '<%=movie.getMovieName()%>';
		var movieProdBanner = '<%=movie.getProdBanner()%>';
		var movieReleaseDate = '<%=movie.getReleaseDate()%>';
		var movieAvailableCopies = '<%=movie.getAvailableCopies()%>';
		var movieCategory = '<%=movie.getMovieCategory().getMovieCategory()%>';
		var movieTotalCopies = '<%=movie.getTotalCopies()%>';
		var movieRentAmount = '<%=movie.getRentAmount()%>';
		document.details.movie_name.value = movieName;
		document.details.movie_production.value = movieProdBanner;
		document.details.SnapHost_Calendar.value = movieReleaseDate;
		document.details.movie_ac.value = movieAvailableCopies;
		document.details.movie_type.value = movieCategory;
		document.details.movie_tc.value = movieTotalCopies;
		document.details.movie_ra.value = movieRentAmount;
<%}
			}%>
	}
</script>
<style>
.movie_div {
	padding-left: 480px;
	padding-top: 40px;
	display: block;
}

.movie_div2 {
	padding-left: 480px;
	padding-top: 40px;
	display: none;
}

table.movie {
	width: 580px;
	background-color: #fafafa;
	border: 1px #000000 solid;
	border-collapse: collapse;
	border-spacing: 0px;
}

td.movie_det {
	background-color: #99CCCC;
	border: 1px #000000 solid;
	font-family: Verdana;
	font-weight: bold;
	font-size: 18px;
	color: #404040;
}

td.movie_cnct {
	border-bottom: 1px #6699CC dotted;
	text-align: left;
	font-family: Verdana, sans-serif, Arial;
	font-weight: normal;
	font-size: .7em;
	color: #404040;
	background-color: #fafafa;
	padding-top: 4px;
	padding-bottom: 4px;
	padding-left: 8px;
	padding-right: 0px;
}
</style>





<body onload="populateDiv();">
	<div id="single_movie_details" class="movie_div"
		style="padding-left: 380px; padding-top: 5px;">
		<form id="details" name="details" method="post"
			action="AddUpdateDelete">
			<table class="movie" cellspacing="0" summary="movie template">
				<tr>
					<td class="movie_det" colspan="3">Movie Details</td>
				</tr>
				<tr>
					<td><img src="../LibraryIMG/Movie_poster_i_robot.jpg" /></td>
					<td class="movie_cnct">
						<table>

							<tr>
								<td>Movie Name :</td>
								<%
									if (!(usertype.equals("3"))) {
								%>
								<td><input name="movie_name" value="" type="text"
									id="movie_name" readonly="readonly" /></td>
								<%
									} else {
								%>
								<td><input name="movie_name" type="text" maxlength="20" /></td>
								<%
									}
								%>
							</tr>
							<tr>
								<td>Movie Type :</td>
								<%
									if (!(usertype.equals("3"))) {
								%>
								<td><input name="movie_type" value="" type="text"
									readonly="readonly" /></td>
								<%
									} else {
								%>
								<td><input name="movie_type" value="" type="text"
									maxlength="10" /></td>
								<%
									}
								%>
							</tr>
							<tr>
								<td>Movie Production :</td>
								<%
									if (!(usertype.equals("3"))) {
								%>
								<td><input name="movie_production" value="" type="text"
									readonly="readonly" /></td>
								<%
									} else {
								%>
								<td><input name="movie_production" value="" type="text"
									maxlength="15" /></td>
								<%
									}
								%>
							</tr>
							<tr>
								<td>Movie Release Date :</td>
								<%
									if (!(usertype.equals("3"))) {
								%>
								<td><input name="movie_rd" value="" type="text"
									readonly="readonly" /></td>
								<%
									} else {
								%>
								<td><script type="text/javascript"
										src="http://www.snaphost.com/jquery/Calendar.aspx?dateFormat=dd/mm/yy"></script>
								</td>
								<%
									}
								%>
							</tr>
							<tr>
								<td>Rent Amount :</td>
								<%
									if (!(usertype.equals("3"))) {
								%>
								<td><input name="movie_ra" value="" type="text"
									readonly="readonly" /></td>
								<%
									} else {
								%>
								<td><input name="movie_ra" value="" type="text"
									maxlength="5" /></td>
								<%
									}
								%>
							</tr>
							<tr>
								<td>Movie Total Copies :</td>
								<%
									if (!(usertype.equals("3"))) {
								%>
								<td><input name="movie_tc" value="" type="text"
									readonly="readonly" /></td>
								<%
									} else {
								%>
								<td><input name="movie_tc" value="" type="text"
									maxlength="3" /></td>
								<%
									}
								%>
							</tr>
							<tr>
								<td>Available Copies :</td>
								<%
									if (!(usertype.equals("3"))) {
								%>
								<td><input name="movie_ac" value="" type="text"
									readonly="readonly" /></td>
								<%
									} else {
								%>
								<td><input name="movie_ac" value="" type="text"
									readonly="readonly" /></td>
								<%
									}
								%>
							</tr>
							<%
								if ((usertype.equals("3"))) {
							%>
							<tr>
								<td colspan="2" align="center"><input type="submit"
									alt="submit" value="Add/Update - Movie"
									title="Add/Update - Movie" /></td>
								<%
									} else {
								%>
							</tr>
							<tr>
								<td align="center"><input type="image"
									src="../LibraryIMG/AddToCartButton.png" alt="submit"
									title="submit" onclick="" /></td>
								<%
									}
								%>
							</tr>
						</table>

					</td>
				</tr>
				<%
					if ((usertype.equals("3"))) {
				%>
				<!-- <tr>


				<td class="movie_det" colspan="3" align="center"><input
					type="button" value="Bulk Upload Movie" alt="submit" title="submit"
					onclick="javascript:hideDiv();" /></td>

			</tr> -->
				<%
					}
				%>
			</table>
		</form>
	</div>
	<div id="bulk_upload" class="movie_div2">
		<form name="file_upload" method="post" action="UploadServlet"
			enctype="multipart/form-data">
			<table class="movie" cellspacing="0" summary="movie template">
				<tr>
					<td class="movie_det" colspan="3">Bulk Upload Movie Details</td>
				</tr>
				<tr>
					<td align="right"><FONT color="#FF0000">* </FONT>File
						Description&nbsp;</td>
					<td><input type="text" name="movie_des" /></td>
				</tr>

				<tr class="bgwhite">
					<td align="right"><FONT color="#FF0000">* </FONT>File
						Details&nbsp;</td>
					<td><input type="file" name="Upload_File" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Insert" alt="insert"
						name="insert" title="insert" onclick="insert();" /></td>

					<td><input type="submit" name="insert_update"
						value="Insert/Update" alt="submit" title="insert/update"
						onclick="update();" /></td>
				</tr>
			</table>
		</form>

	</div>
</body>
</html>