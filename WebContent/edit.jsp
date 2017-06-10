<%@page import="EditingServlets.EditStatusConstants"%>
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
<link rel="stylesheet" href="css/style.css">
<title>Edit</title>
</head>
<body>
	<%
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		DBConnection connector = (DBConnection) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.getClassroom(classroomId);
	
		String status  = request.getParameter(EditStatusConstants.STATUS);
		
	%>
	
	<div class="jumbotron">
		<h2><a href="index.jsp" id="header-name">Macs Classroom</a></h2>
	</div>
	<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#"><%= currentClassroom.getClassroomName() %></a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href=<%= "stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>>Stream</a></li>
					<li><a href=<%= "about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>  >About</a></li>
					<li><a href=<%= "formation.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>>Formation</a></li>
					<li class="active"><a href=<%= "edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>>Edit</a></li>
				</ul>
			</div>
	</nav>
	
	
	
	<!-- adds new lecturer to the class -->	
	<form action=<%="AddNewLecturerServlet?" 
	+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>  method="post">
		<h4>Add New Lecturer:</h4>
		<p>Enter Email address, name and surname of the lecturer</p>
		
		
		<input type="text" name="email">
		<input type="text" name="name">
		<input type="text" name="surname">
		
		<input type="submit" value ="Add">
		
		<%	
			if(status != null){
				if(status.equals( EditStatusConstants.ADD_NEW_LECTURER_ACC)) out.println(EditStatusConstants.ACCEPT);
				if(status.equals( EditStatusConstants.ADD_NEW_LECTURER_REJ)) out.println(EditStatusConstants.REJECT);
			}
		%>
		
	</form><br>
	
	<!-- adds new seminarist to the class -->	
	<form action=<%="AddNewSeminaristServlet?"
	+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>  method="post">
		<h4>Add New Seminarist:</h4>
		<p>Enter Email address, name and surname of the seminarist</p>
		
		
		<input type="text" name="email">
		<input type="text" name="name">
		<input type="text" name="surname">
		
		<input type="submit" value ="Add">
		
		<%
			if(status != null){
				if(status.equals( EditStatusConstants.ADD_NEW_SEMINARIST_ACC)) out.println(EditStatusConstants.ACCEPT);
				if(status.equals( EditStatusConstants.ADD_NEW_SEMINARIST_REJ)) out.println(EditStatusConstants.REJECT);	
			}
		%>
	</form><br>
	
	
	<!-- adds new section leader to the class -->	
	<form action=<%="AddNewSectionLeaderServlet?" 
	+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>  method="post">
		<h4>Add New Section Leader:</h4>
		<p>Enter Email address, name and surname of the section leader</p>
		
		
		<input type="text" name="email">
		<input type="text" name="name">
		<input type="text" name="surname">
		
		<input type="submit" value ="Add">
		
		<%
			if(status != null){
				if(status.equals( EditStatusConstants.ADD_NEW_SECTION_LEADER_ACC)) out.println(EditStatusConstants.ACCEPT);
				if(status.equals( EditStatusConstants.ADD_NEW_SECTION_LEADER_REJ)) out.println(EditStatusConstants.REJECT);
			}
		%>
		
	</form><br>
	
	
	<!-- adds new student to the class -->
	<form action= <%="AddNewStudentServlet?"
	+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>  method="post">
		<h4>Add New Student:</h4>
		<p>Enter Email address, name and surname of the student</p>
		
		
		<input type="text" name="email">
		<input type="text" name="name">
		<input type="text" name="surname">
		
		<input type="submit" value ="Add">
		
		<%
			if(status != null){
				if(status.equals( EditStatusConstants.ADD_NEW_STUDENT_ACC)) out.println(EditStatusConstants.ACCEPT);
				if(status.equals( EditStatusConstants.ADD_NEW_STUDENT_REJ)) out.println(EditStatusConstants.REJECT);
			}
		%>
		
	</form><br>
	
	
	<form action="EditServlet" method="post">
		<h4>Delete person by Email:</h4>
		<p>Enter Email </p>
		
		
		<input type="text" name="email">
		
		
		<input type="submit" value ="Delete">
		
	</form><br>
	
	
	<!-- adds existing student to section -->
	<form action="EditServlet" method="post">
		<h4>Add student to section:</h4>
		<p>enter Email address of the student</p>
		<input type="text" name="email">
		
		<input type="submit" value ="Add">
		
	</form><br>

	
	<!-- adds existing student to seminar -->
	<form action="EditServlet" method="post">
		<h4>Add student to seminar:</h4>
		<p>enter Email address of the student</p>
		<input type="text" name="email">
		
		<input type="submit" value ="Add">
		
	</form><br>	
	
	
	
	<form action="EditServlet" method="post">
		<h4>Edit Groups:</h4>
		<p>Choose wanted options and enter name(s) of the group(s)</p>
		
		<select>
			<option value = "add">Add</option>
			<option value = "delete">Delete</option>
		</select>
		
		<select>
			<option value = "seminar">Seminar</option>
			<option value = "section">Section</option>
		</select>
		
		<input type="text" name="groupName">
		
		<input type="submit" value ="Submit">
	</form><br>
	
	
	<form action="EditServlet" method="post">
		<h4>Edit Classes:</h4>
		<p>Choose wanted options and enter name(s), location(s) and time(s) of the class(es)</p>
		
		<select>
			<option value = "add">Add</option>
			<option value = "delete">Delete</option>
		</select>
		
		<select>
			<option value = "seminar">Seminar</option>
			<option value = "lecture">Section</option>
		</select>
		
		
		<input type="text" name="className">
		
		<input type="text" name="classLocation">
		
		<input type="text" name="classTime">
		
		<input type="submit" value ="Submit">
	</form><br>
	
	
	
	<form action="EditServlet" method="post">
		<h4>Edit Student-Group Relations:</h4>
		<p>Choose wanted options and enter name(s) of the person(s) and name of the group</p>
		
		<select>
			<option value = "add">Add</option>
			<option value = "delete">Delete</option>
		</select>
		
		<select>
			<option value = "seminar">Seminar</option>
			<option value = "section">Section</option>
		</select>
		
		
		<input type="text" name="personName">
		
		<input type="text" name="groupName">
		
		<input type="submit" value ="Submit">
	</form>
		

	
	
	
	
	
</body>
</html>