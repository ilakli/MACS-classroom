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
<link rel="stylesheet" href="css/sectionsAndSeminars.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<title>Edit Sections And Seminars</title>
</head>
<body>
	<%
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomID);

		String status = request.getParameter(EditStatusConstants.STATUS);


		
		System.out.println("seminars and sections downloaded successfully!");

		List<Seminar> seminars = connector.seminarDB.getSeminars(classroomID);

		List<Section> sections = connector.sectionDB.getSections(classroomID);
		
		List<Person> studentsWithoutSeminar = connector.studentDB.getStudentsWithoutSeminar(classroomID);
		List<Person> studentsWithoutSection = connector.studentDB.getStudentsWithoutSection(classroomID);
		
		System.out.println("seminars, active seminars and sections downloaded successfully!");
		
		System.out.println("studentsWithoutSeminar ARE:");
		for (Person p : studentsWithoutSeminar){
			System.out.println(p.getEmail());
		}
		
		System.out.println("studentsWithoutSection ARE:");
		for (Person p : studentsWithoutSection){
			System.out.println(p.getEmail());
		}
		
		

	%>
	<input type="hidden" value = <%= classroomID %> id = "classroom-id" >


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
			<li><a
				href=<%="edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Edit</a></li>
			<li><a
				href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>About</a></li>	
			<li><a
				href=<%="assignments.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Assignments</a></li>
			<li><a
				href=<%="settings.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Settings</a></li>
			<li class="active"><a
				href=<%="editSectionsAndSeminars.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
				Edit Sections And Seminars</a></li>
			<li>
		</ul>
	</div>
	</nav>
	
	
	<!-- 	
	<form action=<%="EditSectionsAndSeminarsServlet?" 
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID %>  method="post">
    	<input type="submit" value="Edit" />
	</form>
	
	
	
	-->
	

    <div class='seminar-boxes' >
		<h1 style = "background-color: #dfdae0; text-align: center;"> Seminar Groups</h1>
       
       	<form action=<%="ManualDistributionToSeminarsServlet?" 
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID %>  method="post">
    		<input type="submit" value="Manually Distribute Students To Seminars" />
		</form>
		
		<form action=<%="AutoDistributionToSeminarsServlet?" 
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID %>  method="post">
    		<input type="submit" value="Automatically Distribute Students To Seminars" />
		</form>
        
        
        <%
            for(Seminar seminar : seminars){
            	
        %>
            	
              	<div class='seminar-box'>
               	<p style = " margin: 10px 10px;">Seminar <%=seminar.getSeminarN()%></p>
                <hr>
                
                <%
                Person p = seminar.getSeminarist();
                if(p!= null) {
                	String seminaristName = p.getName() + " " + p.getSurname();
                	
                	%>
                	
                	<form style = " margin: 10px 10px; "
                	action=<%="RemoveSeminaristFromSeminarServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID
					 +"&seminarN="+ seminar.getSeminarN()%> method="post">
					 	<p><%=seminaristName %></p>
					 	<input type="submit" value ="Remove Seminarist">
				 	</form>
                	
                	<hr>
                	<%
                }else{
                	%>
                	<form style = " margin: 10px 10px; "
                    action=<%="AddSeminaristToSeminarServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID
        			+"&seminarN="+ Integer.toString(seminar.getSeminarN())%> method="post">
        					 	
        				<input type="submit" value ="Set Seminarist">
        			</form>
                        	
                    <hr>
                	<%
                }
                
                
                
                
                
                List<Person> seminarStudents = seminar.getSeminarStudents();

                %>
                
                <!-- this part was taken from https://www.w3schools.com/bootstrap/bootstrap_modal.asp -->
				<div class="container">
					<!-- Trigger the modal with a button -->
					<button type="button" data-toggle="modal" data-target="#myModal">Add
						Students</button>
					<!-- Modal -->
					<div class="modal fade" id="myModal" role="dialog">
						<div class="modal-dialog">
							<!-- Modal content-->
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">Add Students</h4>
								</div>
								<div class="modal-body">
									<div class="emails">
										<input type="text" value="" placeholder="Add Email" />
										<button class="lecturerAddButton btn btn-success">Submit</button>
										<input type="hidden" value="AddStudentToSectionServlet">
									</div>
			
									<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
									<script type="text/javascript" src='js/multiInput.js'></script>
									<script type="text/javascript" src='js/lecturerAdd.js'></script>
			
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			                
                
                <ul class='seminar-students' >
                <form action=<%="RemoveStudentsFromSeminarServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID
				+"&seminarN="+ seminar.getSeminarN()%> method="post">
  
  
                <%
                for (Person student : seminarStudents){
                	%>
                	
                	<li><input type="checkbox" name="studentsEmails" value=<%=student.getEmail() %>>
                	<%
                	out.println( student.getName() + " " + student.getSurname()+"</li>");
                }

				
                if(!seminarStudents.isEmpty()){
                	
                %>
                	<li><input type="submit" value="Remove Students"></ul>
                <%
                }
				%>
				
				</form>
				</ul>
	</div>
                
	<%
            }
        
        	
     %>
       
       			<div class='seminar-box'>
		        <p style = " margin: 10px 10px;">Students Without Seminar </p>
		        <hr>
		        <p>  </p>
		        <hr>
		        <ul class='seminar-students' >
		        <%
		        for (Person student : studentsWithoutSeminar){
		        	out.println("<li>"+ student.getName() + " " + student.getSurname() +"</li>");
		        	
		        }
		
		        %>
		        </ul>
		    	</div>
		        
        
        
        
       
    </div>
	
	
	
	<div class='seminar-boxes'>
   		<h1 style = "background-color: #dfdae0; text-align: center; "> Sections</h1>
       
       	<form action=<%="ManualDistributionToSectionsServlet?" 
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID %>  method="post">
    		<input type="submit" value="Manually Distribute Students To Sections" />
		</form>
		
		<form action=<%="AutoDistributionToSectionsServlet?" 
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID %>  method="post">
    		<input type="submit" value="Automatically Distribute Students To Sections" />
		</form>
        <%
        	
            for(Section section : sections){
            	%>
            	<div class='seminar-box'>
               	<p style = " margin: 10px 10px;">Section <%=section.getSectionN()%></p>
                <hr>
                
                <%
                Person p = section.getSectionLeader();
                
                if(p!= null) {
                	String sectionLeaderName = p.getName() + " " + p.getSurname();
                	
                	%>
                	
                	<form style = " margin: 10px 10px;" 
                	action=<%="RemoveSectionLeaderFromSectionServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID
					 +"&sectionN="+ section.getSectionN()%> method="post">
					 	<p><%=sectionLeaderName %></p>
					 	<input type="submit" value ="Remove Section Leader">
				 	</form>
                	
                	<hr>
                	<%
                }else{
                	%>
                	<form style = " margin: 10px 10px; "
                    action=<%="AddSectionLeaderToSectionServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID
        			+"&sectionN="+ section.getSectionN()%> method="post">
        					 	
        			<input type="submit" value ="Set Section Leader">
        			</form>
                        	
                     <hr>
                	<%
                }
                
           
                
                
                List<Person> sectionStudents = section.getSectionStudents();

                %>
                

                
                
                
                <!-- this part was taken from https://www.w3schools.com/bootstrap/bootstrap_modal.asp -->
				<div class="container">
					<!-- Trigger the modal with a button -->
					<button type="button" data-toggle="modal" data-target="#<%=section.getSectionN()%>">Add
						Students</button>
					<!-- Modal -->
					<div class="modal fade" id="<%= section.getSectionN() %>" role="dialog">
						<div class="modal-dialog">
							<!-- Modal content-->
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">Add Students</h4>
								</div>
								<div class="modal-body">
									<div class="emails">
										<input type="text" value="" placeholder="Add Email" />
										<button class="studentAddSectionButton btn btn-success">Submit</button>
										<input type="hidden" value=<%out.print("\"" + section.getSectionN() + "\""); %>>
										<input type="hidden" value="AddStudentToSectionServlet">
									</div>
								
											
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								</div>
							</div>
						</div>
					</div>
				</div>
                
                
                
              	<ul class='seminar-students'>
                <form action=<%="RemoveStudentsFromSectionServlet?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID
                +"&sectionN="+ section.getSectionN()%> method="post">
  
  
                <%
                for (Person student : sectionStudents){
                %>
                	
                	<li><input type="checkbox" name="studentsEmails" value=<%=student.getEmail() %>>
                	<%
                	out.println( student.getName() + " " + student.getSurname()+"</li>");
                }
                
                if(!sectionStudents.isEmpty()){
                	
                    %>
                    	<li><input type="submit" value="Remove Students"></ul>
                    <%
                    }
    				%>
    				
    				</form>
    				</ul>
    	</div>
                    
    	<%
     
            }
        
        %>
	        <div class='seminar-box' >
	        <p style = " margin: 10px 10px;  ">Students Without Section </p>
	        <hr>
	         <p style = " margin: 10px 10px;  ">  </p>
	        <hr>
	        <ul class='seminar-students' >
    
		    <%    
		        for (Person student : studentsWithoutSection){
		        	out.println("<li>"+ student.getName() + " " + student.getSurname() +"</li>");
		        	
		        }
		    %>
			</ul>
		</div>
				
       		 
    </div>

	

	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
									<script type="text/javascript" src='js/multiInput.js'></script>
									<script type="text/javascript" src='js/studentAddSection.js'></script>


</body>
</html>