<%@page import="EditingServlets.EditStatusConstants"%>
<%@page import="defPackage.Classroom"%>
<%@page import="defPackage.DBConnection"%>


<%@page import="defPackage.Person"%>
<%@page import="defPackage.Seminar"%>
<%@page import="defPackage.Section"%>
<%@page import="defPackage.ActiveSeminar"%>
<%@page import="java.util.List"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<title>Edit</title>
</head>
<body>
	<%
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		DBConnection connector = (DBConnection) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.getClassroom(classroomId);

		String status = request.getParameter(EditStatusConstants.STATUS);

		List<Person> lecturers = connector.getLecturers(classroomId);
		List<Person> seminarists = connector.getSeminarists(classroomId);
		List<Person> sectionLeaders = connector.getSectionLeaders(classroomId);
		List<Person> students = connector.getStudents(classroomId);
		System.out.println("roles downloaded successfully!");

		List<Seminar> seminars = connector.getSeminars(classroomId);
		List<ActiveSeminar> activeSeminars = connector.getActiveSeminars(classroomId);
		List<Section> sections = connector.getSections(classroomId);
		System.out.println("seminars, active seminars and sections downloaded successfully!");
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
				href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>Stream</a></li>
			<li><a
				href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>About</a></li>
			<li><a
				href=<%="formation.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>Formation</a></li>
			<li class="active"><a
				href=<%="edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>Edit</a></li>
		</ul>
	</div>
	</nav>
	
	
	
	
	<input class="awesomplete" list="students" />
	<datalist id="students">
	<%
		for(Person person : students){
			out.println("<option>" + person.getEmail() + "</option>");
		}
	%>
	</datalist>
	
	
	<h1>Seminar Groups:</h1>
    <div class='seminar-boxes'>
        <%
            for(Seminar seminar : seminars){
                out.println("<div class='seminar-box'>");
               
                out.println("<h1>Seminarist name here...  </h1> </br>");
               
                out.println("<h2>Students:</h2>");
                out.println("<ul class='seminar-students'>");
                
                for (int i=1; i<=seminar.hashCode()%15; i++)
                out.println("<li>student1</li>");
                out.println("<li>student2</li>");
                out.println("<li>student3</li>");
                out.println("</ul>");
               
                out.println("</div>");
            }
        %>
    </div>
	
	

	<div class='-groupsedit'>
	
	
		<div class='quick-edit'>


			<div class='display-quick-edit-button'>Delete Persons</div>

			<ul class='group-quick-edit'>
				<li>
					<!-- deletes person from the classroom -->
					<form
					action=<%="DeletePersonServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>
					method="post">
					<h4>Delete person by Email:</h4>
					<p>Enter Email address of the person you want to delete from this
						classroom</p>
			
					<input type="text" name="email" placeholder="Email"> <input
						type="submit" value="Delete">
			
					<%
						if (status != null) {
							if (status.equals(EditStatusConstants.DEL_PERSON_ACC))
								out.println(EditStatusConstants.ACCEPT);
							if (status.equals(EditStatusConstants.DEL_PERSON_REJ))
								out.println(EditStatusConstants.REJECT);
						}
					%>
					
				</form>
	
				</li>
				
			</ul>

		</div>		




	</div>
	
	
	
	
	
	
	
	<div class='person-groups'>

		<!-- LECTURERS -->
		<div class='group-lecturers'>

			<div class='display-persons-button'>Lecturers</div>

			<ul class='group-persons'>

				<li>
					<!-- adds new lecturer to the class -->
					<form
						action=<%="AddNewLecturerServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>
						method="post">
						<h4>Add New Lecturer:</h4>
						<p>Enter Email address, name and surname of the lecturer</p>

						<input type="text" name="email" placeholder="Email">
						<input type="submit" value="Add">

						<%
							if (status != null) {
								if (status.equals(EditStatusConstants.ADD_NEW_LECTURER_ACC))
									out.println(EditStatusConstants.ACCEPT);
								if (status.equals(EditStatusConstants.ADD_NEW_LECTURER_REJ))
									out.println(EditStatusConstants.REJECT);
							}
						%>

						
					</form>
				</li>
				
				
				<li><h4>Existing Lecturers:</h4> </li>
				<%
					if (lecturers.isEmpty()) {
						out.println("<li>" + "Empty" + "</li>");
					} else {
						for (Person lecturer : lecturers) {
							out.println("<li>" + lecturer.getEmail() + "</li>");
							
						}
					}
				%>
				
			</ul>		
		</div>
		

		<!-- SEMINARISTS -->
		<div class='group-seminarists'>

			<div class='display-persons-button'>Seminarists</div>

			<ul class='group-persons'>
				<li> 
				
					<!-- adds new seminarist to the class -->
					<form
						action=<%="AddNewSeminaristServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>
						method="post">
						<h4>Add New Seminarist:</h4>
						<p>Enter Email address, name and surname of the seminarist</p>
				
						<input type="text" name="email" placeholder="Email"> 
						<input type="submit" value="Add">
				
						<%
							if (status != null) {
								if (status.equals(EditStatusConstants.ADD_NEW_SEMINARIST_ACC))
									out.println(EditStatusConstants.ACCEPT);
								if (status.equals(EditStatusConstants.ADD_NEW_SEMINARIST_REJ))
									out.println(EditStatusConstants.REJECT);
							}
						%>
						
						
					</form>
					
				</li>
				
				<li> <h4>Existing Seminarists:</h4> </li>
				
				
				<%
					if (seminarists.isEmpty()) {
						out.println("<li>" + "Empty" + "</li>");
					} else {
						for (Person seminarist : seminarists) {
							out.println("<li>" + seminarist.getEmail() + "</li>");
						}
					}
				%>
			</ul>
		</div>


		<!-- SECTION LEADERS -->
		<div class='group-section-leaders'>

			<div class='display-persons-button'>Section Leaders</div>

			<ul class='group-persons'>
			
				<li>
				
					<!-- adds new section leader to the class -->
					<form
						action=<%="AddNewSectionLeaderServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>
						method="post">
						<h4>Add New Section Leader:</h4>
						<p>Enter Email address, name and surname of the section leader</p>
				
						 <input type="text" name="email" placeholder="Email"> 
						 <input type="submit" value="Add">
				
						<%
							if (status != null) {
								if (status.equals(EditStatusConstants.ADD_NEW_SECTION_LEADER_ACC))
									out.println(EditStatusConstants.ACCEPT);
								if (status.equals(EditStatusConstants.ADD_NEW_SECTION_LEADER_REJ))
									out.println(EditStatusConstants.REJECT);
							}
						%>
				
					</form>
				
				</li>
				
				<li> <h4>Existing Section Leaders:</h4> </li>
			
				<%
					if (sectionLeaders.isEmpty()) {
						out.println("<li>" + "Empty" + "</li>");
					} else {
						for (Person sectionLeader : sectionLeaders) {
							out.println("<li>" + sectionLeader.getEmail() + "</li>");
						}
					}
				%>
			</ul>
		</div>

		<!-- STUDENTS -->
		<div class='group-students'>

			<div class='display-persons-button'>Students</div>

			<ul class='group-persons'>
			
				<li>
						<!-- adds new student to the class -->
						<form
							action=<%="AddNewStudentServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>
							method="post">
							<h4>Add New Student:</h4>
							<p>Enter Email address, name and surname of the student</p>
							
							<input type="text" name="email" placeholder="Email"> 
							<input type="submit" value="Add">
					
							<%
								if (status != null) {
									if (status.equals(EditStatusConstants.ADD_NEW_STUDENT_ACC))
										out.println(EditStatusConstants.ACCEPT);
									if (status.equals(EditStatusConstants.ADD_NEW_STUDENT_REJ))
										out.println(EditStatusConstants.REJECT);
								}
							%>
					
							
						</form>
				
				</li>
				
				<li><h4>Existing Students:</h4> </li>
				
				<%
					if (students.isEmpty()) {
						out.println("<li>" + "Empty" + "</li>");
					} else {
						for (Person student : students) {
							out.println("<li>" + student.getEmail() + "</li>");
						}
					}
				%>
			</ul>
		</div>




	<div class='grenevt-groups'>

		<!-- SEMINARS -->
		<div class='grevent-seminars'>

			<div class='display-grevent-button'>Seminars</div>

			<ul class='grevent-items'>
			
				<li>
				
					<!-- adds new seminar -->
					<form
						action=<%="AddNewSeminarServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>
						method="post">
						<h4>Add New Seminar:</h4>
						<p>Enter name of the seminar you want to add (name must be unique)
						</p>
				
						<input type="text" name="name" placeholder="Seminar Name"> <input
							type="submit" value="Add">
				
						<%
							if (status != null) {
								if (status.equals(EditStatusConstants.ADD_NEW_SEMINAR_ACC))
									out.println(EditStatusConstants.ACCEPT);
								if (status.equals(EditStatusConstants.ADD_NEW_SEMINAR_REJ))
									out.println(EditStatusConstants.REJECT);
							}
						%>
				
					</form>
				
				</li>
				
				
				<li>
				
				<!-- deletes existing seminar -->
					<form
						action=<%="DeleteSeminarServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>
						method="post">
						<h4>Delete Seminar:</h4>
						<p>Enter name of the seminar you want to delete
						</p>
				
						<input type="text" name="name" placeholder="Seminar Name"> <input
							type="submit" value="Delete">
				
						<%
							if (status != null) {
								if (status.equals(EditStatusConstants.DEL_SEMINAR_ACC))
									out.println(EditStatusConstants.ACCEPT);
								if (status.equals(EditStatusConstants.DEL_SEMINAR_REJ))
									out.println(EditStatusConstants.REJECT);
							}
						%>
				
					</form>
				
				</li>
				
				<li>
				
					<!-- adds existing seminarist to seminar -->
					<form action=<%="AddSeminaristToSeminarServlet?" 
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>  method="post">
						<h4>Add Seminarist To Seminar:</h4>
						<p>Enter name of the seminar and email of the seminarist</p>
						
						
						<input type="text" name="seminarName" placeholder = "Seminar Name">
						
						<input type="text" name="seminaristEmail" placeholder = "EMail">
						
						<input type="submit" value ="Add">
						<%
							if(status != null){
								if(status.equals( EditStatusConstants.ADD_SEMINARIST_TO_SEMINAR_ACC))
									out.println(EditStatusConstants.ACCEPT);
								if(status.equals( EditStatusConstants.ADD_SEMINARIST_TO_SEMINAR_REJ)) 
									out.println(EditStatusConstants.REJECT);
							}
						%>	
						
					</form><br>
				
				</li>
				
				<li>
											
					<!-- adds existing student to seminar -->
					<form action=<%="AddStudentToSeminarServlet?" 
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>  method="post">
						<h4>Add Student To Seminar:</h4>
						<p>Enter name of the seminar and email of the student</p>
						
						
						<input type="text" name="seminarName" placeholder = "Seminar Name">
						
						<input type="text" name="studentEmail" placeholder = "EMail">
						
						<input type="submit" value ="Add">
								
						<%
							if(status != null){
								if(status.equals( EditStatusConstants.ADD_STUDENT_TO_SEMINAR_ACC)) 
									out.println(EditStatusConstants.ACCEPT);
								if(status.equals( EditStatusConstants.ADD_STUDENT_TO_SEMINAR_REJ)) 
									out.println(EditStatusConstants.REJECT);
							}
						%>	
						
					</form>
	
				</li>
				
				<li> <h4>Existing Seminars:</h4> </li>
				
				<%
					if (seminars.isEmpty()) {
						out.println("<li>" + "Empty" + "</li>");
					} else {
						for (Seminar seminar : seminars) {
							out.println("<li>" + seminar.getSeminarName() + "<li>");
						}
					}
				%>
			</ul>
		</div>


		<!-- SECTIONS -->
		<div class='grevent-sections'>

			<div class='display-grevent-button'>Sections</div>

			<ul class='grevent-items'>
				<li>
				
					<!-- adds new section -->
					<form
						action=<%="AddNewSectionServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>
						method="post">
						<h4>Add New Section:</h4>
						<p>Enter name of the section you want to add (name must be unique)
						</p>
				
						<input type="text" name="name" placeholder="Section Name"> <input
							type="submit" value="Add">
				
						<%
							if (status != null) {
								if (status.equals(EditStatusConstants.ADD_NEW_SECTION_ACC))
									out.println(EditStatusConstants.ACCEPT);
								if (status.equals(EditStatusConstants.ADD_NEW_SECTION_REJ))
									out.println(EditStatusConstants.REJECT);
							}
						%>
						
					</form>
				
				</li>
				
				<li>
				
					<!-- deletes existing section -->
					<form
						action=<%="DeleteSectionServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>
						method="post">
						<h4>Delete Section:</h4>
						<p>Enter name of the section you want to delete
						</p>
				
						<input type="text" name="name" placeholder="Section Name"> <input
							type="submit" value="Delete">
				
						<%
							if (status != null) {
								if (status.equals(EditStatusConstants.DEL_SECTION_ACC))
									out.println(EditStatusConstants.ACCEPT);
								if (status.equals(EditStatusConstants.DEL_SECTION_REJ))
									out.println(EditStatusConstants.REJECT);
							}
						%>
						
					</form>
				
				</li>
				
				<li>
				
					<!-- adds existing section leader to section -->
					<form action=<%="AddSectionLeaderToSectionServlet?" 
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>  method="post">
						<h4>Add Section Leader To Section:</h4>
						<p>Enter name of the section and email of the section leader</p>
						
						
						<input type="text" name="sectionName" placeholder = "Section Name">
						
						<input type="text" name="sectionLeaderEmail" placeholder = "EMail">
						
						<input type="submit" value ="Add">
						
						<%
							if(status != null){
								if(status.equals( EditStatusConstants.ADD_SECTION_LEADER_TO_SECTION_ACC)) 
									out.println(EditStatusConstants.ACCEPT);
								if(status.equals( EditStatusConstants.ADD_SECTION_LEADER_TO_SECTION_REJ)) 
									out.println(EditStatusConstants.REJECT);
							}
						%>	
					
					</form>	
				
				</li>
				
				<li>
					
					<!-- adds existing student to section -->
					<form action=<%="AddStudentToSectionServlet?" 
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>  method="post">
						<h4>Add Student To Section:</h4>
						<p>Enter name of the section and email of the student</p>
						
						
						<input type="text" name="sectionName" placeholder = "Section Name">
						
						<input type="text" name="studentEmail" placeholder = "EMail">
						
						<input type="submit" value ="Add">
						
						<%
							if(status != null){
								if(status.equals( EditStatusConstants.ADD_STUDENT_TO_SECTION_ACC))
									out.println(EditStatusConstants.ACCEPT);
								if(status.equals( EditStatusConstants.ADD_STUDENT_TO_SECTION_REJ)) 
									out.println(EditStatusConstants.REJECT);
							}
						%>	
						
					</form>	
				
				</li>
			
				<li> <h4>Existing Sections:</h4> </li>
				
				<%
					if (sections.isEmpty()) {
						out.println("<li>" + "Empty" + "</li>");
					} else {
						for (Section section : sections) {
							out.println("<li>" + section.getSectionName() + "<li>");
						}
					}
				%>
			</ul>
		</div>


		<!-- ACTIVE SEMINARS -->
		<div class='grevent-active-seminars'>

			<div class='display-grevent-button'>Active Seminars</div>

			<ul class='grevent-items'>
			
				<li>
				
					<!-- adds new active seminar  -->
					<form action=<%="AddNewActiveSeminarServlet?"
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId %>  method="post">
						<h4>Add Active Seminar:</h4>
						<p>Enter name of the active seminar, name of the seminar group, 
						location and time of the active seminar </p>
							
						<input type="text" name="activeSeminarName" placeholder = "Active Seminar Name">
						
						<input type="text" name="seminarName" placeholder = "Seminar Name">
						
						<input type="text" name="seminarLocation" placeholder = "Location">
						
						<input type="text" name="seminarTime" placeholder = "Time">
						
						<input type="submit" value ="Add">
						
						<%
							if(status != null){
								if(status.equals( EditStatusConstants.ADD_NEW_ACTIVE_SEMINAR_ACC)) 
									out.println(EditStatusConstants.ACCEPT);
								if(status.equals( EditStatusConstants.ADD_NEW_ACTIVE_SEMINAR_REJ)) 
									out.println(EditStatusConstants.REJECT);
							}
						%>	
						
					
					</form>
								
				</li>
				
				<li><h4>Existing Active Seminars:</h4> </li>
			
				<%
					if (activeSeminars.isEmpty()) {
						out.println("<li>" + "Empty" + "</li>");
					} else {
						for (ActiveSeminar activeSeminar : activeSeminars) {
							out.println("<li>" + activeSeminar.getActiveSeminarName() + ".  " + " Location: "
									+ activeSeminar.getActiveSeminarLocation() + ".  " + " Time: "
									+ activeSeminar.getActiveSeminarTime() + "<li>");
						}
					}
				%>
			</ul>
		</div>

		


	</div>

	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	<script type="text/javascript" src='js/formationJS.js'></script>





</body>
</html>