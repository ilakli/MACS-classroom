<%@page import="defPackage.StudentAssignment"%>
<%@page import="database.PersonDB"%>
<%@page import="defPackage.Comment"%>
<%@page import="defPackage.Person"%>
<%@page import="defPackage.Section"%>
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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/1.11.8/semantic.min.css"/>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="css/style.css">
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<title>Assignments</title>
<style type="text/css">
.item {
	width: 50%;
}
</style>



</head>
<body>


	<%!private String generateAssignmentHTML(Assignment a) {
		
		String result = "<div class=\"panel panel-default\"> " + 
						" <div class=\"panel-body\"> " + 
						"<h1>" + a.getTitle() + "</h1>" + 
						"<p> " + a.getInstructions() + "</p>";
						
						if( a.getDeadline()!= null){
							result+="<p> Deadline:" + a.getDeadline() + "</p>";
						}
						
						if(a.getFileName() != null){
							result +=" <a href=\"DownloadServlet?" + DownloadServlet.DOWNLOAD_PARAMETER 
									+ "=" + a.getFileName() + "\">" + a.getFileName() + "</a></div>";
						}
						
						
						result+= " <div class=\"panel-footer\"></div> " 
								
						+ "</div>";
		
		return result;
	}%>
	
	<%!private String generateStudentHTML(Person p, String classroomID, Assignment a, AllConnections connector) {
		
		StudentAssignment studentAssignment = 
				connector.studentAssignmentDB.getStudentAssignment(classroomID, p.getPersonID(), a.getTitle());
		boolean isApproved = studentAssignment.getApproval();
		Integer grade = studentAssignment.getAssignmentGrade();
		
		String gradeCode;
		if (grade == null)
			gradeCode = "<div class=\"ui red horizontal label\">Not Graded</div>"; else
			gradeCode = "<div class=\"ui green horizontal label\">Graded</div>";
		
		String approvalCode;
		if (isApproved)
			approvalCode = "<div class=\"ui green horizontal label\">Approved</div>"; else
			approvalCode = "<div class=\"ui red horizontal label\">Not Yet Approved</div>";
		
		String hrefCode = " href = \"studentsOneAssignment.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID+ 
				"&studentEmail="+p.getEmail()+ "&assignmentTitle=" + a.getTitle() +"\"";
		
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
		
		String assignmentTitle = request.getParameter("assignmentTitle");
		Assignment assignment = connector.assignmentDB.getAssignment(assignmentTitle, classroomID);
	
		for (Person p : currentClassroom.getClassroomStudents()){
			
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			String deadlineWithReschedulings = "";
			if (assignment.getDeadline()!=null) deadlineWithReschedulings = format1.format(assignment.getDeadline());
			
			
			connector.studentAssignmentDB.addStudentAssignment(classroomID, p.getPersonID(), assignmentTitle,
					deadlineWithReschedulings );
		}
		
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
			
			<li class="active"><a
				href=<%="assignments.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Assignments</a></li>
			
			<%if (isAdmin || isLecturer){%>
			<li><a
				href=<%="settings.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Settings</a></li>
			
			<li><a
				href=<%="editSectionsAndSeminars.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
				Edit Sections And Seminars</a></li>
			<%}%>
			<li>
			<a
			href=<%="people.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
				People
			</a>
			</li>
		</ul>
	</div>
	</nav>
	
	<!-- -------------------------------------------------------------------- -->
	<%
		String htmlCode = generateAssignmentHTML(assignment);
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
		}%>
	<%}%>
	
	<!-- -------------------------------------------------------------------- -->
	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	<script type="text/javascript" src='js/posts.js'></script>
	<script type="text/javascript" src='js/comments.js' type="text/javascript"></script>



</body>
</html>