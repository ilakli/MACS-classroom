
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
<meta name="google-signin-client_id" content="127282049380-isld6v6lrvjeqk5nrq8o9qjquk5bp0ig.apps.googleusercontent.com">
<title>Macs Classroom</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
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

</head>
<body>
	<%
		Person currentPerson = (Person)request.getSession().getAttribute("currentPerson");
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		LecturerDB lecturerDB = connector.lecturerDB;
		ArrayList<Person> globalLecturers = lecturerDB.getGlobalLecturers();
		
		boolean isAdmin = connector.personDB.isAdmin(currentPerson);
		boolean isGlobalLecturer = globalLecturers.contains(currentPerson);
		
	%>

	<div class="jumbotron">
		<h2>
			<a href="index.jsp" id="header-name">Macs Classroom</a>
		</h2>
	</div>
	  <a href="DeleteSessionServlet" onclick="signOut();">Sign out</a>

	<%if (isAdmin || isGlobalLecturer) { %>
	<button id="create" type="submit" class="btn btn-danger"
		onclick="redirectClassroom()">Create New Classroom</button>
	<%}%>
	
	<%if (isAdmin){ %>
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
	<%}%>
	
	<!--  Given a person generates list item containing persons name, surname and email -->
	<%!
		private String printPersonInfo(Person currentPerson) {
		String result = "<li><h5>";
		result = result + currentPerson.getEmail() + " " + currentPerson.getName() + " " + currentPerson.getSurname();
		result = result + "</h5></li>";
		
		return result;
	}
	%>
	
	

	


	<%-- 
		Generates HTML code according to given name. 
		HTML code consists of section and div which together make up a classroom display.
	 --%> <%!private String generateNameHTML(String name, String classroomId) {
		String result = "<section class=\"single-classroom\"> <div class=\"well\"> <a href=\"stream.jsp?"
				+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId + "\" class=\"single-classroom-text\">" + name
				+ "</a> </div> </section>";
		return result;
	}%> <%-- 
		Takes DBConnector from servlet context and pulls list of classrooms out of it. 
		Then displays every classroom on the page.
	--%>
	<%
		ArrayList<Classroom> classrooms;
		if (isAdmin) classrooms = connector.classroomDB.getClassrooms(); else
					 classrooms = connector.classroomDB.getPersonsClassrooms(currentPerson);
		
		System.out.println("person was: " + currentPerson.getEmail());
		
		for (Classroom classroom : classrooms) {
			out.print(generateNameHTML(classroom.getClassroomName(), classroom.getClassroomID()));
		}
	%>
	
	<script>
    function signOut() {
      var auth2 = gapi.auth2.getAuthInstance();
      auth2.signOut().then(function () {
        console.log('User signed out.');
      });
    }

    function onLoad() {
      gapi.load('auth2', function() {
        gapi.auth2.init();
      });
    }
  </script>

	<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>

</body>
</html>