<%@page import="defPackage.StudentAssignment"%>
<%@page import="database.PersonDB"%>
<%@page import="defPackage.Comment"%>
<%@page import="defPackage.Person"%>
<%@page import="defPackage.Section"%>
<%@page import="defPackage.MyDrive"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="defPackage.Seminar"%>
<%@page import="defPackage.Post"%>
<%@page import="java.util.List"%>
<%@page import="defPackage.Classroom"%>
<%@page import="defPackage.Assignment"%>
<%@page import="database.AllConnections"%>
<%@page import="WorkingServlets.DownloadServlet"%>
<%@page import="defPackage.Material"%>


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
<style type="text/css">
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
	!
	important;
}
body > *{
	margin: 0.5%;
}
</style>
</head>
<body>


	<%!private String generateAssignmentHTML(Assignment a, String fileId) {
		
		String result = "<div class=\"ui top attached tabular menu\"> " + 
						"<div class=\"ui raised segment\"> <div class=\"active item\"> " + 
						a.getTitle() + "</div>" + "<div class=\"ui bottom attached active tab segment\"> " +
						"<p>" + a.getInstructions() + "</p>";
						
						result+="<p></p>";								   						
						
						if(a.getFileName() != null){
							result +=" <a class=\"ui blue ribbon label\"> File: <a href=https://drive.google.com/open?id=" + 
							fileId + ">" + a.getFileName() + "</a></p>";
						}
						result+="<p></p>";
						if( a.getDeadline()!= null){
							result+="<a class=\"ui red ribbon label\"> Deadline: " + a.getDeadline() + "</a>";
						}						
						
						result+="</div> </div> </div>";
		
		return result;
	}%>
	
	<%!private String generateStudentHTML(Person p, String classroomID, Assignment a, AllConnections connector) {
		
		StudentAssignment studentAssignment = 
				connector.studentAssignmentDB.getStudentAssignment(classroomID, p.getPersonID(), a.getAssignmentID());
		boolean isApproved = studentAssignment.getApproval();
		String grade = studentAssignment.getAssignmentGrade();
		
		String gradeCode;
		if (grade == null || grade.equals("Not Graded"))
			gradeCode = "<div class=\"ui red horizontal label\">Not Graded</div>"; else
			gradeCode = "<div class=\"ui green horizontal label\">Graded</div>";
		
		String approvalCode;
		if (isApproved)
			approvalCode = "<div class=\"ui green horizontal label\">Approved</div>"; else
			approvalCode = "<div class=\"ui red horizontal label\">Not Yet Approved</div>";
		
		String hrefCode = " href = \"studentsOneAssignment.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID+ 
				"&studentID="+p.getPersonID()+ "&assignmentID=" + a.getAssignmentID() +"\"";
		
		String result = "<div class=\"item\">" +
						"<div class=\"right floated content\">" +
						gradeCode +
						approvalCode +
						"</div>" +
						"<img class=\"ui avatar image\" src = \"" + p.getPersonImgUrl() + "\">" +
						"<div class = \"content\">" +
						"<a class =\"header\"" + hrefCode + ">" + p.getName() + " " + p.getSurname() + "</a>" +
						"<div class = \"description\">" + p.getEmail() + "</div>" +
						"</div>" + 
						"</div>"; 
		
		return result;
	}%>

	<%
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomID);
		
		
		Person currentPerson = (Person)request.getSession().getAttribute("currentPerson");
		boolean isAdmin = connector.personDB.isAdmin(currentPerson);
		boolean isStudent = currentClassroom.classroomStudentExists(currentPerson.getEmail());
		boolean isSectionLeader = currentClassroom.classroomSectionLeaderExists(currentPerson.getEmail());
		boolean isSeminarist = currentClassroom.classroomSeminaristExists(currentPerson.getEmail());
		boolean isLecturer = currentClassroom.classroomLecturerExists(currentPerson.getEmail());
		
		if(!isAdmin && !isSectionLeader && !isSeminarist && !isLecturer){
			 response.sendError(400, "Not Permitted At All");
		}
		
		String assignmentID = request.getParameter("assignmentID");
		System.out.println("writing:    " +assignmentID );
		Assignment assignment = connector.assignmentDB.getAssignment(assignmentID);
		
		
		
		for (Person p : currentClassroom.getClassroomStudents()){
			
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			String deadlineWithReschedulings = "";
			if (assignment.getDeadline()!=null) deadlineWithReschedulings = format1.format(assignment.getDeadline());
			
			
			connector.studentAssignmentDB.addStudentAssignment(classroomID, p.getPersonID(), assignmentID,
					deadlineWithReschedulings );
		}
		
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
	
	<!-- -------------------------------------------------------------------- -->
	<%
		MyDrive service = (MyDrive) request.getServletContext().getAttribute("drive");
		String assignmentFileId = service.getAssignmentFileId(classroomID, assignment.getTitle());
		String htmlCode = generateAssignmentHTML(assignment, assignmentFileId);
		out.println(htmlCode);
	%>
	
	<%if (isSectionLeader){%>
		
		<%Section section = connector.sectionDB.getSectionByLeader(currentPerson, classroomID);
		if (section != null) {
			List <Person> sectionStudents = section.getSectionStudents();
			
			out.println("<h2>Students:</h2>");
			out.println("<div class=\"ui relaxed list\">");
			for (Person p : sectionStudents) {
				out.println(generateStudentHTML(p, classroomID, assignment, connector));
			}
			out.println("</div>");
		} else {
			out.println("you have no section yet...");
		}%>
		
	<%}%>
	
	
	
	<%if (isSeminarist){%>
		
		<%Seminar seminar = connector.seminarDB.getSeminarBySeminarist(currentPerson, classroomID);
		if (seminar != null){
			List <Person> seminarStudents = seminar.getSeminarStudents();
			
			out.println("<h2>Students:</h2>");
			out.println("<div class=\"ui relaxed list\">");
			for (Person p : seminarStudents){
				out.println(generateStudentHTML(p, classroomID, assignment, connector));
			}
			out.println("</div>");
		} else {
			out.println("you have no seminar group yet...");
		}%>
	<%}%>
	
	<!-- -------------------------------------------------------------------- -->
	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	<script type="text/javascript" src='js/posts.js'></script>
	<script type="text/javascript" src='js/comments.js' type="text/javascript"></script>
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


</body>
</html>