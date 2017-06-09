<%@page import="defPackage.Classroom"%>
<%@page import="defPackage.DBConnection"%>
<%@page import="defPackage.Person"%>
<%@page import="defPackage.Seminar"%>
<%@page import="defPackage.Section"%>
<%@page import="defPackage.ActiveSeminar"%>
<%@page import="java.util.List"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="style.css">
<title>Formation</title>
</head>
<body>
	<%
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		DBConnection connector = (DBConnection) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.getClassroom(classroomId);
		
		List <Person> lecturers = connector.getLecturers(classroomId);
		List <Person> seminarists = connector.getSeminarists(classroomId);
		List <Person> sectionLeaders = connector.getSectionLeaders(classroomId);
		List <Person> students = connector.getStudents(classroomId);
		System.out.println("roles downloaded successfully!");
		
		List <Seminar> seminars = connector.getSeminars(classroomId);
		List <ActiveSeminar> activeSeminars = connector.getActiveSeminars(classroomId);
		List <Section> sections = connector.getSections(classroomId);
		System.out.println("seminars, active seminars and sections downloaded successfully!");
		
	%>
	
	<div class="jumbotron">
		<h2>Macs Classroom</h2>
	</div>
	<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#"><%= currentClassroom.getClassroomName() %></a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href=<%= "stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>>Stream</a></li>
					<li><a href=<%= "about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>  >About</a></li>
					<li class="active"><a href=<%= "formation.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>>Formation</a></li>
					<li><a href=<%= "edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>>Edit</a></li>
				</ul>
			</div>
	</nav>
	
	<h2>Lecturers:</h2>
		<ul>
		<%
			for(Person lecturer : lecturers){
				out.println("<il>" + lecturer.getEmail() + "</il>");
			}
			
		%>
		</ul>
	
	<h2>Seminarists:</h2>
		<ul>
		<%
			for(Person seminarist : seminarists){
				out.println("<il>" + seminarist.getEmail() + "</il>");
			}
		%>
		</ul>
	
	<h2>Section Leaders:</h2>
		<ul>
		<%
			for(Person sectionLeader : sectionLeaders){
				out.println("<il>" + sectionLeader.getEmail() + "</il>");
			}
		%>
		</ul>
	
	<h2>Students:</h2>
		<ul>
		<%
			for(Person student : students){
				out.println("<il>" + student.getEmail() + "</il>");
			}
		%>
		</ul>
	
	<h2>Seminars:</h2>
		<ul>
		<%
			for(Seminar seminar : seminars){
				out.println("il" + seminar.getSeminarName() + "</il>");
			}
		%>
		</ul>
	
	<h2>Active Seminars:</h2>
		<ul>
		<%
			for(ActiveSeminar activeSeminar : activeSeminars){
				out.println("il" + activeSeminar.getActiveSeminarName() + "</il>");
			}
		%>
		</ul>
	
	<h2>Sections:</h2>
		<ul>
		<%
			for(Section section : sections){
				out.println("il" + section.getSectionName() + "</il>");
			}
		%>
		</ul>
	
	
	
</body>
</html>