<%@page import="database.PersonDB"%>
<%@page import="defPackage.Comment"%>
<%@page import="defPackage.Person"%>
<%@page import="defPackage.Post"%>
<%@page import="java.util.List"%>
<%@page import="defPackage.Classroom"%>
<%@page import="defPackage.Assignment"%>
<%@page import="database.AllConnections"%>
<%@page import="WorkingServlets.DownloadServlet"%>
<%@page import="defPackage.Material"%>
<%@page import="java.util.Date"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.css">
<script
	src="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.js"></script>

<link rel="icon" href="favicon.ico" type="image/x-icon" />
<title>Assignments</title>
<style>
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
}
.assignment-add-button {
	margin: 0 0.8% !important;
}
</style>
</head>
<body>
	<%
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomID);

		Person currentPerson = (Person) request.getSession().getAttribute("currentPerson");
		boolean isAdmin = connector.personDB.isAdmin(currentPerson);
		boolean isStudent = currentClassroom.classroomStudentExists(currentPerson.getEmail());
		boolean isSectionLeader = currentClassroom.classroomSectionLeaderExists(currentPerson.getEmail());
		boolean isSeminarist = currentClassroom.classroomSeminaristExists(currentPerson.getEmail());
		boolean isLecturer = currentClassroom.classroomLecturerExists(currentPerson.getEmail());
		
		if(!isAdmin && !isStudent && !isSectionLeader && !isSeminarist && !isLecturer){
			 response.sendError(400, "Not Permitted At All");
		}
		
		PersonDB personConnector = connector.personDB;
	%>

		<div class="ui block header head-panel">
	<a href="index.jsp">
		<h3 class="ui header head-text">Macs Classroom</h3>
	</a>
	  <a class="sign-out" href="DeleteSessionServlet" onclick="signOut();">Sign out</a>
</div>
	<%!private String generateAssignmentHTML(Assignment a, boolean isStudent, boolean isSectionLeader,
			boolean isSeminarist, String classroomID, Person currentPerson) {

		String result = "<div class=\"panel panel-default\"> " + " <div class=\"panel-body\"> ";
		if (isStudent) {
			result += "<h1><a href = \"studentsOneAssignment.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID
					+ "&studentEmail=" + currentPerson.getEmail() + "&assignmentTitle=" + a.getTitle() + "\"> "
					+ a.getTitle() + "</a></h1>";
		} else {
			result += "<h1><a href = \"oneAssignment.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID
					+ "&assignmentTitle=" + a.getTitle() + "\"> " + a.getTitle() + "</a></h1>";
		}

		if (a.getDeadline() != null) {
			result += "<p> Deadline:" + a.getDeadline() + "</p>";
		}

		result += "</div>";

		return result;
	}%>
	<div class="ui menu">
		<a
			href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>
			class="header item"> <%=currentClassroom.getClassroomName()%>
		</a> <a class="item"
			href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Stream</a>


		<a class="item"
			href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>About</a>

		<a class="active item"
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
						class="item"> Seminars </a>
				</div>
			</div>
			<script>
				$('.ui.dropdown').dropdown();
			</script>
		</div>
	</div>

	<%
		if (isAdmin || isLecturer) {
	%>
	<button class="positive ui button assignment-add-button">Add
		New Assignment</button>

	
		<form action="AddNewAssignmentServlet" enctype="multipart/form-data"
				method="POST" class="ui modal">
		<i class="close icon"></i>
		<div class="header">Add Assignment</div>
		
		<div class="content">
			<div class="ui form">
				<div class="field">
					<label>Title</label> <input type="text" name="assignmentTitle">
				</div>
				<div class="field">
					<label>Instructionst</label>
					<textarea name="assignmentInstructions"></textarea>
				</div>

				<input type="hidden" name=<%=Classroom.ID_ATTRIBUTE_NAME%>
					value=<%=classroomID%>>

				<div class="field">
					<label>Deadline</label> <input name="deadline" type="date" value="" />
				</div>

				<div>
					<label for="file" class="ui icon button"> <i
						class="file icon"></i> Upload File
					</label> <input type="file" id="file" name="file" size=30
						style="display: none">
				</div>
			</div>
		</div>
		<div class="actions">
			<button type = "submit" class="ui teal button assignment-add-button">Add</button>
		</div>
	</form>
	<script>
		$(".assignment-add-button").click(function() {
			$('.ui.modal').modal('show');
		});
	</script>
	<%
		}
	%>




	<%
	


	List<Assignment> assignments = connector.assignmentDB.getAssignments(classroomID);
	for (Assignment a : assignments) {
		
		 %>
					<div class="ui segments">
					  <div class="ui segment">
					  			<%
					 			if(isStudent){ 
					 			 %>
					 			 	<%=
					 			 	"<h1><a href = \"studentsOneAssignment.jsp?"+Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID+
									"&studentEmail="+currentPerson.getEmail()+"&assignmentTitle="+a.getTitle()
									+"\"> " + a.getTitle() + "</a></h1>"
					 			 	
					 			 	
								 	%>
									
								<%
								} else {
								%>		
							
									<%= 
									"<h1><a href = \"oneAssignment.jsp?"+Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID+
									"&assignmentTitle="+a.getTitle()+"\"> " + a.getTitle() + "</a></h1>"
									%>
								<%		
								}

						  		%>		
					    
					    
					    
					  </div>
					  <div class="ui secondary segment">

					    <% 
					    	if( a.getDeadline()!= null){
					    %>
								<p> Deadline:" <%= a.getDeadline()  %>  </p>
								
						<%
							}
					    
					    %> 
					  </div>
					</div>			
					
					
					
					
					
			<%		
				}
			%>
	<script type="text/javascript" src='js/posts.js'></script>
	<script type="text/javascript" src='js/comments.js'
		type="text/javascript"></script>



</body>
</html>