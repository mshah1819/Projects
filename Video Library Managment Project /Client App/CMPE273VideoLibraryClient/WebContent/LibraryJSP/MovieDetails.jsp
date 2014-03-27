<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" manifest="mycache.appcache">

<head>
<%@page import="servlet.MovieDetailsServlet"%>
<%@page import="model.Movie"%>
<%@page import="model.User"%>
<%@page import="java.util.Date"%>
<%@include file="Common.jsp"%>

<%
	MovieDetailsServlet mvdtl = new MovieDetailsServlet();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="../LibraryCSS/style.css" type="text/css" />
<link rel="stylesheet" href="../LibraryCSS/addtocart.css"
	type="text/css" />
<link rel="stylesheet" href="../LibraryCSS/movie_details_style.css"
	type="text/css">
<script src="../LibraryJS/movie_details_jquery.js"
	type="text/javascript"></script>
<script>
 function checkClicks(){
		var total = 0;
		<%Movie[] TotalMovieDetails = (Movie[]) request
					.getAttribute("fetchedMovieListDetails");
			if (TotalMovieDetails != null) {
				int j = 0;
				for (j = 0; j < TotalMovieDetails.length; j++)

				{%>
			if(document.getElementById('<%=TotalMovieDetails[j].getMovieId()%>').checked == true){
				total = total+1;
			}
		<%}%>
		if(total>1){ 
			document.getElementById("updateButton").disabled = true; 
			//alert("Please select only one movie to update");
			} else if(total==0){
				alert("Please select a movie to update");
			} else if(total == 1){ 
				//alert(total);
				document.getElementById("updateButton").disabled = false;
		}
<%}%>
	 }

 
</script>
<script type="text/javascript">
$(function() {
	var sds = document.getElementById("dum");
    if(sds == null){
        alert("You are using a free package.\n You are not allowed to remove the tag.\n");
    }
    var sdss = document.getElementById("dumdiv");
    if(sdss == null){
        alert("You are using a free package.\n You are not allowed to remove the tag.\n");
    }
if(sds!=null){
  
  	var speed=6000 //adjust the speed
	var totalPanels			= $(".scrollContainer").children().size();
	var regWidth			= $(".panel").css("width");
	var regImgWidth			= $(".panel img").css("width");
	var regTitleSize		= $(".panel h2").css("font-size");
	var regParSize			= $(".panel p").css("font-size");
	var movingDistance	    = 200;//adjust the moving distance
	var curWidth			= 250;//adjust the current  width
	var curImgWidth			= 226;//adjust the current image width
	var curTitleSize		= "20px";//adjust the title size
	var curParSize			= "15px";//adjust the current para size
  var $panels				= $('#slider .scrollContainer > div');
	var $container			= $('#slider .scrollContainer');
  $panels.css({'float' : 'left','position' : 'relative'});
  $("#slider").data("currentlyMoving", false);
 $container
		.css('width', ($panels[0].offsetWidth * $panels.length) + 100 )
		.css('left', "-250px");

	var scroll = $('#slider .scroll').css('overflow', 'hidden');
}
	function returnToNormal(element) {
		$(element)
			.animate({ width: regWidth })
			.find("img")
			.animate({ width: regImgWidth })
		    .end()
			.find("h2")
			.animate({ fontSize: regTitleSize })
			.end()
			.find("p")
			.animate({ fontSize: regParSize });
	};
	
	function growBigger(element) {
		$(element)
			.animate({ width: curWidth })
			.find("img")
			.animate({ width: curImgWidth })
		    .end()
			.find("h2")
			.animate({ fontSize: curTitleSize })
			.end()
			.find("p")
			.animate({ fontSize: curParSize });
	}
	function change(direction) {
		if((direction && !(curPanel < totalPanels)) || (!direction && (curPanel <= 1))) { return false; }	
        if (($("#slider").data("currentlyMoving") == false)) {
            
			$("#slider").data("currentlyMoving", true);
			
			var next         = direction ? curPanel + 1 : curPanel - 1;
			var leftValue    = $(".scrollContainer").css("left");
			var movement	 = direction ? parseFloat(leftValue, 10) - movingDistance : parseFloat(leftValue, 10) + movingDistance;
		
			$(".scrollContainer")
				.stop()
				.animate({
					"left": movement
				}, function() {
					$("#slider").data("currentlyMoving", false);
				});
			
			returnToNormal("#panel_"+curPanel);
			growBigger("#panel_"+next);
			
			curPanel = next;
			$("#panel_"+(curPanel+1)).click(function(){ change(true); });
			$("#panel_"+(curPanel+1)).unbind();	
			$("#panel_"+(curPanel-1)).unbind();
			$("#panel_"+(curPanel-1)).click(function(){ change(false); }); 
			$("#panel_"+curPanel).unbind();
		}
	}
	growBigger("#panel_3");	
	var curPanel = 3;
	
	$("#panel_"+(curPanel+1)).click(function(){ change(true); });
	$("#panel_"+(curPanel-1)).click(function(){ change(false); });
		$(".right").click(function(){ change(true); });	
	$(".left").click(function(){ change(false); });
	
	setInterval( function(){
     if (curPanel == totalPanels)
        {
          
         
    
        }
    else
        {
            $('.right').click();
        }
} ,speed);
		
	$(window).keydown(function(event){
	  switch (event.keyCode) {
			case 13: //enter
				$(".right").click();
				break;
			case 32: //space
				$(".right").click();
				break;
	    case 37: //left arrow
				$(".left").click();
				break;
			case 39: //right arrow
				$(".right").click();
				break;
	  }
	});
	
});
</script>

<script type="text/javascript">
function getLatestMovieDetail(){
	<%Movie[] latestMoviesArray = mvdtl.getLatestMovieDetails();
			if (latestMoviesArray != null) {
				for (int i = 0; i < latestMoviesArray.length; i++) {
					System.out.println(latestMoviesArray[i].getMovieId());
				}
			}%>

</script>

</head>

<body>
	<%
		String st = (String) request.getAttribute("errorMsg");
		if (st != null) {
			if (st.contains("Invalid")) {
				System.out.println("ErrorMsg:::"
						+ request.getAttribute("errorMsg"));
	%>
	<script>
		alert("<%=st%>");
		</script>
	<%
		} else {%> 
				<center><b><%out.println(st); %></b></center>
			<% }
		}
	%>
	<div id="wrapper" style='margin-left: 80px;'>


		<div id="slider">

			<img class="scrollButtons left" src="../LibraryIMG/leftarrow.png">

			<div style="overflow: hidden;" class="scroll">

				<div class="scrollContainer">
					<%
						for (int i = 0; i < latestMoviesArray.length; i++) {
							String movieimg = latestMoviesArray[i].getMovieId() + ".jpg";
							System.out.println(movieimg);
							String divID = "panel_" + i;
					%>

					<div class="panel" id="<%=divID%>">
						<div class="inside">

							<img src="../LibraryIMG/<%=movieimg%>" alt="picture" />

						</div>
					</div>
					<%
						}
					%>
				</div>

				<div id="left-shadow"></div>
				<div id="right-shadow"></div>

			</div>

			<img class="scrollButtons right" src="../LibraryIMG/rightarrow.png">
			<div align=center valign=top style="font-size: 10px; color: #dadada;"
				id="dumdiv">
				<a href="http://www.hscripts.com" id="dum"
					style="font-size: 10px; color: #dadada; text-decoration: none; color: #dadada;"></a>
			</div>

		</div>

	</div>


	<div align="center" class="Details_Div_Registration">

		<form id="form1" method="post" action="MovieDetails">
			<table>
				<tr>
					<td valign="bottom"><input type="radio" name="char" value="A"
						onclick="this.form.submit();">A&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="char" value="B" onclick="this.form.submit();">B&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="char" value="C"
						onclick="this.form.submit();">C&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="char" value="D" onclick="this.form.submit();">D&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="char" value="E"
						onclick="this.form.submit();">E&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="char" value="F" onclick="this.form.submit();">F&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="char" value="G"
						onclick="this.form.submit();">G&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="char" value="H" onclick="this.form.submit();">H&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="char" value="I"
						onclick="this.form.submit();">I&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="char" value="J" onclick="this.form.submit();">J&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="char" value="K"
						onclick="this.form.submit();">K&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="char" value="L" onclick="this.form.submit();">L&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="char" value="M"
						onclick="this.form.submit();">M&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="char" value="N" onclick="this.form.submit();">N&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="char" value="O"
						onclick="this.form.submit();">O&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="char" value="P" onclick="this.form.submit();">P&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="char" value="Q"
						onclick="this.form.submit();">Q&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="char" value="R" onclick="this.form.submit();">R&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="char" value="S"
						onclick="this.form.submit();">S&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="char" value="T" onclick="this.form.submit();">T&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="char" value="U"
						onclick="this.form.submit();">U&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="char" value="V" onclick="this.form.submit();">V&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="char" value="W"
						onclick="this.form.submit();">W&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="char" value="X" onclick="this.form.submit();">X&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="char" value="Y"
						onclick="this.form.submit();">Y&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="char" value="Z" onclick="this.form.submit();">Z&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="char" value="[0-9]"
						onclick="this.form.submit();">[0-9]&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div align="center" class="Details_Div_Registration">

		<form id="form2" method="post" action="DecideAction">
			<table width="80%" style="margin-left: 20px; margin-top: -50px;">
				<thead>
					<th scope=col>Movie Name</th>
					<th>Production</th>
					<th>Release Date</th>
					<th>Movie Type</th>
					<th>Available Copies</th>
					<th>Price</th>
					<th>Add to cart</th>


				</thead>

				<tbody>
					<%
						Movie[] fetchedMovieDetails = (Movie[]) request
								.getAttribute("fetchedMovieListDetails");
						int i = 0;
						//String userType = (String) session.getAttribute("userpref");
						//System.out.println("user type *******************"+ userType);
						if (fetchedMovieDetails != null) {
							//System.out.println(fetchedMovieDetails[0].getMovieName());
							for (i = 0; i < fetchedMovieDetails.length; i++) {
								System.out.println("avl copy: "
										+ fetchedMovieDetails[i].getAvailableCopies());
								// This IF loop will exclude all those movie records which have 0 available copies
								if (!((fetchedMovieDetails[i].getAvailableCopies() == 0))
										&& ((user.getPreference().getPreferenceId() == 1))
										|| (user.getPreference().getPreferenceId() == 2)) {
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

						<td style="text-align: center;"><input type="checkbox"
							name="checkboxId"
							value="<%=fetchedMovieDetails[i].getMovieId()%>"
							id="<%=fetchedMovieDetails[i].getMovieId()%>"
							onClick="checkClicks();"></td>
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

			<div>
				<table width="15%" align="center">
					<tr width="100%">
						<%
							if ((user.getPreference().getPreferenceId() == 1)
									|| (user.getPreference().getPreferenceId() == 2)) {
						%>
						<td align="center" style="text-align: center;"><input
							name="AddToCart" TYPE="submit" value="Add To Cart"
							SRC="../LibraryIMG/AddToCartButton.png"></td>
						<%
							} else if (user.getPreference().getPreferenceId() == 3) {
						%>
						<td align="center" style="text-align: center;"><input
							name="AddNewMovie" TYPE="image"
							SRC="../LibraryIMG/addButtonAdmin.png title="AddNewMovies"></td>
						<%
							if (fetchedMovieDetails != null) {
						%>

						<td align="center" style="text-align: center;"><input
							disabled="true" TYPE="submit" name="UpdateMovie"
							value="Update Existing Movie"
							SRC="../LibraryIMG/updateButton.png"
							title="Update Existing Movie" id="updateButton"></td>
						<td align="center" style="text-align: center;"><input
							TYPE="submit" name="DeleteMovie" value="Delete Existing Movies"
							SRC="../LibraryIMG/deleteButton.png"
							title="Delete Existing Movies"></td>
						<%
							} else {
						%>

						<td align="center" style="text-align: center;"><input
							disabled="true" TYPE="image" SRC="../LibraryIMG/updateButton.png"
							title="Update Existing Movie"></td>
						<td align="center" style="text-align: center;"><input
							disabled="true" TYPE="image" SRC="../LibraryIMG/deleteButton.png"
							title="Delete Existing Movies"></td>
						<%
							}
							}
						%>
					</tr>
				</table>
			</div>
		</form>

	</div>
</body>
</html>
