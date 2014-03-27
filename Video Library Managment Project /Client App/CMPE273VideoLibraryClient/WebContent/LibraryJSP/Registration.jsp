
<!DOCTYPE html>


<%@page import="servlet.RegistrationServlet"%>
<html xmlns="http://www.w3.org/1999/xhtml" manifest="mycache.appcache">
<head>
<%!String[] cntList, cityList, stateList;
	public String[] countrylist() {
		String[] country = new String[110];
		int i = 0, j = 0;
		RegistrationServlet rg = new RegistrationServlet();
		country = rg.listCountry();
		for (i = 1; ((i < country.length) && (country[i] != null)); i++)
			j++;
		String[] cntryList = new String[j];
		for (int k = 0; k < j; k++)
			cntryList[k] = country[k + 1];
		return cntryList;

	}
	public String[] citylist() {
		String[] cityList = null;
		int i = 0, j = 0;
		RegistrationServlet rg = new RegistrationServlet();
		cityList = rg.listCity("California");
		return cityList;
	}
	public String[] statelist() {
		String[] state = new String[110];
		int i = 0, j = 0;
		RegistrationServlet rg = new RegistrationServlet();
		state = rg.listState("United States");
		return stateList;

	}%>
<%
	String st = (String) request.getAttribute("result");
	if (st != null) {
		if (st.contains("Invalid")) {
%>
<script>
		alert("<%=(String) request.getAttribute("result")%>	");
</script>
<%
	}
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>My Video Library</title>
<link rel="stylesheet" href="../LibraryCSS/style.css" type="text/css" />
</head>
<script>
	function showDiv(value) {
		if (value == 'Premiuim User')
			document.getElementById("premium").checked = true;
		document.getElementById("payment").style.display = 'block';
	}
	function removeHide() {
		document.getElementById("premium").checked = false;
		document.getElementById("simple").checked = true;
		document.getElementById("admin").checked = false;
	}
	function hideDiv(value) {
		if (value == 'Administrator' || value == 'Simple User')
			document.getElementById("premium").checked = false;
		document.getElementById("payment").style.display = 'none';
	}
</script>
<body
	style="background: url(../LibraryIMG/background.jpg) repeat-x scroll left top transparent;"
	<%cntList = countrylist();
			cityList = citylist();
			stateList = statelist();%>
	onload="removeHide();">
	<div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="10%"></td>
				<td align="center" width="90%"><Font COLOR=WHITE
					FACE="WildWest" SIZE=10>My Video Library</Font></td>
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
	<div class="Details_Div_Registration"
		style="padding-left: 195px; padding-top: 5px;">
		<form id="form1" method="post" action="Registration">
			<div>
				<table>
					<tr>
						<td valign="bottom">Salutation&nbsp;*&nbsp;</td>
						<td><select class="idleField" name="salutation">
								<option value="--[Select One]--">--[Select One]--</option>
								<option value="Mr">Mr</option>
								<option value="Miss">Miss</option>
								<option value="Mrs">Mrs</option>
								<option value="Dr">Dr</option>
								<option value="Prof">Prof</option>
								<option value="Master">Master</option>

						</select></td>

						<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;First
							Name&nbsp;*&nbsp;</td>
						<td><input class="idleField" name="first_name" value=""
							type="text" /></td>
						<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Middle Name&nbsp;</td>
						<td><input class="idleField" name="middle_name" value=""
							type="text" /></td>
						<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Last
							Name&nbsp;*&nbsp;</td>
						<td><input class="idleField" name="last_name" value=""
							type="text" /></td>
					</tr>
					<tr>
						<td valign="bottom">Display Name&nbsp;</td>
						<td><input class="idleField" name="display_name" value=""
							type="text" /></td>
						<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Date of Birth
							&nbsp;*&nbsp;</td>
						<td><script type="text/javascript"
								src="http://www.snaphost.com/jquery/Calendar.aspx?dateFormat=dd/mm/yy"></script>
						</td>

						<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Sex&nbsp;*&nbsp;</td>
						<td valign="bottom"><input type="radio" name="sex"
							value="male">Male&nbsp;&nbsp;&nbsp;&nbsp; <input
							type="radio" name="sex" value="female">Female</td>
					</tr>
					<tr>
						<td valign="bottom">Address Line 1&nbsp;*&nbsp;</td>
						<td><input class="idleField" name="add_1" value=""
							type="text" /></td>
						<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Address Line
							2&nbsp;</td>
						<td><input class="idleField" name="add_2" value=""
							type="text" /></td>
						<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Address Line
							3&nbsp;</td>
						<td><input class="idleField" name="add_3" value=""
							type="text" /></td>
						<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;City&nbsp;*&nbsp;</td>
						<td><select class="idleField" name="city">
								<%
									for (int k = 0; k < cityList.length; k++) {
								%>
								<option value="<%=cityList[k]%>"><%=cityList[k]%></option>
								<%
									}
								%>
						</select></td>
					</tr>
					<tr>
						<td valign="bottom">State&nbsp;*&nbsp;</td>
						<td><select class="idleField" name="state">
								<option>California</option>
								<option>New York</option>
								<option>Texas</option>
								<option>Florida</option>
								
						</select></td>
						<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Country&nbsp;*&nbsp;</td>
						<td><select class="idleField" name="country">
								<option>United States</option>
								<%
									for (int k = 0; k < cntList.length; k++) {
								%>
								<option value="<%=cntList[k]%>"><%=cntList[k]%></option>
								<%
									}
								%>
						</select></td>
						<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Zip
							Code&nbsp;*&nbsp;</td>
						<td><input class="idleField" name="zipcode" value=""
							type="text" /></td>
						<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Contact Number</td>
						<td><input class="idleField" name="contact_num" value=""
							type="text" /></td>
					</tr>
					<tr>
						<td valign="bottom">Email ID&nbsp;*&nbsp;</td>
						<td><input class="idleField" name="email_id" value=""
							type="text" /></td>
						<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Login
							ID&nbsp;*&nbsp;</td>
						<td><input class="idleField" name="login_id" value=""
							type="text" /></td>
					</tr>
					<tr>
						<td valign="bottom">Password&nbsp;*&nbsp;</td>
						<td><input class="idleField" name="password" value=""
							type="password" /></td>
					</tr>
				</table>
				<div>
					<table>
						<tr>
							<td valign="bottom"><input id="simple" type="radio"
								name="userType" value="Simple User"
								onclick="hideDiv(this.checked)">Simple
								User&nbsp;&nbsp;&nbsp;&nbsp; <input id="premium" type="radio"
								name="userType" value="Premiuim User"
								onclick="showDiv(this.checked)">Premium
								User&nbsp;&nbsp;&nbsp;&nbsp; <input id="admin" type="radio"
								name="userType" value="Administrator"
								onclick="hideDiv(this.checked)">Administrator</td>

						</tr>
					</table>
				</div>
				<div>
					<table>
						<tr>
							<td><br>
								<div id="payment" style="display: none;">
									<table>
										<tr>
											<td valign="bottom">SSN Number :&nbsp;*&nbsp;</td>
											<td><input name="ssn1" value="" type="text" size="3"
												maxlength="3" />-</td>
											<td><input name="ssn2" value="" type="text" size="2"
												maxlength="2" />-</td>
											<td><input name="ssn3" value="" type="text" size="4"
												maxlength="4" /></td>
										</tr>
									</table>
									VideoPal :: Payment Gateway
									<table>
										<tr>
											<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Card Number
												:&nbsp;*&nbsp;</td>
											<td>&nbsp;<input name="card_one" value="" type="text"
												size="4" maxlength="4" />
											</td>
											<td><input name="card_two" value="" type="text" size="4"
												maxlength="4" /></td>
											<td>&nbsp;&nbsp;<input name="card_three" value=""
												type="text" size="4" maxlength="4" />
											</td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
												name="card_four" value="" type="text" size="4" maxlength="4" />
											</td>
										</tr>

										<tr>
											<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;CVV
												:&nbsp;*&nbsp;</td>
											<td>&nbsp;<input name="card_cvv" value=""
												type="password" size="3" maxlength="3" />
											</td>
										</tr>
										<tr>
											<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Valid till
												:&nbsp;*&nbsp;</td>
											<td>&nbsp;<select name="month">
													<option value="[Month]">[Month]</option>
													<option value="01">01</option>
													<option value="02">02</option>
													<option value="03">03</option>
													<option value="04">04</option>
													<option value="05">05</option>
													<option value="06">06</option>
													<option value="07">07</option>
													<option value="08">08</option>
													<option value="09">09</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
											</select>
											</td>
											<td>&nbsp;<select name="year">
													<option value="[Year]">[Year]</option>
													<option value="2013">2013</option>
													<option value="2014">2014</option>
													<option value="2015">2015</option>
													<option value="2016">2016</option>
													<option value="2017">2017</option>
													<option value="2018">2018</option>
													<option value="2019">2019</option>
													<option value="2020">2020</option>
													<option value="2021">2021</option>
													<option value="2022">2022</option>
													<option value="2023">2023</option>
													<option value="2024">2024</option>
											</select>
											</td>
										</tr>
									</table>
									<table>
										<tr>
											<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Name on card
												:&nbsp;*&nbsp;</td>
											<td><input name="card_name" value="" type="text"
												size="20" maxlength="20" /></td>
										</tr>
									</table>
								</div></td>
						</tr>
					</table>
				</div>
				<div style="padding-left: 550px;">
					<table>
						<tr>
							<td align="center"><input type="image"
								src="../LibraryIMG/button_submit_orange.jpg" alt="submit"
								title="submit" /></td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</div>
</body>


</html>
