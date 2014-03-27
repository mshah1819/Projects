<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="Common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>

	<br />
	<br />
	<center>
		<h3>VideoPal :: Payment Gateway</h3>
	</center>
	<br />
	<div class="Details_Div_Registration"
		style="padding-left: 480px; padding-top: 5px;">
	
		<form id="AddBalanceForm" method="post" action="AddBalance" onSubmit="return validateForm()">
			<table>
				<tr>
					<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Card Number
						:&nbsp;*&nbsp;</td>
					<td>&nbsp;<input name="card_one" value="" type="text" size="4"
						maxlength="4" />
					</td>
					<td><input name="card_two" value="" type="text" size="4"
						maxlength="4" /></td>
					<td>&nbsp;&nbsp;<input name="card_three" value="" type="text"
						size="4" maxlength="4" />
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="card_four"
						value="" type="text" size="4" maxlength="4" />
					</td>
				</tr>

				<tr>
					<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;CVV :&nbsp;*&nbsp;</td>
					<td>&nbsp;<input name="card_cvv" value="" type="password"
						size="3" maxlength="3" />
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
					<td><input name="card_name" value="" type="text" size="20"
						maxlength="20" /></td>
				</tr>
			</table>

			<table>
				<tr>
					<td valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;Amount to add
						:&nbsp;*&nbsp;</td>
					<td><input type="text" name="amountToAdd" size="5"
						maxlength="5" /></td>
				</tr>
			</table>

			<br />
			<div style="padding-left: 130px">

				<table>
					<tr>
						<td align="center"><input type="submit" name="AddBalance"
							value="Add Balance" src="../LibraryIMG/button_submit_orange.jpg"
							title="submit" /></td>
					</tr>
				</table>
				<div style="padding-left: -100px">
				<%
					if (request.getAttribute("errorMsg") != null
												&& request.getAttribute("errorMsg") != "") {
						out.println(request.getAttribute("errorMsg"));
					}
					%>
				</div>
		</form>
	</div>
	</div>
</body>
<script type="text/javascript">
		function validateForm() {
			var x = document.forms["AddBalanceForm"]["amountToAdd"].value;
			if (x == null || x == "") {
				alert("Enter a valid email address");
				return false;
			}
		};
	</script>
</html>