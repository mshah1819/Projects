<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" manifest="mycache.appcache">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>My Video Library</title>
<link rel="stylesheet" href="../LibraryCSS/style.css" type="text/css" />


<script type="text/javascript">
	$(document).ready(function() {
		$('input[type="text"]').addClass("idleField");
		$('input[type="password"]').addClass("idleField");
		$('input[type="text"]').focus(function() {
			$(this).removeClass("idleField").addClass("focusField");
			if (this.value == this.defaultValue) {
				this.value = '';
			}
			if (this.value != this.defaultValue) {
				this.select();
			}
		});

		$('input[type="password"]').focus(function() {
			$(this).removeClass("idleField").addClass("focusField");
			if (this.value == this.defaultValue) {
				this.value = '';
			}
			if (this.value != this.defaultValue) {
				this.select();
			}
		});
		$('input[type="text"]').blur(function() {
			$(this).removeClass("focusField").addClass("idleField");
			if ($.trim(this.value) == '') {
				this.value = (this.defaultValue ? this.defaultValue : '');
			}
		});
		$('input[type="password"]').blur(function() {
			$(this).removeClass("focusField").addClass("idleField");
			if ($.trim(this.value) == '') {
				this.value = (this.defaultValue ? this.defaultValue : '');
			}
		});
	});
</script>
</head>

<body
	style="background: url(../LibraryIMG/background.jpg) repeat-x scroll left top transparent;">
	<div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<!-- <td width="10%"><img src="../LibraryIMG/movietape.jpg"
					width="125" height="167" /></td> -->
				<td align="center" width="90%"><Font color="ffffff"
					face="WildWest" size=10>My Video Library</Font></td>
			</tr>


		</table>
	</div>

	<!-- header ends-->

	<nav>
		<ul class="fancyNav">
			<li id="home"><a href="../LibraryJSP/Login.jsp" class="homeIcon">Home</a></li>
			<li id="art"><a href="#">Art and Culture</a></li>
			<li id="cinema"><a href="../LibraryJSP/Login.jsp">Cinema</a></li>
			<li id="history"><a href="#">History</a></li>
			<li id="sports"><a href="#">Sports</a></li>
			<li id="music"><a href="#">Music and dance</a></li>
			<li id="science"><a href="#">Science and Health</a></li>
			<li id="tourism"><a href="#">Tourism</a></li>
			<li id="contact"><a href="#">Contact us</a></li>
			<li id="login"><a href="#" class="loginIcon">Login</a></li>
		</ul>
	</nav>

	<!-- <div class="container1">-->
	<div class="Container">
		<div class="ContentBtnBg">

			<table style="padding-left: 70px; padding-top: 130px">
				<tr>
					<td>
						<div class="frmContainer">
							<form id="LoginForm" method="post" action="Login"
								onsubmit="return validateForm()">
								<div class="Details_Div" style="padding-left: 10px">
									<%
										System.out.println("error Msg in Login JSP:"
												+ request.getAttribute("errorMsg"));
										if (request.getAttribute("errorMsg") != null
												&& request.getAttribute("errorMsg") != "SUCCESS") {
									%>
									<div align="center">
										<%
											out.println(request.getAttribute("errorMsg"));
										%>
									</div>
									<%
										}
									%>

									<table
										style="border-collapse: collapse; border-spacing: inherit">
										<tr>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;Username&nbsp;&nbsp;</td>

											<td><input class="idleField" name="username" value=""
												type="text" /></td>
										</tr>
										<tr>

										</tr>
										<tr>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;Password&nbsp;&nbsp;</td>

											<td><input class="idleField" name="password" value=""
												type="password" /></td>
										</tr>
										<tr>
											<td style="text-align: right; padding-top: 5px;" colspan="3"><input
												type="checkbox" name="chbox" id="chbox"> <label>Remember
													me</label></td>
										</tr>
										<tr>
											<td style="text-align: center; padding-left: 10px;"
												colspan="3"><input type="image"
												src="../LibraryIMG/login_btn.gif" alt="submit"
												title="submit" /></td>

										</tr>
										<tr>
											<td><label id="new user">&nbsp;&nbsp;<a
													href="../LibraryJSP/Registration.jsp"><Font
														color="000000">Register Now</Font></a></label></td>
										</tr>
									</table>
								</div>
							</form>
						</div>


					</td>
					<td><div class="container1">
							<video width="320" height="240" controls="controls">
								<source src="..\LibraryMOVIE\1.ogg" type="video/ogg"></source>
							</video>
						</div></td>
				</tr>
			</table>
		</div>
	</div>

	<script type="text/javascript">
		function validateForm() {
			var x = document.forms["LoginForm"]["username"].value;
			if (x == null || x == "") {
				alert("Enter a valid username");
				return false;
			} else {
				if (/[^a-zA-Z0-9]/.test(x)) {
					alert("You have entered an invalid username!");
					return (false);
				}
				;
			}
			x = document.forms["LoginForm"]["password"].value;
			if (x == null || x == "") {
				alert("Enter Password");
				return false;
			}
			;
		};
	</script>

</body>

</html>
