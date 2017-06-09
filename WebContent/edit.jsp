<%@page import="defPackage.Classroom"%>
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
<title>Edit</title>
</head>
<body>
<%
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		DBConnection connector = (DBConnection) request.getServletContext().getAttribute("connection");
		Classroom currentclassroom = connector.getclassroom(classroomId);
	
		
	%>
	
	<div class="jumbotron">
		<h2>Macs Classroom</h2>
	</div>
	<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<%-- getclassroom to be implemented --%>
					<a class="navbar-brand" href="#">Plain Text</a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href=<%= "stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>>Stream</a></li>
					<li><a href=<%= "about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>  >About</a></li>
					<li><a href=<%= "formation.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>>Formation</a></li>
					<li class="active"><a href=<%= "edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>>Edit</a></li>
				</ul>
			</div>
	</nav>
</body>
</html>