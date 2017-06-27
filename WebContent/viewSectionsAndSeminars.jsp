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
<title>Sections And Seminars</title>
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
			<li class="active"><a
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
		<h1 style = "background-color: #dfdae0; margin: 0 0; text-align: center; color: white;"> Seminar Groups</h1>
       
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
                out.println("<div class='seminar-box'");  
                out.println("style = \" color: white;\">");
        
                out.println("<p style = \" margin: 10px 10px;  \">Seminar " +  seminar.getSeminarN() + " </p> ");
               	
                out.println("<hr>");
                
                String seminaristName = "Seminarist Is Not Added Yet";
               	Person p = seminar.getSeminarist();
                if(p!= null) seminaristName = p.getName() + " " + p.getSurname();
                
                out.println("<p style = \" margin: 10px 10px;  \">" + seminaristName +"  </p> ");
               
                out.println("<hr>");
                out.println("<ul class='seminar-students' style = \" margin: 10px 10px;  \" >");
                
                
                List<Person> seminarStudents = seminar.getSeminarStudents();

                for (Person student : seminarStudents){
                	out.println("<li>"+ student.getName() + " " + student.getSurname()+"</li>");
                }

				out.println("</ul>");
                   
				out.println("</div>");
                

            }
        
        	
        
        
		        out.println("<div class='seminar-box'");  
		        out.println("style = \" color: white;\">");
		
		        out.println("<p style = \" margin: 10px 10px;  \">Studens Without Seminar </p> ");
		       	
		        out.println("<hr>");
		        
		        
		        out.println("<p style = \" margin: 10px 10px;  \">  </p> ");
		       
		        out.println("<hr>");
		        out.println("<ul class='seminar-students' style = \" margin: 10px 10px;  \" >");
		        
		        for (Person student : studentsWithoutSeminar){
		        	out.println("<li>"+ student.getName() + " " + student.getSurname() +"</li>");
		        	
		        }
		
				out.println("</ul>");
		           
				out.println("</div>");
		        
        
        
        
        %>
    </div>
	
	
	
	<div class='seminar-boxes'>
   		<h1 style = "background-color: #dfdae0; margin: 0 0; text-align: center; color: white;"> Sections</h1>
       
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
                out.println("<div class='seminar-box'");  
                out.println("style = \" color: white;\">");
        
                out.println("<p style = \" margin: 10px 10px;  \">Section " +  section.getSectionN() + " </p> ");
               	
                out.println("<hr>");
                
                String sectionLeaderName = "Section Leader Is Not Added Yet";
                Person p = section.getSectionLeader();
                if(p!= null) sectionLeaderName = p.getName();
                
                out.println("<p style = \" margin: 10px 10px;  \">" + sectionLeaderName + "  </p> ");
               
                out.println("<hr>");
                out.println("<ul class='seminar-students' style = \" margin: 10px 10px;  \" >");
                
                List<Person> sectionStudents = section.getSectionStudents();
                for (Person student : sectionStudents){
                	out.println("<li>"+ student.getName() + " " + student.getSurname() +"</li>");
                	
                }

				out.println("</ul>");
                   
				out.println("</div>");
                
                
                
                
                
            }
        
        
		        out.println("<div class='seminar-box'");  
		        out.println("style = \" color: white;\">");
		
		        out.println("<p style = \" margin: 10px 10px;  \">Studens Without Section </p> ");
		       	
		        out.println("<hr>");
		        
		        
		        out.println("<p style = \" margin: 10px 10px;  \">   </p> ");
		       
		        out.println("<hr>");
		        out.println("<ul class='seminar-students' style = \" margin: 10px 10px;  \" >");
		        
		        for (Person student : studentsWithoutSection){
		        	out.println("<li>"+ student.getName() + " " + student.getSurname() +"</li>");
		        	
		        }
		
				out.println("</ul>");
		           
				out.println("</div>");
				
        %>
    </div>
	

	







</body>
</html>