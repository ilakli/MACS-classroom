<%@page import="java.util.ArrayList"%>
<%@page import="defPackage.Person"%>
<%@page import="database.PersonDB"%>
<%@page import="database.AllConnections"%>
<%@page import="defPackage.Classroom"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>People</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/1.11.8/semantic.min.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/1.11.8/semantic.min.js"></script>
    <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/style.css">
<style>
.list-wrapper {
	float: left;
	display: inline-block;
	margin-right: 1%;
	margin-left: 1%;
	width: 23%;
}

.checkbox {
	margin-top: 35%;
}

h2 {
	text-align: center;
}
</style>
</head>
<body>




<%
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomID);
		PersonDB personConnector = connector.personDB;
		
		Person currentPerson = (Person)request.getSession().getAttribute("currentPerson");
		boolean isAdmin = connector.personDB.isAdmin(currentPerson);
		boolean isStudent = currentClassroom.classroomStudentExists(currentPerson.getEmail());
		boolean isSectionLeader = currentClassroom.classroomSectionLeaderExists(currentPerson.getEmail());
		boolean isSeminarist = currentClassroom.classroomSeminaristExists(currentPerson.getEmail());
		boolean isLecturer = currentClassroom.classroomLecturerExists(currentPerson.getEmail());
		
	%>

	<div class="jumbotron">
		<h2>
			<a href="index.jsp" id="header-name">Macs Classroom</a>
		</h2>
	</div>
	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#"><%=currentClassroom.getClassroomName()%></a>
		</div>
		<ul class="nav navbar-nav">
			<li><a
				href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Stream</a></li>
			
			<%if (isAdmin || isLecturer || isSeminarist || isSectionLeader){%>
			<li><a
				href=<%="viewSectionsAndSeminars.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
				Sections And Seminars</a></li>
			<%}%>
			
			<%if (isAdmin || isLecturer){%>
			<li><a
				href=<%="edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Edit</a></li>
			<%}%>
			
			<li><a
				href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>About</a></li>	
			
			<li><a
				href=<%="assignments.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Assignments</a></li>
			
			<%if (isAdmin || isLecturer){%>
			<li><a
				href=<%="settings.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Settings</a></li>
			
			<li><a
				href=<%="editSectionsAndSeminars.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
				Edit Sections And Seminars</a></li>
			<%}%>
			<li class="active">
			<a
			href=<%="people.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
				People
			</a>
			</li>
			<li>
			<a
			href=<%="people.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
				People
			</a>
			</li>
		</ul>
	</div>
	</nav>
	
	

<div class="list-wrapper">
<h2 class="ui header">Students</h2>
<div class="ui celled list">
	<%
		ArrayList<Person> students = connector.studentDB.getStudents(request.getParameter(Classroom.ID_ATTRIBUTE_NAME));
		for(Person currentStudent : students){
	%>
  <div class="item">
    <img class="ui avatar image" src="<%= currentStudent.getPersonImgUrl()%>">
    <div class="content">
      <div class="header"><%= currentStudent.getName() + " " + currentStudent.getSurname() %></div>
      <%= currentStudent.getEmail() %>
    </div>
  </div>
	<%
		}	
	%>
</div>
</div>

<div class="list-wrapper">
<h2 class="ui header">Section Leaders</h2>
<div class="ui celled list">
	<%
		ArrayList<Person> sectionLeaders = connector.sectionLeaderDB.getSectionLeaders(request.getParameter(Classroom.ID_ATTRIBUTE_NAME));
		for(Person currentSectionLeader : sectionLeaders){
	%>
  <div class="item">
    <img class="ui avatar image" src="<%= currentSectionLeader.getPersonImgUrl()%>">
    <div class="content">
      <div class="header"><%= currentSectionLeader.getName() + " " + currentSectionLeader.getSurname() %></div>
      <%= currentSectionLeader.getEmail() %>
    </div>
  </div>
	<%
		}	
	%>
</div>
</div>

<div class="list-wrapper">
<h2 class="ui header">Seminarists</h2>
<div class="ui celled list">
	<%
		ArrayList<Person> seminarists = connector.seminaristDB.getSeminarists(request.getParameter(Classroom.ID_ATTRIBUTE_NAME));
		for(Person currentSeminarist : seminarists){
	%>
  <div class="item">
    <img class="ui avatar image" src="<%= currentSeminarist.getPersonImgUrl()%>">
    <div class="content">
      <div class="header"><%= currentSeminarist.getName() + " " + currentSeminarist.getSurname() %></div>
      <%= currentSeminarist.getEmail() %>
    </div>
  </div>
	<%
		}	
	%>
</div>
</div>

<div class="list-wrapper">
<h2 class="ui header">Lecturers</h2>
<div class="ui celled list">
	<%
		ArrayList<Person> lecturers = connector.lecturerDB.getLecturers(request.getParameter(Classroom.ID_ATTRIBUTE_NAME));
		for(Person currentLecturer : lecturers){
	%>
  <div class="item">
    <img class="ui avatar image" src="<%= currentLecturer.getPersonImgUrl()%>">
    <div class="content">
      <div class="header"><%= currentLecturer.getName() + " " + currentLecturer.getSurname() %></div>
      <%= currentLecturer.getEmail() %>
    </div>
  </div>
	<%
		}	
	%>
</div>
</div>
</body>
</html>