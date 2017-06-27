<%@page import="EditingServlets.EditStatusConstants"%>
<%@page import="defPackage.Classroom"%>
<%@page import="database.AllConnections"%>


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
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/multiInput.css">
<title>Edit</title>
<style>
	li {
		display: table;
	}
</style>
</head>
<body>
	<%
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomID);
		String status = request.getParameter(EditStatusConstants.STATUS);
		List<Person> lecturers = connector.lecturerDB.getLecturers(classroomID);
		List<Person> seminarists = connector.seminaristDB.getSeminarists(classroomID);
		List<Person> sectionLeaders = connector.sectionLeaderDB.getSectionLeaders(classroomID);
		List<Person> students = connector.studentDB.getStudents(classroomID);
		System.out.println("roles downloaded successfully!");
		
		
		System.out.println("seminars and sections downloaded successfully!");
		List<Seminar> seminars = connector.seminarDB.getSeminars(classroomID);
		List<Section> sections = connector.sectionDB.getSections(classroomID);
		
		List<Person> others = connector.studentDB.getStudentsWithoutSeminar(classroomID);
		List<Person> srehto = connector.studentDB.getStudentsWithoutSection(classroomID);
		
		System.out.println("seminars, active seminars and sections downloaded successfully!");
		
		System.out.println("OTHERS ARE:");
		for (Person p : others){
			System.out.println(p.getEmail());
		}
		
		System.out.println("SREHTO ARE:");
		for (Person p : srehto){
			System.out.println(p.getEmail());
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
			<li><a
				href=<%="viewSectionsAndSeminars.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
				Sections And Seminars</a></li>
			<li class="active"><a
				href=<%="edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Edit</a></li>
			<li><a
				href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>About</a></li>	
			<li><a
				href=<%="assignments.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Assignments</a></li>
			<li><a
				href=<%="settings.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Settings</a></li>
	
		</ul>
	</div>
	</nav>
	
	
	<!--   

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
    
    -->
	
	

	<div class='-groupsedit'>
	
	
		<div class='quick-edit'>


			<div class='display-quick-edit-button'>Delete Persons</div>

			<ul class='group-quick-edit'>
				<li>
					<!-- deletes person from the classroom -->
					
					
					<h4>Delete person by Email:</h4>
					<p>Enter Email address of the person you want to delete from this
						classroom</p>
			
					<div class="emails">
  								<input type="text" value="" placeholder="Add Email" />
  								<button class="personAddButton btn btn-success"> Submit </button>
  								<input type="hidden" value="DeletePersonServlet">
  								<input type="hidden" value = <%= classroomID %> >
							</div>
			
					<%
						if (status != null) {
							if (status.equals(EditStatusConstants.DEL_PERSON_ACC))
								out.println(EditStatusConstants.ACCEPT);
							if (status.equals(EditStatusConstants.DEL_PERSON_REJ))
								out.println(EditStatusConstants.REJECT);
						}
					%>
					
				
	
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
					
						<h4>Add New Lecturer:</h4>
						<p>Enter Email address, name and surname of the lecturer</p>

						<div class="emails">
  								<input type="text" value="" placeholder="Add Email" />
  								<button class="personAddButton btn btn-success"> Submit </button>
  								<input type="hidden" value="AddNewLecturerServlet">
  								<input type="hidden" value = <%= classroomID %> >
							</div>

						<%
							if (status != null) {
								if (status.equals(EditStatusConstants.ADD_NEW_LECTURER_ACC))
									out.println(EditStatusConstants.ACCEPT);
								if (status.equals(EditStatusConstants.ADD_NEW_LECTURER_REJ))
									out.println(EditStatusConstants.REJECT);
							}
						%>

						
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
					
						
						<h4>Add New Seminarist:</h4>
						<p>Enter Email address, name and surname of the seminarist</p>
				
						<div class="emails">
  								<input type="text" value="" placeholder="Add Email" />
  								<button class="personAddButton btn btn-success"> Submit </button>
  								<input type="hidden" value="AddNewSeminaristServlet">
  								<input type="hidden" value = <%= classroomID %> >
							</div>
				
						<%
							if (status != null) {
								if (status.equals(EditStatusConstants.ADD_NEW_SEMINARIST_ACC))
									out.println(EditStatusConstants.ACCEPT);
								if (status.equals(EditStatusConstants.ADD_NEW_SEMINARIST_REJ))
									out.println(EditStatusConstants.REJECT);
							}
						%>
						
						
					
					
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
					
						
						<h4>Add New Section Leader:</h4>
						<p>Enter Email address, name and surname of the section leader</p>
						
						<div class="emails">
  								<input type="text" value="" placeholder="Add Email" />
  								<button class="personAddButton btn btn-success"> Submit </button>
  								<input type="hidden" value="AddNewSectionLeaderServlet">
  								<input type="hidden" value = <%= classroomID %> >
							</div>
				
						<%
							if (status != null) {
								if (status.equals(EditStatusConstants.ADD_NEW_SECTION_LEADER_ACC))
									out.println(EditStatusConstants.ACCEPT);
								if (status.equals(EditStatusConstants.ADD_NEW_SECTION_LEADER_REJ))
									out.println(EditStatusConstants.REJECT);
							}
						%>
				
				
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
						
							
							<h4>Add New Student:</h4>
							<p>Enter Email address, name and surname of the student</p>
							
							<div class="emails">
  								<input type="text" value="" placeholder="Add Email" />
  								<button class="personAddButton btn btn-success"> Submit </button>
  								<input type="hidden" value="AddNewStudentServlet">
  								<input type="hidden" value = <%= classroomID %> >
							</div>
					
							<%
								if (status != null) {
									if (status.equals(EditStatusConstants.ADD_NEW_STUDENT_ACC))
										out.println(EditStatusConstants.ACCEPT);
									if (status.equals(EditStatusConstants.ADD_NEW_STUDENT_REJ))
										out.println(EditStatusConstants.REJECT);
								}
							%>
					
							
						
				
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
						action=<%="AddNewSeminarServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>
						method="post">
						<h4>Add New Seminar:</h4>
						<p>Enter name of the seminar you want to add (name must be unique)
						</p>
				
						<input type="submit" value="AddSeminar">
				
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
						action=<%="DeleteSeminarServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>
						method="post">
						<h4>Delete Seminar:</h4>
					
						<input	type="submit" value="DeleteSeminar">
				
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
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID %>  method="post">
						<h4>Add Seminarist To Seminar:</h4>
						<p>Enter index of the seminar and email of the seminarist</p>
						
						
						<input type="text" name="seminarN" placeholder = "Seminar N">
						
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
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID %>  method="post">
						<h4>Add Student To Seminar:</h4>
						<p>Enter index of the seminar and email of the student</p>
						
						
						<input type="text" name="seminarN" placeholder = "Seminar N">
						
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
							out.println("<li>" + seminar.getSeminarN() + "<li>");
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
						action=<%="AddNewSectionServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>
						method="post">
						<h4>Add New Section:</h4>
						<p>Enter name of the section you want to add (name must be unique)
						</p>
				
						<input type="submit" value="AddSection">
				
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
						action=<%="DeleteSectionServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>
						method="post">
						<h4>Delete Section:</h4>
						
	
						<input type="submit" value="DeleteSection">
				
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
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID %>  method="post">
						<h4>Add Section Leader To Section:</h4>
						<p>Enter name of the section and email of the section leader</p>
						
						
						<input type="text" name="sectionN" placeholder = "Section N">
						
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
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID %>  method="post">
						<h4>Add Student To Section:</h4>
						<p>Enter index of the section and email of the student</p>
						
						<input type="text" name="sectionN" placeholder = "Section N">
						
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
							out.println("<li>" + section.getSectionN() + "<li>");
						}
					}
				%>
			</ul>
		</div>


	</div>

	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	<script type="text/javascript" src='js/formationJS.js'></script>
	<script type="text/javascript" src='js/multiInput.js'></script>




</body>
</html>