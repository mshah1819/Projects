<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>My Video Library</title>
<link rel="stylesheet" type="text/css" href="../LibraryCSS/style.css" />
<%@include file="Common.jsp"%>

<script type="text/javascript">
	$(function() {

		$('#container').tabs();
		$('#showcontent2').click(function() {
			$(this).parents('div').parents('div').eq(1).triggerTab(2);
			return false;
		});

		$('#showcontent3').click(function() {
			$(this).parents('div').parents('div').eq(1).triggerTab(3);
			return false;
		});

		$('#showcontent4').click(function() {
			$(this).parents('div').parents('div').eq(1).triggerTab(4);
			return false;
		});
	});
</script>

</head>
<body>
	<br />
	<div align="center" >
	<table width="100%" border="0" cellspacing="0" cellpadding="0">

		<tr>

			<td style="padding-left: 160px; padding-top: 15px;"><img
				src="../LibraryIMG/collage.jpg" style="vertical-align: bottom;"
				usemap="#videomap" /> <map name="videomap">
					<area shape="rect" coords="0,0,160,240" href="#"
						title="Art and Culture" />
					<area shape="rect" coords="160,0,520,165" href="#" title="Tourism" />
					<area shape="rect" coords="520,0,990,275" href="#" title="Music" />
					<area shape="rect" coords="590,280,990,500" href="#" title="Sports" />
					<area shape="rect" coords="160,165,250,230" href="#"
						title="History" />
					<area shape="rect" coords="260,230,520,580" href="#"
						title="History" />
					<area shape="rect" coords="520,300,590,400" href="#"
						title="History" />
					<area shape="rect" coords="0,240,250,550"
						href="../LibraryJSP/MovieDetails.jsp" title="Cinema" />

				</map></td>
		</tr>
	</table>
	</div>

</body>
</html>

