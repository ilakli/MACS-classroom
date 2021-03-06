<%@page import="defPackage.StudentAssignment"%>
<%@page import="database.PersonDB"%>
<%@page import="database.AssignmentGradeDB"%>
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
<%@page import="defPackage.MyDrive"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
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
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<% 	
	String studentID = request.getParameter("studentID");

	String assignmentID = request.getParameter("assignmentID"); 
	
	String status = request.getParameter("status");
	AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
	Assignment a = connector.assignmentDB.getAssignment(assignmentID);
	%>
	<title><%=a.getTitle()%></title>
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
	.grade {
		float: right;
		margin-top: 0.5% !important;
		margin-bottom: 0.5% !important;
		
	}
	.button-margin{
		margin-top: 0.5% !important;
		margin-bottom: 0.5% !important;
	}
	
	.lable{
		margin-top: 0.5% !important;
		margin-bottom: 0.5% !important;
	}


	.section{
		margin-top:2%;
	 	margin-left:2%;
	 	margin-right:2%;
	 	margin-bottom:2%;
	 }
	
	</style>
</head>
<body   style = "" >

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

	<%!private String generateAssignmentHTML(Assignment a, String fileId) {
		
		String result = "<div class=\"ui top attached tabular menu\"> <div class=\"ui section\">" + 
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
						
						result+="</div> </div></div> <br></div>";
		
		return result;
	}%>

	<%
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomID);
				
		Person currentPerson = (Person)request.getSession().getAttribute("currentPerson");
		if(currentPerson == null){
			response.sendError(400, "Not Permitted At All");
			return;
		}
		boolean isAdmin = connector.personDB.isAdmin(currentPerson);
		boolean isStudent = currentClassroom.classroomStudentExists(currentPerson.getEmail());
		boolean isSectionLeader = currentClassroom.classroomSectionLeaderExists(currentPerson.getEmail());
		boolean isSeminarist = currentClassroom.classroomSeminaristExists(currentPerson.getEmail());
		boolean isLecturer = currentClassroom.classroomLecturerExists(currentPerson.getEmail());
		boolean isClassroomFinished = connector.classroomDB.isClassroomFinished(classroomID);
		
		Person student = connector.personDB.getPerson(studentID);
		String studentEmail = student.getEmail();
		String personID = student.getPersonID();
		
		
		String sectionLeaderEmail = connector.studentDB.getSectionLeaderEmail(classroomID, personID);
		String seminaristEmail = connector.studentDB.getSeminaristEmail(classroomID, personID);
		String currentPersonEmail = currentPerson.getEmail();
		
		if(!isAdmin && !isLecturer && !sectionLeaderEmail.equals(currentPersonEmail) && !seminaristEmail.equals(currentPersonEmail) && !studentEmail.equals(currentPersonEmail)){
		    response.sendError(400, "Not Permitted At All");
		    return;
		}
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String deadlineWithReschedulings = "";
		if (a.getDeadline()!=null) deadlineWithReschedulings = format1.format(a.getDeadline());
		
		connector.studentAssignmentDB.addStudentAssignment(classroomID, personID, assignmentID, deadlineWithReschedulings );
		
		StudentAssignment studentAssignment = connector.studentAssignmentDB.getStudentAssignment(
				classroomID, personID, assignmentID);
	
		List<AssignmentComment> assignmentComments = connector.commentDB.
				getStudentAssignmentComments(studentAssignment.getStudentAssignmentId());
		List<AssignmentComment> assignmentStaffComments = connector.commentDB.
				getStudentAssignmentStaffComments(studentAssignment.getStudentAssignmentId());
		
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
			if ((isAdmin || isLecturer) && !isClassroomFinished) {
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
			String assignmentFileId = service.getAssignmentFileId(classroomID, a.getTitle());
			String htmlCode = generateAssignmentHTML(a, assignmentFileId);
			out.println(htmlCode);
		
	%>
	
		
		<div class = "ui section">
	<% 
	
		if(studentAssignment.getAssignmentGrade().equals("Not Graded")){
			out.println("<div class=\"ui big red label right grade\">" + studentAssignment.getAssignmentGrade() + "</div>");	
		}else{
			out.println("<div class=\"ui big green label right grade\">" + studentAssignment.getAssignmentGrade() + "</div>");
		}
	%>
		</div>

		
	
	<div class="panel panel-default">  
	
		<div class="panel-body"> 
		<div class = "ui section">
	<%
	
		if(isStudent && !isClassroomFinished){

			Date now = new Date();
			Date availableDate = studentAssignment.getDeadlineWithReschedulings();
			
			boolean canTurnIn = false;
			int mustUseReschedulings = 0;
			if(availableDate != null){

			
		
				int maxAvailableRes = currentClassroom.getNumberOfReschedulings() - 
						connector.classroomDB.reschedulingsUsed(studentID, classroomID) ;
				
				for(int i = 0; i <= maxAvailableRes; i++){
					availableDate = addDays(studentAssignment.getDeadlineWithReschedulings(), i*currentClassroom.getReschedulingLength());
					
					if(now.before(availableDate)){
						canTurnIn = true;
						mustUseReschedulings = i;
						break;
					}
				}
			
			}else canTurnIn = true;
			
			if(canTurnIn){
				
				
				%>
				<form action="TurnInAssignmentServlet" enctype="multipart/form-data" method="POST">
					<textarea style="display:none;" name="studentID"><%=studentID%></textarea>
					<textarea style="display:none;" name=<%=Classroom.ID_ATTRIBUTE_NAME%>><%=classroomID%></textarea>
					<textarea style="display:none;" name="assignmentID"><%=assignmentID%></textarea>
					
					<textarea style="display:none;" name="numReschedulings"><%=mustUseReschedulings%></textarea>
					
					<div>
						<label for="file" class="ui icon button button-margin"> <i
							class="file icon "></i> Choose Work File
						</label> <input type="file" id="file" name="file" size=30 
							style="display: none">
						</div>
		
						<input type="submit" class="ui blue button button-margin" value="Add">
				</form>	
				
				
					
				<%
			
			}else{
				
				%>

				<div class="ui big red label late">Late</div>
				
				<%
				
			}
			
			
		}	
		
		ArrayList<String> generatedHTML = service.getHtmlForStudentUploads(classroomID, a.getTitle(), studentEmail);
		
		out.println("<div class=\"ui middle aligned divided list\">");	
		for(int i = 0; i<generatedHTML.size(); i++){
			out.println("<div class=\"item\">");
			out.println(generatedHTML.get(i));
			out.println("</div>");
		}
		out.println("</div>");

/*			
			List<String> uploadedFiles = connector.studentAssignmentDB.
					getStudentSentFiles(assignment.getStudentAssignmentId());
			for(String s : uploadedFiles){
				out.println(" <a href=\"DownloadServlet?" + DownloadServlet.DOWNLOAD_PARAMETER 
							+ "=" + s+ "\">" + s + "</a>");
			}
*/			
		
	%>
	
	</div>	
	
	<div class = "ui section">
	
	<!-- GRADING -->
	<% if((isSectionLeader || isSeminarist) && !isClassroomFinished){ %>
				<form action="GiveGradeServlet" method="post">	
				<div class="ui selection dropdown">
				  <input type="hidden" name="newGrade" value="<%=studentAssignment.getAssignmentGrade()%>">
				  <i class="dropdown icon"></i>
				  <div class="default text"> <%= studentAssignment.getAssignmentGrade() %></div>
				  <div class="menu">
				  	<%
				  		List<String> allGrades = connector.assignmentGradeDB.getAllGrades();
				  		for(String grade : allGrades ){
				  		out.println(" <div class=\"item\" data-value=\"" + grade +"\">" +
				  				grade+"</div> ");
				  		}
				  	%>				  
				   </div>
				</div>

		<input type="hidden" name="studentID"value="<%=studentID%>">
		<input type="hidden" name="classroomId"value="<%=classroomID%>">
		<input type="hidden" name="assignmentID"value="<%=assignmentID%>">
		<input type="hidden" name="isSeminarist"value="<%=isSeminarist%>">
				
		<input type="submit" class="ui blue button" value="Set"></form>
	<script>
		$('.ui.dropdown').dropdown();
	</script>
	
	
	<% } %>
	
	</div>
	<!-- END OF GRADING -->
	
	
	
	
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
		
		
		
		<div class = "ui section ">
		
		<!-- BASIC COMMENTS -->
		<div class="ui comments" id="ALL_COMMENTS">
		 
		 	  <%
			  	for (AssignmentComment ac : assignmentComments){
			  		String commentHtml = generateCommentHTML(ac, connector);
			  		out.println(commentHtml);
			  	}
			  %>
			  
		  </div>
		  
		  <%
		  	if (!isClassroomFinished) {
		  %>
		  
		  <form class="ui reply form" id = "COMMENT_ADDING_FORM">		
		 	    <div class="field">
			      <textarea id="COMMENT_TEXT"></textarea>
			    </div>
				
				<div class="ui primary submit labeled icon button" id = "ADD_COMMENT_BUTTON">
					<textarea style="display:none" id=PERSON_ID><%=currentPerson.getPersonID()%></textarea>
					<textarea style="display:none" id=PERSON_IMG_URL><%=currentPerson.getPersonImgUrl()%></textarea>
					<textarea style="display:none" id=PERSON_NAME><%=currentPerson.getName()%></textarea>
					<textarea style="display:none" id=STUDENT_ASSIGNMENT_ID><%=studentAssignment.getStudentAssignmentId()%></textarea>
					<i class="icon edit"></i> Add Comment
			  	</div>
		  </form>
		  
		  <%
		  	}
		  %>
		  
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
		  
		  <%
		 	if (!isClassroomFinished) {	
		  %>
		  
		  <form class="ui reply form" id = "STAFF_COMMENT_ADDING_FORM">		
		 	    <div class="field">
			      <textarea id="STAFF_COMMENT_TEXT"></textarea>
			    </div>
					
				<div class="ui primary submit labeled icon button" id = "ADD_STAFF_COMMENT_BUTTON">
					<textarea style="display:none" id=PERSON_ID><%=currentPerson.getPersonID()%></textarea>
					<textarea style="display:none" id=PERSON_IMG_URL><%=currentPerson.getPersonImgUrl()%></textarea>
					<textarea style="display:none" id=PERSON_NAME><%=currentPerson.getName()%></textarea>
					<textarea style="display:none" id=STUDENT_ASSIGNMENT_ID><%=studentAssignment.getStudentAssignmentId()%></textarea>
					<i class="icon edit"></i> Add Staff Comment
			  	</div>
		  </form>  		  
		  
		  <%
		 	}
		  %>
  		  
  		  <!-- END OF STAFF COMMENTS -->
		  <%}%>
		  
		  </div>
		  
	<!-- END OF COMMENTS -->
	
		</div>
	</div>
	
	
	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	
	<script type="text/javascript" src='js/studentsOneAssignmentComment.js'></script>
	<script type="text/javascript" src='js/studentsOneAssignmentMenu.js'></script>
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