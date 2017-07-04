<%@page import="defPackage.StudentAssignment"%>
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
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
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
<% 	
	String studentEmail = request.getParameter("studentEmail");

	String assignmentTitle = request.getParameter("assignmentTitle"); 
	System.out.println("+++++++++++++++++++");
	System.out.println("title now: " + assignmentTitle);
	System.out.println("+++++++++++++++++++");
	
	String status = request.getParameter("status");
	%>
<title>assignmentTitle</title>




</head>
<body>

	<%!public Date addDays(Date dt, int days){
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, days);  // number of days to add
		return c.getTime();
	}	
	%>
	

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
		
		
		String personID = connector.personDB.getPersonId(studentEmail);
		StudentAssignment assignment = connector.studentAssignmentDB.getStudentAssignment(
				classroomID, personID, assignmentTitle);
		if(assignment!=null) status = "done";

		
		
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
		</ul>
	</div>
	</nav>
	
	
	
	<!-- -------------------------------------------------------------------- -->
	<%
		
		
		Assignment a = connector.assignmentDB.getAssignment(assignmentTitle, classroomID);
			String htmlCode = generateAssignmentHTML(a);
			out.println(htmlCode);
		
	%>
	
	
	<%
		if(isStudent && status==null){
			
			Date now = new Date();
			Date availableDate = a.getDeadline();
			boolean canTurnIn = false;
			int mustUsereschedulings = 0;
			
			
			if(now.before(a.getDeadline() )){	
				
			%>
			<form action=<%="TurnInAssignmentServlet"%>
						enctype="multipart/form-data" method="POST">
				<h6>Upload File</h6>
				
				<textarea style="display:none;" name="studentEmail"><%=studentEmail%></textarea>
				<textarea style="display:none;" name=<%=Classroom.ID_ATTRIBUTE_NAME%>><%=classroomID%></textarea>
				<textarea style="display:none;" name="assignmentTitle"><%=assignmentTitle%></textarea>
				
				<input type="file" name="file" size="30" /> </br> <input type="submit"
					/ value="Turn In" class="btn btn-success">
			</form>	
			
				
			<%
			}else{
				int maxAvailableRes = currentClassroom.getNumberOfReschedulings() - 
						connector.studentDB.reschedulingsUsed(studentEmail, classroomID) ;
				System.out.println(maxAvailableRes + " maxAvRes");
				for(int i = 1; i <= maxAvailableRes; i++){
					availableDate = addDays(availableDate, currentClassroom.getReschedulingLength());
					System.out.println(availableDate + " avDate " + i);
					if(now.before(availableDate)){
						canTurnIn = true;
						mustUsereschedulings = i;
						break;
					}
				}
			
			
			if(canTurnIn){
				%>
				<form action=<%="TurnInAssignmentServlet?"+Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID+
					"&studentEmail="+studentEmail+"&assignmentTitle="+assignmentTitle + 
					"&numreschedulings="+ mustUsereschedulings%> enctype="multipart/form-data" method="POST">
					<h6>Upload File</h6>
					
					<textarea style="display:none;" name="studentEmail"><%=studentEmail%></textarea>
					<textarea style="display:none;" name=<%=Classroom.ID_ATTRIBUTE_NAME%>><%=classroomID%></textarea>
					<textarea style="display:none;" name="assignmentTitle"><%=assignmentTitle%></textarea>
					
					<input type="file" name="file" size="30" /> </br> <input type="submit"
						/ value="Turn In" class="btn btn-success">
				</form>	
				
				
					
				<%
			
			}else{
				
				%>
				<h2 style = " color: red">Late</h2>
				
				
				<%
				
			}
			
			
			}	
		
		
		}else if(status.equals("done")){
			
			
				out.println(" <a href=\"DownloadServlet?" + DownloadServlet.DOWNLOAD_PARAMETER 
						+ "=" + assignment.getFileName()+ "\">" + assignment.getFileName() + "</a>");
			
			
		}
	%>
	
	
	<!-- -------------------------------------------------------------------- -->
	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	<script type="text/javascript" src='js/posts.js'></script>
	<script type="text/javascript" src='js/comments.js' type="text/javascript"></script>



</body>
</html>