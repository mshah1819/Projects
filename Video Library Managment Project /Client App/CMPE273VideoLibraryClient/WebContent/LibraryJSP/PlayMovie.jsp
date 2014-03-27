<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="Common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		int PlayMovieId = (Integer)request.getAttribute("playMovieId");
	%>
	<div align="center"
		style="padding-left: -50px; padding-top: 30px;">
	
		<video width="600" height="400" controls="controls"> <source
			src="..\LibraryMOVIE\<%=PlayMovieId %>.ogg" type="video/ogg"></source> </video>
	</div>
</body>
</html>