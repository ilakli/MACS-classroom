
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="defPackage.Course"%>
<%@page import="defPackage.DBConnection"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Macs Classroom</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="style.css">
</head>
<body>
	<div class="jumbotron">
		<h2>Macs Classroom</h2>
	</div>

	<button id="create" type="submit" class="btn btn-danger">Create
		New</button>

	<%-- 
		Generates HTML code according to given name. 
		HTML code consists of section and div which together make up a course display.
	 --%>
	<%!private String generateNameHTML(String name,String courseId) {

		String result = "<section class=\"single-classroom\"> <div class=\"well\"> <a href=\"stream.jsp?" +Course.ID_ATTRIBUTE_NAME + "=" + courseId + "\" class=\"single-classroom-text\">"
				+ name + "</a> </div> </section>";
		return result;

	}%>
	
	<%-- 
		Takes DBConnector from servlet context and pulls list of courses out of it. 
		Then displays every course on the page.
	--%>
	<%
		DBConnection connector = (DBConnection) request.getServletContext().getAttribute("connection");

		ArrayList<Course> courses = connector.getCourses();

		for (Course course : courses) {
			out.print(generateNameHTML(course.getCourseName(),course.getCourseID()));
		}
	%>
</body>
</html>