<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="Common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>
<br/><br/><br/><br/><br/><br/>
<div align="center">
	<h2>
		<font color="ffffff" face="WildWest" size=8>
			Movie successfully added to your Cart!</font>
	</h2>
	<form id="AddAnotherItemForm" method="post" action="MovieDetails"><br/>
		<div class="form-row"><input class="submit" type="submit" value="Add another Movie!"/></div>
	</form>
	<form id="ProceedToCheckoutForm" method="post" action="MyCart"><br/>
		<div class="form-row"><input class="submit" type="submit" value="View Cart"/></div>
	</form>
	</div>
</body>
</html>