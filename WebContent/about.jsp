<%@page import="database.MaterialDB"%>
<%@page import="defPackage.Category"%>
<%@page import="defPackage.Person"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Listeners.ContextListener"%>
<%@page import="database.CategoryDB"%>
<%@page import="WorkingServlets.DownloadServlet"%>
<%@page import="defPackage.Material"%>
<%@page import="java.util.List"%>
<%@page import="defPackage.Classroom"%>
<%@page import="defPackage.Assignment"%>
<%@page import="database.DBConnection"%>
<%@page import="database.AllConnections"%>
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
<link rel="stylesheet" href="css/category.css">
<title>About</title>
</head>
<body>

	<%!private String generateAssignmentHTML(Assignment a) {

		String result = "<div class=\"panel panel-default\"> " + " <div class=\"panel-body\"> " + "<h1>" + a.getTitle()
				+ "</h1>" + "<p> " + a.getInstructions() + "</p>" + " <a href=\"DownloadServlet?"
				+ DownloadServlet.DOWNLOAD_PARAMETER + "=" + a.getFileName() + "\">" + a.getFileName() + "</a></div>"

				+ " <div class=\"panel-footer\"></div> "

				+ "</div>";

		return result;
	}%>
	<%!private String generateMaterial(String materialName) {
		System.out.println("Material Name is: " + materialName);

		String result = "<div class=\"panel panel-default\">  <div class=\"panel-body\"> <a href=\"DownloadServlet?"
				+ DownloadServlet.DOWNLOAD_PARAMETER + "=" + materialName + "\">" + materialName
				+ "</a></div> <div class=\"panel-footer\"></div> </div>";

		System.out.println(result);
		return result;
	}%>
	<%
		System.out.println("Already Here");
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomID);
		
		Person currentPerson = (Person)request.getSession().getAttribute("currentPerson");
		boolean isAdmin = connector.personDB.isAdmin(currentPerson);
		boolean isStudent = currentClassroom.classroomStudentExists(currentPerson.getEmail());
		boolean isSectionLeader = currentClassroom.classroomSectionLeaderExists(currentPerson.getEmail());
		boolean isSeminarist = currentClassroom.classroomSeminaristExists(currentPerson.getEmail());
		boolean isLecturer = currentClassroom.classroomLecturerExists(currentPerson.getEmail());
		
		
		CategoryDB categoryDB = ((AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME)).categoryDB;
		ArrayList<Category> allCategories = categoryDB.getCategorys(classroomID);
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
			
			<li class="active"><a
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
		</ul>
	</div>
	</nav>

	<%if (isAdmin || isLecturer || isSeminarist) {%>
		<div class="categories">
			<input type="text" value="" placeholder="Add Category" />
			<button class="categoryAddButton btn btn-success">Submit</button>
			<input type="hidden" value="AddNewCategoryServlet">
		</div>
	
		<form action="UploadServlet" method="POST"
			enctype="multipart/form-data">
			<input name=<%=Classroom.ID_ATTRIBUTE_NAME%> type="hidden"
				value=<%=classroomID%> id="classroomID" /> 
				
	  	
			<input type="file" name="file" size="30" />
			<select name="materialCategory">
	  			<option value="" disabled selected>Select Category</option>
	 			<%
	  				for(int i=0;i<allCategories.size();i++){
	  					
	  					Category currentCategory = allCategories.get(i);
	  					String currentCategoryName = currentCategory.getCategoryName();
	  					String currentCategoryId = currentCategory.getCategoryId();
	  					
	  					out.println("<option value='" + currentCategoryId +"'>" + currentCategoryName+ "</option>");
	  				}
	  			%>
	  		</select>
	  		<br> 
			<input type="submit"  class="btn btn-success">
		</form>
	<%}%>
	
	<%
	MaterialDB materialDB = ((AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME)).materialDB;
	

	
	for(int i=0;i<allCategories.size();i++){
		Category currentCategory = allCategories.get(i);
		
		out.print("<h1 class=\"category-title\">" + currentCategory.getCategoryName() + "</h1>");
		
		List<Material> associatedMaterials = materialDB.getMaterialsForCategory(classroomID,currentCategory.getCategoryId());
		
		for (int j = 0; j < associatedMaterials.size(); j++) {
			String materialName = associatedMaterials.get(j).getMaterialName();
			String htmlMaterial = generateMaterial(materialName);
			
			out.print(htmlMaterial);
		}
	}
	%>
	
	




<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
		<script type="text/javascript" src='js/categoryMultiInput.js'></script>
	<script type="text/javascript" src='js/categoryAdd.js'></script>
</body>
</html>