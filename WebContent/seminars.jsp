<%@page import="java.util.List"%>
<%@page import="defPackage.Seminar"%>
<%@page import="defPackage.Section"%>
<%@page import="defPackage.Person"%>
<%@page import="database.PersonDB"%>
<%@page import="defPackage.Classroom"%>
<%@page import="database.AllConnections"%>
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

<script type="text/javascript" src='js/multiInput.js'></script>
<script type="text/javascript" src='js/studentAddSeminar.js'></script>
<link rel="stylesheet" href="css/multiInput.css" />

<style>
.raised.segment {
	margin: 1.5% !important;
	width: 30%;
	display: inline-block;
	margin-top: 100px;
	float: left;
	text-align: center;
	height: 80%;
}

.seminarist-add {
	margin: 1%;
}

.ui.checkbox {
	padding-top: 1.4%;
	padding-left: 1%;
}
.ui.header h2{
	display: inline;
}
.icon {
	cursor: pointer;
}
.icon-student {
	float: right;
	display: inlie;
}
.remove.icon {
	float: right;
	margin-top: 0.8% !important;
}
.students{
	max-height: 250px !important;
	position: relative;
	overflow: auto;
}
.fixed-position{
	display: inline-block;
	position: fixed;
	clear: both;
	bottom: 0;
    width: 100%;
}
.ui.menu {
	margin-top: 0;
}
.block.header {
	margin: 0;
}
.sign-out {
	float: right;
	margin-top: 0.8%;
	margin-right: 0.7%;
}
.head-panel {
	display: block;
	margin: 0 !important;
	padding: 0 !important;
}

.head-text {
	display: inline-block;
	border: solid;
	padding: .78571429rem 1rem !important;
	!
	important;
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


		List<Seminar> Seminars = connector.seminarDB.getSeminars(classroomID);
		List<Person> studentsWithoutSeminar = connector.studentDB.getStudentsWithoutSeminar(classroomID);
		List<Person> SeminaristsWithoutSeminar = connector.seminaristDB
				.getSeminaristsWithoutSeminar(classroomID);
	%>
	<div class="ui block header head-panel">
	<a href="index.jsp">
		<h3 class="ui header head-text">Macs Classroom</h3>
	</a>
	  <a class="sign-out" href="DeleteSessionServlet" onclick="signOut();">Sign out</a>
	</div>
	<div class="ui menu">
		<a
			href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>
			class="header item"> <%=currentClassroom.getClassroomName()%>
		</a> <a class="item"
			href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Stream</a>

		<a class="item"
			href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>About</a>

		<a class="item"
			href=<%="assignments.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Assignments</a>

		<%
			if (isAdmin || isLecturer) {
		%>
		<a class="item"
			href=<%="settings.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Settings</a>
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
					<a
						href=<%="sections.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>
						class="item"> Sections </a> <a
						href=<%="seminars.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>
						class="active item"> Seminars </a>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="classroom-id" value ="<%= classroomID %>">
	
	<div class="ui raised segment">
		<h2 class="ui header">Students Without Seminars</h2>
		
		<div class="ui middle aligned selection list">
			<form method="POST" action="AutoDistributionToSeminarsServlet">
				<input type="hidden" name = "<%= Classroom.ID_ATTRIBUTE_NAME %>" value= "<%= classroomID %>" >
				<button type="submit" class="positive ui button">Auto
					Distribution</button>
			</form>

			<%
				for (int i = 0; i < studentsWithoutSeminar.size(); i++) {
					Person currentStudent = studentsWithoutSeminar.get(i);
			%>
			<div class="item">
				<img class="ui avatar image"
					src="<%=currentStudent.getPersonImgUrl()%>">
				<div class="content">
					<div class="header"><%=currentStudent.getName() + " " + currentStudent.getSurname()%></div>
				</div>
			</div>
			<%
				}
			%>
		</div>
	</div>
	<%
		for (int i = 0; i < Seminars.size(); i++) {
			Seminar currentSeminar = Seminars.get(i);
			Person Seminarist = currentSeminar.getSeminarist();
			List<Person> currentSeminarStudents = currentSeminar.getSeminarStudents();
	%>
	<div class="ui raised segment">
		
		<i class="add user icon icon-student"></i>
		
		<input type="hidden" value="<%=currentSeminar.getSeminarN() %>">
		
		<div class="ui modal student" id="<%= currentSeminar.getSeminarN()%>">
		<div class="header">Add Student</div>
		<div class="content">
			<div class="field">
				<div class="emails">

					<input type="text" value="" placeholder="Add Email" />
				</div>
			</div>
		</div>
		<div class="actions">
			<button class="ui teal button studentAddSeminarButton">Add</button>
			<input type="hidden" value="AddStudentToSeminarServlet">
			<input type="hidden" value="<%= currentSeminar.getSeminarN()%>">
			<button class="ui red button cancel">Cancel</button>
		</div>
		</div>
		
		<script>
			
			$(".icon-student").click(function() {
				
				var modalId = $(this).next().val();
				$("#" + modalId).modal("show");
			});
		</script>
		<h2 class="ui header"><%=currentSeminar.getSeminarN()%></h2>
		<div class="ui middle aligned selection list">

			<div class="item">
				<%
					if (Seminarist != null) {
				%>
				<form action="RemoveSeminaristFromSeminarServlet" method="POST">
				<input type="hidden" name="seminarN" value="<%= currentSeminar.getSeminarN() %>">
				<input type="hidden" name="classroomID" value = "<%= classroomID %>">
     			<i class="remove icon"></i>
				 </form>
				 <script>
				 	$(".remove.icon").click(function(){
				 		$(this).parent().submit();
				 	});
				 	
				 </script>
				<img class="ui avatar image"
					src="<%=Seminarist.getPersonImgUrl()%>">
				<div class="content">
					<h3 class="ui header"><%=Seminarist.getName() + " " + Seminarist.getSurname()%></h3>
				</div>
				<hr>
				<%
					} else {
				%>
				<button class="positive ui button">Set Seminar Leader</button>
				<form class="seminarist-add" method="post"
					action="AddSeminaristToSeminarServlet">
					<div class="ui selection dropdown">
						<input type="hidden" name="seminaristEmail"> <i
							class="dropdown icon"></i>
						<div class="default text">Choose Seminar Leader</div>
						<div class="menu">
							<%
								for (int j = 0; j < SeminaristsWithoutSeminar.size(); j++) {
											Person currentSeminarist = SeminaristsWithoutSeminar.get(j);
							%>
							<div class="item"
								data-value="<%=currentSeminarist.getEmail()%>"><%=currentSeminarist.getName() + " " + currentSeminarist.getSurname()%></div>
							<%
								}
							%>
						</div>
					</div>
					<input type="hidden" name="seminarN"
						value="<%=currentSeminar.getSeminarN()%>"> <input
						type="hidden" name="<%=Classroom.ID_ATTRIBUTE_NAME%>"
						value="<%=classroomID%>"> <input type="submit"
						class="ui teal button" value="Set">
				</form>
				<%
					}
				%>
			</div>
		</div>
		<form method="POST" action="RemoveStudentsFromSeminarServlet">
			<div class="ui middle aligned selection list students">
				<%
					for (Person currentStudent : currentSeminarStudents) {
				%>
				<div class="item">
					<img class="ui avatar image"
						src="<%=currentStudent.getPersonImgUrl()%>">
					<div class="content">
						<div class="header"><%=currentStudent.getName() + " " + currentStudent.getSurname()%></div>
					</div>
					<div class="ui checkbox">
						<input type="checkbox" name="studentsEmails"
							value="<%=currentStudent.getEmail()%>"> <label></label>
					</div>
				</div>
				<%
					}
				%>



			</div>
			<input type="hidden" name="seminarN" value="<%=currentSeminar.getSeminarN()%>">
			<input type="hidden" name="classroomID" value="<%=classroomID%>">
			<input type="submit" class="ui red button" value="Remove Marked">
		</form>
	</div>
	
	<%
		}
	%>
	
	
	<i class="huge add circle icon fixed-position"></i>
	<form action="AddNewSeminarServlet" method="Post">
	<input type="hidden" name="<%= Classroom.ID_ATTRIBUTE_NAME %>" value = "<%= classroomID %>">
	</form>
	<i style="margin-left: 5%;"class="huge minus circle icon fixed-position"></i>
	<form action="DeleteSeminarServlet" method="Post">
	<input type="hidden" name="<%= Classroom.ID_ATTRIBUTE_NAME %>" value = "<%= classroomID %>">
	</form>
	
	<script>
		$(".fixed-position").click(function(){
			$(this).next().submit();
		});
		$(".seminarist-add").hide();
		$('.ui.dropdown').dropdown();
		$(".positive.ui.button").click(function() {
			$(this).next().toggle();
		});
	</script>
	
</body>
</html>