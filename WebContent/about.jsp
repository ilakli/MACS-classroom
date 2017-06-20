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
<link rel="stylesheet" href="css/style.css">
<title>About</title>
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
				href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>About</a></li>
			<li><a
				href=<%="formation.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Formation</a></li>
			<li><a
				href=<%="edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Edit</a></li>
			<li><a
				href=<%="settings.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Settings</a></li>
		</ul>
	</div>
	</nav>




	<form action="UploadServlet" method="POST"
		enctype="multipart/form-data">
		<input name=<%=Classroom.ID_ATTRIBUTE_NAME%> type="hidden"
			value=<%=classroomID%> id="classroomID" /> <input type="file"
			name="file" size="30" /> <input type="submit"
			/ class="btn btn-success">
	</form>
	
	<%
		List<Assignment> assignments = connector.assignmentDB.getAssignments(classroomID);
		
		for (Assignment a : assignments) {
			String htmlCode = generateAssignmentHTML(a);
			out.println(htmlCode);
		}
	%>
	
	</br></br></br></br></br></br>
	
	<%
		List<Material> materials = currentClassroom.getMaterials();
		for (int i = 0; i < materials.size(); i++) {
			String materialName = materials.get(i).getMaterialName();
			String htmlMaterial = generateMaterial(materialName);
			out.print(htmlMaterial);
		}
	%>






</body>
</html>