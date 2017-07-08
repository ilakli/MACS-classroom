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

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.css">
<script
	src="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.js"></script>
	
	
	
<script type="text/javascript" src='js/multiInput.js'></script>
<script type="text/javascript" src='js/studentAdd.js'></script>
<script type="text/javascript" src='js/sectionLeaderAdd.js'></script>
<script type="text/javascript" src='js/seminaristAdd.js'></script>
<script type="text/javascript" src='js/classroomLecturerAdd.js'></script>

<link rel="stylesheet" href="css/multiInput.css" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<style>
.list-wrapper {
	float: left;
	display: inline-block;
	margin-right: 0.5%;
	margin-left: 0.5%;
	width: 24%;
}

.checkbox {
	margin-top: 35%;
}

.icon {
	display: inline-block;
	float: right;
	cursor: pointer;
	position: relative;
	padding-top: 1.9%;
	padding-right: 8%;
}

.head-wrapper {
	text-align: center;
}

.head-wrapper h2 {
	display: inline-block;
	height: 25px;
}

.block.header {
	margin: 0;
}

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
			<a class="active item"
				href=<%="people.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
				People </a>
		</div>
	</div>

	<input type="hidden" id="classroomId" value=<%=classroomID%>>
	

	<div class="ui modal student">
		<div class="header">Add Student</div>
		<div class="content">
			<div class="field">
				<div class="emails">
					<input type="text" value="" placeholder="Add Email" /> <input
						type="hidden" id="studentServlet" value="AddNewStudentServlet">
				</div>
			</div>
		</div>
		<div class="actions">
			<button class="ui teal button personAddButton">Add</button>
			<button class="ui red button cancel">Cancel</button>
		</div>
	</div>

	<div class="ui modal section-leader">
		<div class="header">Add Section Leader</div>
		<div class="content">
			<div class="field">
				<div class="emails">

					<input type="text" value="" placeholder="Add Email" /> <input
						type="hidden" id="sectionLeaderAddServlet"
						value="AddNewSectionLeaderServlet">
				</div>
			</div>
		</div>
		<div class="actions">
			<button class="ui teal button sectionLeaderAddButton">Add</button>
			<button class="ui red button cancel">Cancel</button>
		</div>
	</div>

	<div class="ui modal seminarist">
		<div class="header">Add Seminarist</div>
		<div class="content">
			<div class="field">
				<div class="emails">

					<input type="text" value="" placeholder="Add Email" /> <input
						type="hidden" id="seminaristAddServlet"
						value="AddNewSeminaristServlet">
				</div>
			</div>
		</div>
		<div class="actions">
			<button class="ui teal button seminaristAddButton">Add</button>
			<button class="ui red button cancel">Cancel</button>
		</div>
	</div>

	<div class="ui modal lecturer">
		<div class="header">Add Lecturer</div>
		<div class="content">
			<div class="field">
				<div class="emails">

					<input type="text" value="" placeholder="Add Email" /> <input
						type="hidden" id="lecturerAddServlet"
						value="AddNewLecturerServlet">
				</div>
			</div>
		</div>
		<div class="actions">
			<button class="ui teal button lecturerAddButton">Add</button>
			<button class="ui red button cancel">Cancel</button>
		</div>
	</div>



	<div class="list-wrapper">

		<div class="head-wrapper">

			<h2 class="ui header">Students</h2>

			<i class="add user icon icon-student"></i>
			<script>
				$(".icon-student").click(function() {
					$('.ui.modal.student').modal('show');
				});
			</script>
		</div>
		<form method="POST" action = "DeletePersonServlet">
		<div class="ui celled list">
			<%
				ArrayList<Person> students = connector.studentDB
						.getStudents(request.getParameter(Classroom.ID_ATTRIBUTE_NAME));
				for (Person currentStudent : students) {
			%>
			<div class="item">
				<div class="right floated content">
					<div class="ui checkbox">
						<input type="checkbox" name ="<%=currentStudent.getEmail() %>" value="<%=currentStudent.getEmail() %>"> 
						<label></label>
					</div>
				</div>
				<img class="ui avatar image"
					src="<%=currentStudent.getPersonImgUrl()%>">
				<div class="content">
					<div class="header"><%=currentStudent.getName() + " " + currentStudent.getSurname()%></div>
					<%=currentStudent.getEmail()%>
				</div>
			</div>
			<%
				}
			%>
		</div>
		<input type="hidden" name= "<%= Classroom.ID_ATTRIBUTE_NAME %>" value="<%= currentClassroom.getClassroomID()%>" >
		<button type="submit" class="ui red button">Remove Marked</button>
		</form>
	</div>

	<div class="list-wrapper">
		<div class="head-wrapper">

			<h2 class="ui header">Section Leaders</h2>

			<i class="add user icon icon-section-leader"></i>
			<script>
				$(".icon-section-leader").click(function() {
					$('.ui.modal.section-leader').modal('show');
				});
			</script>
		</div>
		<form method="POST" action="DeletePersonServlet" >
		<div class="ui celled list">
			<%
				ArrayList<Person> sectionLeaders = connector.sectionLeaderDB
						.getSectionLeaders(request.getParameter(Classroom.ID_ATTRIBUTE_NAME));
				for (Person currentSectionLeader : sectionLeaders) {
			%>
			<div class="item">
				<div class="right floated content">
					<div class="ui checkbox">
						<input type="checkbox" name = "<%=currentSectionLeader.getEmail() %>" value="<%=currentSectionLeader.getEmail() %>"> 
						<label></label>
					</div>
				</div>
				<img class="ui avatar image"
					src="<%=currentSectionLeader.getPersonImgUrl()%>">
				<div class="content">
					<div class="header"><%=currentSectionLeader.getName() + " " + currentSectionLeader.getSurname()%></div>
					<%=currentSectionLeader.getEmail()%>
				</div>
			</div>
			<%
				}
			%>
		</div>
		<input type="hidden" name= "<%= Classroom.ID_ATTRIBUTE_NAME %>" value="<%= currentClassroom.getClassroomID()%>" >
		<button type="submit" class="ui red button">Remove Marked</button>
		</form>
	</div>

	<div class="list-wrapper">
		<div class="head-wrapper">

			<h2 class="ui header">Seminarists</h2>

			<i class="add user icon icon-seminarist"></i>
			<script>
				$(".icon-seminarist").click(function() {
					$('.ui.modal.seminarist').modal('show');
				});
			</script>
		</div>
		<form method="POST" action="DeletePersonServlet">
		<div class="ui celled list">
			<%
				ArrayList<Person> seminarists = connector.seminaristDB
						.getSeminarists(request.getParameter(Classroom.ID_ATTRIBUTE_NAME));
				for (Person currentSeminarist : seminarists) {
			%>
			<div class="item">
			<div class="right floated content">
					<div class="ui checkbox">
						<input type="checkbox" name="<%=currentSeminarist.getEmail() %>" value="<%=currentSeminarist.getEmail() %>"> 
						<label></label>
					</div>
				</div>
				<img class="ui avatar image"
					src="<%=currentSeminarist.getPersonImgUrl()%>">
				<div class="content">
					<div class="header"><%=currentSeminarist.getName() + " " + currentSeminarist.getSurname()%></div>
					<%=currentSeminarist.getEmail()%>
				</div>
			</div>
			<%
				}
			%>
		</div>
		<input type="hidden" name= "<%= Classroom.ID_ATTRIBUTE_NAME %>" value="<%= currentClassroom.getClassroomID()%>" >
		<button type="submit" class="ui red button">Remove Marked</button>
		</form>
	</div>

	<div class="list-wrapper">
		<div class="head-wrapper">

			<h2 class="ui header">Lecturers</h2>

			<i class="add user icon icon-lecturer"></i>
			<script>
				$(".icon-lecturer").click(function() {
					$('.ui.modal.lecturer').modal('show');
				});
			</script>
		</div>
		<form method="POST" action="DeletePersonServlet">
		<div class="ui celled list">
			<%
				ArrayList<Person> lecturers = connector.lecturerDB
						.getLecturers(request.getParameter(Classroom.ID_ATTRIBUTE_NAME));
				for (Person currentLecturer : lecturers) {
			%>
			<div class="item">
			<div class="right floated content">
					<div class="ui checkbox">
						<input type="checkbox" name="<%=currentLecturer.getEmail() %>" value="<%=currentLecturer.getEmail() %>"> 
						<label></label>
					</div>
				</div>
				<img class="ui avatar image"
					src="<%=currentLecturer.getPersonImgUrl()%>">
				<div class="content">
					<div class="header"><%=currentLecturer.getName() + " " + currentLecturer.getSurname()%></div>
					<%=currentLecturer.getEmail()%>
				</div>
			</div>
			<%
				}
			%>
		</div>
		<input type="hidden" name= "<%= Classroom.ID_ATTRIBUTE_NAME %>" value="<%= currentClassroom.getClassroomID()%>" >
		<button type="submit" class="ui red button">Remove Marked</button>
		</form>
	</div>
	<script>
		$(".cancel").click(function(){
			$(this).parent().parent().find("span").remove();
		})
	</script>
</body>
</html>