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
<link rel="stylesheet" href="css/sectionsAndSeminars.css">
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<title>Sections And Seminars</title>
</head>
<body>
	<%
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomID);

		String status = request.getParameter(EditStatusConstants.STATUS);
		
		Person currentPerson = (Person)request.getSession().getAttribute("currentPerson");
		boolean isAdmin = connector.personDB.isAdmin(currentPerson);
		boolean isStudent = currentClassroom.classroomStudentExists(currentPerson.getEmail());
		boolean isSectionLeader = currentClassroom.classroomSectionLeaderExists(currentPerson.getEmail());
		boolean isSeminarist = currentClassroom.classroomSeminaristExists(currentPerson.getEmail());
		boolean isLecturer = currentClassroom.classroomLecturerExists(currentPerson.getEmail());

		
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
			<li class="active"><a
				href=<%="viewSectionsAndSeminars.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
				Sections And Seminars</a></li>
			<%}%>
			
			<%if (isAdmin || isLecturer){%>
			<li><a
				href=<%="edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Edit</a></li>
			<%}%>
			
			<li><a
				href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>About</a></li>	
			
			<li><a
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
	
	
	<!-- 	
	<form action=<%="EditSectionsAndSeminarsServlet?" 
					+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID %>  method="post">
    	<input type="submit" value="Edit" />
	</form>
	
	
	
	-->
	

    <div class='seminar-boxes' >
		<h1 style = "background-color: #dfdae0; text-align: center;"> Seminar Groups</h1>
       
        <%
            for(Seminar seminar : seminars){
            	%>
                <div class='seminar-box'>
                <p style = " margin: 10px 10px;  ">Seminar<%=seminar.getSeminarN() %> </p> 
             	<hr>
                <%
                String seminaristName = "Seminarist Is Not Added Yet";
               	Person p = seminar.getSeminarist();
                if(p!= null) seminaristName = p.getName() + " " + p.getSurname();
                %>
                <p style = " margin: 10px 10px;  "><%= seminaristName %> </p>
              	<hr>
               	<ul class='seminar-students'>
                
                <%
                List<Person> seminarStudents = seminar.getSeminarStudents();

                for (Person student : seminarStudents){
                	out.println("<li>"+ student.getName() + " " + student.getSurname()+"</li>");
                }

				%>
				
				
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

        <%
        	
            for(Section section : sections){
            	%>
            	<div class='seminar-box'>
               	<p style = " margin: 10px 10px;">Section <%=section.getSectionN()%></p>
                <hr>
                <%
                
                String sectionLeaderName = "Section Leader Is Not Added Yet";
                Person p = section.getSectionLeader();
                if(p!= null) sectionLeaderName = p.getName() + " " + p.getSurname();
                
                %>
                <p style = " margin: 10px 10px; "><%=sectionLeaderName %> </p>
                <hr>
                <ul class='seminar-students' >
                <%
                
                List<Person> sectionStudents = section.getSectionStudents();
                for (Person student : sectionStudents){
                	out.println("<li>"+ student.getName() + " " + student.getSurname() +"</li>");
                	
                }
				%>
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
	
</body>
</html>