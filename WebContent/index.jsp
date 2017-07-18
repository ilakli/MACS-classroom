
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


<script src="https://apis.google.com/js/platform.js" async defer></script>
<script type="text/javascript">
	function redirectClassroom() {

		window.location = "createClassroom.jsp"

	}
	function redirectLecturer() {
		window.location = "addLecturer.html"
	}
</script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.css">
<script
	src="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.js"></script>


<script type="text/javascript" src='js/multiInput.js'></script>
<script type="text/javascript" src='js/lecturerAdd.js'></script>

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
	padding-right: 10%;
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


.container {

    margin-top: 2%;
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

.fixed-position {
	display: inline-block;
	position: fixed;
	clear: both;
	bottom: 0;
	width: 100%;
	cursor: pointer;
	z-index: 999;
	padding-top: 0%;
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
		if(currentPerson == null){
			response.sendError(400, "Not Permitted At All");
			return;
		}
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
	<i class="huge add circle icon right fixed-position "></i>
	
	<form class="ui modal add-classroom" action="CreateClassroomServlet" method="post">
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
			<input type="submit" class="ui teal button classroomAddButton" value="Add">
		</div>
	</form>
	
	<%
		}
	%>

		



<!-- -----------------------------------------------------------------------  -->
	




<!-- -----------------------------------------------------------------------  -->

	<%-- 
		Generates HTML code according to given name. 
		HTML code consists of section and div which together make up a classroom display.
	 --%>
	 
	 

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
				+ "<div class=\"description\">" + "You are "+ yourPositionInClassroom(currentClassroom, currentPerson, connector) 
				+ "</div>" + "</div>" + "</div>";
		return result;
	}%>



	
	
	
	<section class="container">
		
		
		
		<div class="one">
		    <%
			if (isAdmin) {		
			%>
			
			<div class="ui modal global-lecturer">
				<div class="header">Add Lecturer</div>
				<div class="content">
					<div class="field">
						<div class="emails">
		
							<input type="text" value="" placeholder="Add Email" /> <input
								type="hidden" id="globalLecturerAddServlet"
								value="AddNewGlobalLecturerServlet">
						</div>
					</div>
				</div>
				<div class="actions">
					<button class="ui teal button globalLecturerAddButton">Add</button>
					<button class="ui red button cancel">Cancel</button>
				</div>
			</div>
			
			
			<div class="list-wrapper">
				<div class="head-wrapper">
					
					<h2 class="ui header">Lecturers</h2>
					
					<i class="add user icon icon-global-lecturer"></i>
					<%
						
					%>
					<script>
						$(".icon-global-lecturer").click(function() {
							$('.ui.modal.global-lecturer').modal('show');
						});
					</script>
				</div>
				<form method="POST" action="DeleteGlobalLecturerServlet">
					<div class="ui celled list">
						<%
							for (Person currentGlobalLecturer : globalLecturers) {
						%>
						<div class="item">
							<div class="right floated content">
								<%
									
								%>
								<div class="ui checkbox">
									<input type="checkbox"
										name="<%=currentGlobalLecturer.getEmail()%>"
										value="<%=currentGlobalLecturer.getEmail()%>"> <label></label>
								</div>
								<%
									
								%>
							</div>
							<img class="ui avatar image"
								src="<%=currentGlobalLecturer.getPersonImgUrl()%>">
							<div class="content">
								<div class="header"><%=currentGlobalLecturer.getName() + " " + currentGlobalLecturer.getSurname()%></div>
								<%=currentGlobalLecturer.getEmail()%>
							</div>
						</div>
						<%
							}
						%>
					</div>
				
					<%
						
					%>
					<button type="submit" class="ui red button">Remove Marked</button>
					
				</form>
			</div>
		
			<%
			}		
			%>
				    
		</div>
		    
		    
		    
		<div class="two">
		    
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
		    
		    
		    
		    
		    
		</div>
	</section>

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
			$(".ui.modal.add-classroom").modal("show");
		});
		$(".classroomAddButton").click(function(){
			$(this).parent().parent().parent().submit();
		});
	</script>
	
	<script type="text/javascript" src='js/index.js'></script>
	
</body>
</html>