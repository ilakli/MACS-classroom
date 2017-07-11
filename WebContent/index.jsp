
<%@page import="defPackage.Person"%>
<%@page import="database.LecturerDB"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="defPackage.Classroom"%>
<%@page import="database.AllConnections"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="google-signin-client_id"
	content="127282049380-isld6v6lrvjeqk5nrq8o9qjquk5bp0ig.apps.googleusercontent.com">
<title>Macs Classroom</title>

<link rel="icon" href="favicon.ico" type="image/x-icon" />

<script src="https://apis.google.com/js/platform.js" async defer></script>
<script type="text/javascript">
	function redirectClassroom() {

		window.location = "createClassroom.jsp"

	}
	function redirectLecturer() {
		window.location = "addLecturer.html"
	}
</script>
<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.css">
<script
	src="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.js"></script>

<style>

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

.fixed-position {
	display: inline-block;
	position: fixed;
	clear: both;
	bottom: 0;
	width: 100%;
	cursor: pointer;
	z-index: 999;
	color: red;
}
.single-classroom {
	margin-left: 1% !important;
	margin-top: 1% !important;
}
</style>
</head>
<body>
	<%
		Person currentPerson = (Person) request.getSession().getAttribute("currentPerson");
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		LecturerDB lecturerDB = connector.lecturerDB;
		ArrayList<Person> globalLecturers = lecturerDB.getGlobalLecturers();

		boolean isAdmin = connector.personDB.isAdmin(currentPerson);
		boolean isGlobalLecturer = globalLecturers.contains(currentPerson);
	%>

	<div class="ui block header head-panel">
		<a href="index.jsp">
			<h3 class="ui header head-text">Macs Classroom</h3>
		</a> <a class="sign-out" href="DeleteSessionServlet" onclick="signOut();">Sign
			out</a>
	</div>

	<%
		if (isAdmin || isGlobalLecturer) {
	%>
	<i class="huge add circle icon fixed-position"></i>
	
	<form class="ui modal global-lecturer" action="CreateClassroomServlet" method="post">
		<div class="header">Add Classroom</div>
		<div class="content">
			<div class="field">

					<div class="ui input">
						<input type="text" placeholder="Enter Classroom Name" name="newClassroomName">
					</div>
					<input type="hidden" name="lecturerEmail"
						value='<%=currentPerson.getEmail()%>'> 
					<input type="hidden" name="lecturerID"
						value='<%=currentPerson.getPersonID()%>'>

			</div>
		</div>
		<div class="actions">
			<input type="submit" class="ui teal button lecturerAddButton" value="Add">
		</div>
	</form>
	
	<%
		}
	%>

	<%
		if (isAdmin) {
	%>
	<button id="create" type="submit" class="btn btn-danger"
		onclick="redirectLecturer()">Add New Lecturer</button>
	<%
		AllConnections connection = (AllConnections) request.getServletContext().getAttribute("connection");

			System.out.println("Globals are: " + globalLecturers);

			out.println("<div id=\"global-lecturers\">");
			out.println("<h4>Lecturers</h4>");
			out.println("<ul>");
			for (int i = 0; i < globalLecturers.size(); i++) {
				Person currentLecturer = globalLecturers.get(i);
				out.println(printPersonInfo(currentLecturer));
			}

			out.println("</ul>");
			out.println("</div>");
	%>
	<%
		}
	%>

	<!--  Given a person generates list item containing persons name, surname and email -->
	<%!private String printPersonInfo(Person currentPerson) {
		String result = "<li><h5>";
		result = result + currentPerson.getEmail() + " " + currentPerson.getName() + " " + currentPerson.getSurname();
		result = result + "</h5></li>";

		return result;
	}%>


	<%!
	private String yourPositionInClassroom(Classroom currentClassroom, Person currentPerson, AllConnections connector){
		if(connector.personDB.isAdmin(currentPerson)){
			return "Admin";
		}
		if(currentClassroom.classroomLecturerExists(currentPerson.getEmail())){
			return "Lecturer";
		}
		if(currentClassroom.classroomSeminaristExists(currentPerson.getEmail())){
			return "Seminarist";
		}
		if(currentClassroom.classroomSectionLeaderExists(currentPerson.getEmail())){
			return "Section Leader";
		}
		if(currentClassroom.classroomStudentExists(currentPerson.getEmail())){
			return "Student";
		}		
		return "nothing";
	}
	
	%>



	<%-- 
		Generates HTML code according to given name. 
		HTML code consists of section and div which together make up a classroom display.
	 --%>
	<%!private String generateNameHTML(Classroom currentClassroom, Person currentPerson, AllConnections connector, String creatorName) {
		String result1 = "<section class=\"single-classroom\"> <div class=\"well\"> <a href=\"stream.jsp?"
				+ Classroom.ID_ATTRIBUTE_NAME + "=" + currentClassroom.getClassroomID() + 
				"\" class=\"single-classroom-text\">" + currentClassroom.getClassroomName()
				+ "</a> </div> </section>";
		String result = "<div class=\"card single-classroom\">" + "<div class=\"image\">"
				+ "<img src=\"https://krishna.org/wp-content/uploads/2010/11/Physics-Blackbord-of-Famous-Equations-620x350.png?x64805\">"
				+ "</div>" + "<div class=\"content\">" + "<div class=\"header\"> " + "<a href=\"stream.jsp?"
				+ Classroom.ID_ATTRIBUTE_NAME + "=" + currentClassroom.getClassroomID() 
				+ "\" class=\"single-classroom-text\">" + currentClassroom.getClassroomName()
				+ "</a>" + "</div>" + "<div class=\"meta\">" + "<a>" + creatorName + "</a>" + "</div>"
				+ "<div class=\"description\">" + " You are "+ yourPositionInClassroom(currentClassroom, currentPerson, connector) 
				+ " </div>" + "</div>" + "</div>";
		return result;
	}%>
	<%-- 
		Takes DBConnector from servlet context and pulls list of classrooms out of it. 
		Then displays every classroom on the page.
	--%>
	<div class="ui link cards">
		<%
			ArrayList<Classroom> classrooms;
			if (isAdmin)
				classrooms = connector.classroomDB.getClassrooms();
			else
				classrooms = connector.classroomDB.getPersonsClassrooms(currentPerson);

			System.out.println("person was: " + currentPerson.getEmail());

			for (Classroom classroom : classrooms) {
				String creatorName = connector.personDB.getPerson(classroom.getClassroomCreatorId()).getName() + " "
						+ connector.personDB.getPerson(classroom.getClassroomCreatorId()).getSurname();
				out.print(generateNameHTML(classroom, currentPerson, connector, creatorName));
			}
		%>
	</div>
	<script>
		function signOut() {
			var auth2 = gapi.auth2.getAuthInstance();
			auth2.signOut().then(function() {
				console.log('User signed out.');
			});
		}

		function onLoad() {
			gapi.load('auth2', function() {
				gapi.auth2.init();
			});
		}
	</script>

	<script src="https://apis.google.com/js/platform.js?onload=onLoad"
		async defer></script>
	<script>
		$(".fixed-position").click(function() {
			$(".ui.modal.global-lecturer").modal("show");
		});
		$(".classroomAddButton").click(function(){
			alert("Hey");
			$(this).parent().parent().parent().submit();
		});
	</script>

</body>
</html>