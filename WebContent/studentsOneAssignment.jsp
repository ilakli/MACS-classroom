<%@page import="defPackage.StudentAssignment"%>
<%@page import="database.PersonDB"%>
<%@page import="defPackage.Comment"%>
<%@page import="defPackage.Person"%>
<%@page import="defPackage.Post"%>
<%@page import="defPackage.AssignmentComment"%>
<%@page import="java.util.List"%>
<%@page import="defPackage.Classroom"%>
<%@page import="defPackage.Assignment"%>
<%@page import="database.AllConnections"%>
<%@page import="WorkingServlets.DownloadServlet"%>
<%@page import="defPackage.Material"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>


<%@page import="java.text.SimpleDateFormat"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"

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

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="css/style.css">
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<% 	
	String studentEmail = request.getParameter("studentEmail");

	String assignmentTitle = request.getParameter("assignmentTitle"); 
	
	String status = request.getParameter("status");
	%>
	<title><%=assignmentTitle%></title>
	<style type="text/css">
	#COMMENT_TEXT {
		white-space: pre-line;
	}
	pre {
		white-space:pre-wrap;
	}
	#STAFF_COMMENT_ADDING_FORM{
		display: none;
	}
	#ALL_STAFF_COMMENTS{
		display: none;
	}
	</style>
</head>
<body>

	<%!public Date addDays(Date dt, int days){
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, days);  // number of days to add
		return c.getTime();
	}	
	%>
	
	<%!private String generateCommentHTML(AssignmentComment ac, AllConnections connector) {
		
		Person commentAuthor = connector.personDB.getPerson(ac.getPersonID());
		
		String result = "<div class = \"comment\">" +
						"<a class = \"avatar\">" +
						"<img src = \"" + commentAuthor.getPersonImgUrl() + "\"></a>" +
						"<div class = \"content\">" +
						"<a class = \"author\">" + commentAuthor.getName() + "</a>" + 
						"<div class = \"metadata\">" +
						"<div class = \"date\">" + ac.getCommentDate() + "</div>" +
						"</div>" +
						"<div class=\"text\">" + "<pre>" +  ac.getCommentText() + "</pre>" + "</div>" +
						"</div>" +
						"</div>";
		
		return result;
	}%>

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
		Assignment a = connector.assignmentDB.getAssignment(assignmentTitle, classroomID);
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String deadlineWithReschedulings = "";
		if (a.getDeadline()!=null) deadlineWithReschedulings = format1.format(a.getDeadline());
		connector.studentAssignmentDB.addStudentAssignment(classroomID, personID, assignmentTitle, deadlineWithReschedulings );
		
		StudentAssignment assignment = connector.studentAssignmentDB.getStudentAssignment(
				classroomID, personID, assignmentTitle);
		
		if(assignment!=null) {
			System.out.print(",,,,,,,,,,,,,,,assignment not null st jsp");
		}else System.out.print(",,,,,,,,,,,,,,,assignment null st jsp");
	

		List<AssignmentComment> assignmentComments = connector.commentDB.getStudentAssignmentComments(assignment.getStudentAssignmentId());
		List<AssignmentComment> assignmentStaffComments = connector.commentDB.getStudentAssignmentStaffComments(assignment.getStudentAssignmentId());
		
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
		
		
		
			String htmlCode = generateAssignmentHTML(a);
			out.println(htmlCode);
		
	%>
	
	
	<%
	System.out.println("......................................status"+status );
		if(isStudent){

			Date now = new Date();
			Date availableDate = assignment.getDeadlineWithReschedulings();
			
			boolean canTurnIn = false;
			int mustUseReschedulings = 0;
			if(availableDate != null){

			
		
				int maxAvailableRes = currentClassroom.getNumberOfReschedulings() - 
						connector.classroomDB.reschedulingsUsed(studentEmail, classroomID) ;
				System.out.println(maxAvailableRes + " maxAvRes");
				for(int i = 0; i <= maxAvailableRes; i++){
					availableDate = addDays(assignment.getDeadlineWithReschedulings(), i*currentClassroom.getReschedulingLength());
					System.out.println(availableDate + " avDate " + i);
					if(now.before(availableDate)){
						canTurnIn = true;
						mustUseReschedulings = i;
						break;
					}
				}
			
			}else canTurnIn = true;
			
			if(canTurnIn){
				
				
				System.out.println("........................uses reschedule  " + mustUseReschedulings );
				
				
				%>
				<form action="TurnInAssignmentServlet" enctype="multipart/form-data" method="POST">
					
					<h6>Upload File</h6>
					
					<textarea style="display:none;" name="studentEmail"><%=studentEmail%></textarea>
					<textarea style="display:none;" name=<%=Classroom.ID_ATTRIBUTE_NAME%>><%=classroomID%></textarea>
					<textarea style="display:none;" name="assignmentTitle"><%=assignmentTitle%></textarea>
					
					<textarea style="display:none;" name="numReschedulings"><%=mustUseReschedulings%></textarea>
					
					<input type="file" name="file" size="30" /> <br> 
					<input type="submit" value="Turn In" class="btn btn-success">
				</form>	
				
				
					
				<%
			
			}else{
				
				%>
				<h2 style = " color: red">Late</h2>
				
				
				<%
				
			}
			
			
		}	
		
		
		
			
		
	
			List<String> uploadedFiles = connector.studentAssignmentDB.
					getStudentSentFiles(assignment.getStudentAssignmentId());	
			for(String s : uploadedFiles){
				out.println(" <a href=\"DownloadServlet?" + DownloadServlet.DOWNLOAD_PARAMETER 
							+ "=" + s+ "\">" + s + "</a>");
			}
			
		
	%>
	
	
	<!-- COMMENTS -->
		<div class="ui menu">
		  <a class="item active" id = "COMMENT_MENU_BAR">
		    Comments
		  </a>
		  <%if (!isStudent){%>
		  <a class="item" id = "STAFF_COMMENT_MENU_BAR">
		    Staff Comments
		  </a>
		  <%}%>
		  
		</div>
		
		<!-- BASIC COMMENTS -->
		<div class="ui comments" id="ALL_COMMENTS">
		 
		 	  <%
			  	for (AssignmentComment ac : assignmentComments){
			  		String commentHtml = generateCommentHTML(ac, connector);
			  		out.println(commentHtml);
			  	}
			  %>
			  
		  </div>
		  
		  <form class="ui reply form" id = "COMMENT_ADDING_FORM">		
		 	    <div class="field">
			      <textarea id="COMMENT_TEXT"></textarea>
			    </div>
					
				<div class="ui primary submit labeled icon button" id = "ADD_COMMENT_BUTTON">
					<textarea style="display:none" id=PERSON_ID><%=currentPerson.getPersonID()%></textarea>
					<textarea style="display:none" id=PERSON_IMG_URL><%=currentPerson.getPersonImgUrl()%></textarea>
					<textarea style="display:none" id=PERSON_NAME><%=currentPerson.getName()%></textarea>
					<textarea style="display:none" id=STUDENT_ASSIGNMENT_ID><%=assignment.getStudentAssignmentId()%></textarea>
					<i class="icon edit"></i> Add Comment
			  	</div>
		  </form>
  		  <!-- END OF BASIC COMMENTS -->
  		  
  		  <%if (!isStudent){%>
  		  <!-- STAFF COMMENTS -->
		
		  <div class="ui comments" id="ALL_STAFF_COMMENTS">
		 
		 	  <%
			  	for (AssignmentComment ac : assignmentStaffComments){
			  		String commentHtml = generateCommentHTML(ac, connector);
			  		out.println(commentHtml);
			  	}
			  %>
			  
		  </div>
		  
		  <form class="ui reply form" id = "STAFF_COMMENT_ADDING_FORM">		
		 	    <div class="field">
			      <textarea id="STAFF_COMMENT_TEXT"></textarea>
			    </div>
					
				<div class="ui primary submit labeled icon button" id = "ADD_STAFF_COMMENT_BUTTON">
					<textarea style="display:none" id=PERSON_ID><%=currentPerson.getPersonID()%></textarea>
					<textarea style="display:none" id=PERSON_IMG_URL><%=currentPerson.getPersonImgUrl()%></textarea>
					<textarea style="display:none" id=PERSON_NAME><%=currentPerson.getName()%></textarea>
					<textarea style="display:none" id=STUDENT_ASSIGNMENT_ID><%=assignment.getStudentAssignmentId()%></textarea>
					<i class="icon edit"></i> Add Staff Comment
			  	</div>
		  </form>  		  
  		  
  		  <!-- END OF STAFF COMMENTS -->
		  <%}%>
	<!-- END OF COMMENTS -->
	
	
	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	<script type="text/javascript" src='js/posts.js'></script>
	<script type="text/javascript" src='js/studentsOneAssignmentComment.js'></script>
	<script type="text/javascript" src='js/studentsOneAssignmentMenu.js'></script>
	<script type="text/javascript" src='js/comments.js' type="text/javascript"></script>



</body>
</html>