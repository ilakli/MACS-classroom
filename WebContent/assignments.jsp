<%@page import="database.PersonDB"%>
<%@page import="defPackage.Comment"%>
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

<link rel="stylesheet" href="css/comments.css" type="text/css">
<title>Assignments</title>




</head>
<body>


	<%!private String generateAssignmentHTML(Assignment a) {
		
		String result = "<div class=\"panel panel-default\"> " + 
						" <div class=\"panel-body\"> " + 
						"<h1>" + a.getTitle() + "</h1>" + 
						"<p> " + a.getInstructions() + "</p>" +
						" <a href=\"DownloadServlet?"
						+ DownloadServlet.DOWNLOAD_PARAMETER + "=" + a.getName() + "\">" + a.getName() + "</a></div>"
						
						+ " <div class=\"panel-footer\"></div> " 
								
						+ "</div>";
		
		return result;
	}%>

	<%
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomID);
		
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
			<li><a
				href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>About</a></li>
			<li><a
				href=<%="formation.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Formation</a></li>
			<li><a
				href=<%="edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Edit</a></li>
			<li><a
				href=<%="settings.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Settings</a></li>
			<li class="active"><a
				href=<%="assignments.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Assignments</a></li>
		</ul>
	</div>
	</nav>

	<button type="button" class="w3-button w3-teal" id="myBtn">Add New Assignment</button>

	<div id="myModal" class="modal">

		<!-- Modal content -->
		<div class="modal-content">
			
			<div class="modal-header">
				<span class="close">&times;</span>
				<h2>Add New Assignment</h2>
			</div>
			
			<div class="modal-body">

				<div class="form-group">
					<form action="AddNewAssignmentServlet"  enctype="multipart/form-data" method="POST">
						
						<h6> Title </h6>
						
						<textarea
							class="form-control"
							rows="1" 
							name="assignmentTitle">
						</textarea>
						
						<h6> Instructions </h6>
						<textarea 
							class="form-control" 
							rows="5" 
							name="assignmentInstructions">
						</textarea>
						
						<input type="hidden" name=<%=Classroom.ID_ATTRIBUTE_NAME%>
							value=<%=classroomID%>>
						
						<h6> Upload File </h6>
						
						<input type="file" name="file" size="30" />
						
						</br>
						
						<input type="submit"/ value = "Submit" class="btn btn-success">
					</form>
					
				</div>

			</div>
		
		</div>

	
	</div>
	<!-- -------------------------------------------------------------------- -->
	<%
		List<Assignment> assignments = connector.assignmentDB.getAssignments(classroomID);
		
		for (Assignment a : assignments) {
			String htmlCode = generateAssignmentHTML(a);
			out.println(htmlCode);
		}
	%>
	<!-- -------------------------------------------------------------------- -->
	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	<script type="text/javascript" src='js/posts.js'></script>
	<script type="text/javascript" src='js/comments.js' type="text/javascript"></script>



</body>
</html>