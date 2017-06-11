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
	
	<link rel="stylesheet" href="css/style.css">
	
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
					<li class="active"><a href=<%= "formation.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>>Formation</a></li>
					<li><a href=<%= "edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>>Edit</a></li>
				</ul>
			</div>
	</nav>
	
	
	<div class='person-groups'>
					
			
			<!-- LECTURERS -->
			<div class='group-lecturers'>
		    	<div class='display-persons-button'>Lecturers</div>
		        <ul class='group-persons'>
		          <%
		          	if (lecturers.isEmpty()){
		          		out.println("<li>" + "Empty" + "</li>");
		          	} else {
			          	for(Person lecturer : lecturers){
							out.println("<li>" + lecturer.getEmail() + "</li>");
						}
		          	}
		          %>
		        </ul>
		    </div>
		    
		    <!-- SEMINARISTS -->
			<div class='group-seminarists'>
		    	<div class='display-persons-button'>Seminarists</div>
		        <ul class='group-persons'>
		          <%
		          	if (seminarists.isEmpty()){
		          		out.println("<li>" + "Empty" + "</li>");
		          	} else {
				  		for(Person seminarist : seminarists){
							out.println("<li>" + seminarist.getEmail() + "</li>");
						}
		          	}
			      %>
		        </ul>
		    </div>
		    
		    <!-- SECTION LEADERS -->
			<div class='group-section-leaders'>
		    	<div class='display-persons-button'>Section Leaders</div>
		        <ul class='group-persons'>
		          <%
		          	if (sectionLeaders.isEmpty()){
		          		out.println("<li>" + "Empty" + "</li>");
		          	} else {
						for(Person sectionLeader : sectionLeaders){
							out.println("<li>" + sectionLeader.getEmail() + "</li>");
						}
		          	}
		          %>
		        </ul>
		    </div>
		    
		    <!-- STUDENTS -->
			<div class='group-students'>
		    	<div class='display-persons-button'>Students</div>
		        <ul class='group-persons'>
		          <%
		          	if (students.isEmpty()){
		          		out.println("<li>" + "Empty" + "</li>");
		          	} else {
						for(Person student : students){
							out.println("<li>" + student.getEmail() + "</li>");
						}
		          	}
				  %>
		        </ul>
		    </div>
    
    
    </div>
	
	
	<div class='grenevt-groups'>
		
		<!-- SEMINARS -->
		<div class='grevent-seminars'>
			<div class='display-grevent-button'>Seminars</div>
			<ul class='grevent-items'>
			<%
				if (seminars.isEmpty()){
					out.println("<li>" + "Empty" + "</li>");
				} else {
					for(Seminar seminar : seminars){
						out.println("<li>" + seminar.getSeminarName() + "<li>");
					}
				}
			%>
			</ul>
		</div>


		<!-- SECTIONS -->
		<div class='grevent-sections'>
			<div class='display-grevent-button'>Sections</div>
			<ul class='grevent-items'>
			<%	
				if (sections.isEmpty()){
					out.println("<li>" + "Empty" + "</li>");
				} else {
					for(Section section : sections){
						out.println("<li>" + section.getSectionName() +  "<li>");
					}
				}
			%>
			</ul>
		</div>


		<!-- ACTIVE SEMINARS -->
		<div class='grevent-active-seminars'>
			<div class='display-grevent-button'>Active Seminars</div>
			<ul class='grevent-items'>
			<%	
				if (activeSeminars.isEmpty()){
					out.println("<li>" + "Empty" + "</li>");
				} else {
					for(ActiveSeminar activeSeminar : activeSeminars){
						out.println("<li>" + activeSeminar.getActiveSeminarName() + ".  " + 
									" Location: " + activeSeminar.getActiveSeminarLocation()  + ".  "+ 
									" Time: " + activeSeminar.getActiveSeminarTime() + "<li>");
					}
				}
			%>
			</ul>
		</div>	

	
	
	
	
	</div>
	
	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	<script type="text/javascript" src='js/formationJS.js'></script>
</body>
</html>