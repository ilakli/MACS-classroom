<%@page import="database.PersonDB"%>
<%@page import="defPackage.Comment"%>
<%@page import="defPackage.Person"%>
<%@page import="defPackage.Section"%>
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

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="css/style.css">

<title>Assignments</title>




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
	
	<%!private String generateStudentHTML(Person p, String classroomID, Assignment a) {
		
		String result = "<li><a href = \"studentsOneAssignment.jsp?"+Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID+ 
				"&studentEmail="+p.getEmail()+ "&assignmentTitle=" + a.getTitle() +"\"> " + p.getEmail() + "</a></li>"; 
		
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
		
		
		PersonDB personConnector = new PersonDB();
		
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
			
			<%if (isAdmin || isLecturer || isSeminarist){%>
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
		</ul>
	</div>
	</nav>
	
	
	<!-- -------------------------------------------------------------------- -->
	<%
		String assignmentTitle = request.getParameter("assignmentTitle");
								
		Assignment assignment = connector.assignmentDB.getAssignment(assignmentTitle, classroomID);
	
		String htmlCode = generateAssignmentHTML(assignment);
		out.println(htmlCode);
	%>
	
	<%if (isSectionLeader){
		
		Section section = connector.sectionDB.getSectionByLeader(currentPerson, classroomID);
		if (section != null) {
			List <Person> sectionStudents = section.getSectionStudents();
			
			out.println("<h2>Students:</h2>");
			out.println("<ul>");
			for (Person p : sectionStudents) {
				out.println(generateStudentHTML(p, classroomID, assignment));
			}
			out.println("</ul>");
		} else {
			out.println("you have no section yet...");
		}
	}%>
	
	<%if (isSeminarist){%>
		<!-- TODO -->
	<%} %>
	
	
	<!-- -------------------------------------------------------------------- -->
	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	<script type="text/javascript" src='js/posts.js'></script>
	<script type="text/javascript" src='js/comments.js' type="text/javascript"></script>



</body>
</html>