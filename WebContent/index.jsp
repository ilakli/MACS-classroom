
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="defPackage.Classroom"%>
<%@page import="database.AllConnections"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Macs Classroom</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">

<script type="text/javascript">

function redirect() {
	
	window.location = "createClassroom.jsp"

}
</script>
</head>
<body>
	<div class="jumbotron">
		<h2><a href="index.jsp" id="header-name">Macs Classroom</a></h2>
	</div>
	
	
	<button id="create" type="submit" class="btn btn-danger" onclick="redirect()">Create
		New</button>

	<%-- 
		Generates HTML code according to given name. 
		HTML code consists of section and div which together make up a classroom display.
	 --%>
	<%!private String generateNameHTML(String name,String classroomId) {
		String result = "<section class=\"single-classroom\"> <div class=\"well\"> <a href=\"stream.jsp?" +
						Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId + "\" class=\"single-classroom-text\">" +
						name + "</a> </div> </section>";
		return result;
	}%>
	
	<%-- 
		Takes DBConnector from servlet context and pulls list of classrooms out of it. 
		Then displays every classroom on the page.
	--%>
	<%
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		ArrayList<Classroom> classrooms = connector.classroomDB.getClassrooms();
		for (Classroom classroom : classrooms) {
			out.print(generateNameHTML(classroom.getClassroomName(),classroom.getClassroomID()));
		}
	%>
	
</body>
</html>