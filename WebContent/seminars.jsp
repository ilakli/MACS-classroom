<%@page import="database.PersonDB"%>
<%@page import="defPackage.Person"%>
<%@page import="database.AllConnections"%>
<%@page import="defPackage.Classroom"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Seminars</title>


<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.css">
<script
	src="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.js"></script>
	
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<style>
.ui.menu {
	margin-top: 0;
}
</style>
</head>
<body>
	


	<%
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomID);
		PersonDB personConnector = connector.personDB;

		Person currentPerson = (Person) request.getSession().getAttribute("currentPerson");
		boolean isAdmin = connector.personDB.isAdmin(currentPerson);
		boolean isStudent = currentClassroom.classroomStudentExists(currentPerson.getEmail());
		boolean isSectionLeader = currentClassroom.classroomSectionLeaderExists(currentPerson.getEmail());
		boolean isSeminarist = currentClassroom.classroomSeminaristExists(currentPerson.getEmail());
		boolean isLecturer = currentClassroom.classroomLecturerExists(currentPerson.getEmail());
	%>
	<a href="index.jsp">
		<h3 class="ui block header">Macs Classroom</h3>
	</a>
	<div class="ui menu">
		<a
			href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>
			class="header item"> <%=currentClassroom.getClassroomName()%>
		</a> <a class="item"
			href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Stream</a>

		<%
			if (isAdmin || isLecturer || isSeminarist || isSectionLeader) {
		%>
		<a class="item"
			href=<%="viewSectionsAndSeminars.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
			Sections And Seminars</a>
		<%
			}
		%>

		<%
			if (isAdmin || isLecturer) {
		%>
		<a class="item"
			href=<%="edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Edit</a>
		<%
			}
		%>

		<a class="item"
			href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>About</a>

		<a class="item"
			href=<%="assignments.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Assignments</a>

		<%
			if (isAdmin || isLecturer) {
		%>
		<a class="item"
			href=<%="settings.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Settings</a>

		<a class="item"
			href=<%="editSectionsAndSeminars.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
			Edit Sections And Seminars</a>
		<%
			}
		%>
		<div class="right menu">
			<a class="item"
				href=<%="people.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
				People </a>
			<div class="ui dropdown item">
				Groups <i class="dropdown icon"></i>
				<div class="menu">
					<a href=<%="sections.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%> class="item"> Sections </a> 
					<a href=<%="seminars.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%> class="active item"> Seminars </a>
				</div>
			</div>
		</div>
	</div>
	<script>
		$('.ui.dropdown').dropdown();
	</script>
</body>
</html>