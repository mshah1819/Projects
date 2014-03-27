<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="model.BillingHistory"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Video Library</title>
<link rel="stylesheet" type="text/css" href="../LibraryCSS/style.css" />
<%@include file="Common.jsp"%>
</head>
<%
	BillingHistory[] fetchedBillingHistory = (BillingHistory[]) request
			.getAttribute("fetchedBillingHistory");
%>
<script>
	function gen() {
<%session.setAttribute("fetchedBillingHistory", fetchedBillingHistory);%>
	}
</script>
<body>
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
	<form id="GeneratePDFForm" method="post" action="PDFServlet">
		<div class="form-row">
				<nav>
				<ul class="fancyNav">
					<li><a href="javascript:;"
						onclick="javascript: document.getElementById('GeneratePDFForm').submit();">Generate PDF</a>
					</li>
				</ul>
				</nav>
			</div>
	</form>
	<table width="80%" style="margin-left: 150px; margin-top: 50px;">
		<thead>
			<tr
				style="border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7; background: #fff; padding: 6px 6px 6px 12px; color: #4f6b72;">
				<th colspan="7" style="text-align: center;" scope="row">Billing
					History</th>
			</tr>
			<tr>
				<th scope=col>Movie Name</th>
				<th>Transaction Date</th>
				<th>Transaction Type</th>
				<th>Transaction Amount</th>
			</tr>
		</thead>
		<tbody>
			<%
				//BillingHistory[] fetchedBillingHistory = (BillingHistory[]) request
				//.getAttribute("fetchedBillingHistory");
				int i = 0;
				if (fetchedBillingHistory != null) {
					//System.out.println(fetchedMovieDetails[0].getMovieName());
					for (i = 0; i < fetchedBillingHistory.length; i++) {
						// This IF loop will exclude all those movie records which have 0 available copies
							String TransactionDate = fetchedBillingHistory[i]
									.getMovieAddedOn().subSequence(0, 10)
									.toString();
			%>
			<tr
				style="border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7; background: #fff; padding: 6px 6px 6px 12px; color: #4f6b72;">
				<td style="text-align: center;" scope="row"><%=fetchedBillingHistory[i].getMovieName()%></td>
				<td style="text-align: center;" scope="row"><%=fetchedBillingHistory[i].getMovieAddedOn()%></td>
				<td style="text-align: center;"><%=fetchedBillingHistory[i].getMovieState()%></td>
				<td style="text-align: center;"><%=fetchedBillingHistory[i].getMovieAmountPaid()%></td>
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
</body>
</html>