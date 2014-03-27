<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<link rel="stylesheet" href="../LibraryCSS/style.css" type="text/css" />
<link rel="stylesheet" href="../LibraryCSS/addtocart.css"
	type="text/css" />
<link rel="stylesheet" href="../LibraryCSS/movie_details_style.css"
	type="text/css">
<script src="../LibraryJS/movie_details_jquery.js"
	type="text/javascript"></script>
</head>
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



<body>
<div id="wrapper" style='margin-left: 200px;'>


		<div id="slider">

			<img class="scrollButtons left" src="../LibraryIMG/leftarrow.png">

			<div style="overflow: hidden;" class="scroll">

				<div class="scrollContainer">
					<%
						
					%>
					}
					<div class="panel" id="panel1">
						<div class="inside">

							<img src="../LibraryIMG/1.jpg" alt="picture" />

						</div>
					</div>
					<%
						
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
<div>
<nav >
		<ul class="fancyNav">
			<li style = "width:1200px;" id="Label"><a>My Account: Please select a movie to play.. </a></li></ul></nav>

</div>

<div class = "videocontainer">
							<video width="320" height="240" controls="controls">
								<source src="..\LibraryMOVIE\YehMera.ogg" type="video/ogg"></source>
							</video>
						</div>
</body>
</html>