<%@page import="defPackage.Course"%>
<%@page import="defPackage.DBConnection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="style.css">
<title>About</title>
</head>
<body>
	<%
		String courseId = request.getParameter(Course.ID_ATTRIBUTE_NAME);
		DBConnection connector = (DBConnection) request.getServletContext().getAttribute("connection");
		Course currentCourse = connector.getCourse(courseId);
	
		
	%>
	
	<div class="jumbotron">
		<h2>Macs Classroom</h2>
	</div>
	<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<%-- getCourse to be implemented --%>
					<a class="navbar-brand" href="#">Plain Text</a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href=<%= "stream.jsp?" + Course.ID_ATTRIBUTE_NAME + "=" + courseId %>>Stream</a></li>
					<li class="active"><a href=<%= "about.jsp?" + Course.ID_ATTRIBUTE_NAME + "=" + courseId %>  >About</a></li>
					<li><a href=<%= "formation.jsp?" + Course.ID_ATTRIBUTE_NAME + "=" + courseId %>>Formation</a></li>
					<li><a href=<%= "edit.jsp?" + Course.ID_ATTRIBUTE_NAME + "=" + courseId %>>Edit</a></li>
				</ul>
			</div>
	</nav>
</body>
</html>