<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<title>Create Classroom</title>

</head>
<body>
	<div class="jumbotron">
		<h2><a href="index.jsp" id="header-name">Macs Classroom</a></h2>
	</div>
	
	<!-- This part let's the user to give new class a name, the information goes to proper servlet -->
	<div>
		<label> Please enter a name for the new class  </label>
		
		<form action="CreateClassroomServlet" method="post">
			<input type="text" name="newClassroomName" />
			<input type="submit" value = "create class" />	
		</form>
	</div>
</body>
</html>