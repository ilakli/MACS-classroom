<%@page import="database.PersonDB"%>
<%@page import="defPackage.Comment"%>
<%@page import="defPackage.Post"%>
<%@page import="java.util.ArrayList"%>
<%@page import="defPackage.Classroom"%>
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

<link rel="stylesheet" href="css/comments.css" type="text/css">
<title>Stream</title>
</head>
<body>
	<%
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomId);
		
		PersonDB personConnector = new PersonDB();
		
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
			<li class="active"><a
				href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>Stream</a></li>
			<li><a
				href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>About</a></li>
			<li><a
				href=<%="formation.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>Formation</a></li>
			<li><a
				href=<%="edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>Edit</a></li>
			<li><a
				href=<%="settings.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>Settings</a></li>
		</ul>
	</div>
	</nav>

	<button type="button" class="w3-button w3-teal" id="myBtn">Add Post</button>

	<div id="myModal" class="modal">

		<!-- Modal content -->
		<div class="modal-content">
			<div class="modal-header">
				<span class="close">&times;</span>
				<h2>Add Post</h2>
			</div>
			<div class="modal-body">

				<div class="form-group">
					<form action="PostServlet" method="POST">
						<textarea class="form-control" rows="5" id="comment"
							name="postText"></textarea>
						<input type="hidden" name=<%=Classroom.ID_ATTRIBUTE_NAME%>
							value=<%=classroomId%>>
						<button type="submit" class="btn btn-success" id="myBtn">Add
						</button>
					</form>
				</div>

			</div>
		</div>

	</div>
	<%

		ArrayList<Post> posts = connector.postDB.getPosts(classroomId);
		for (int i = 0; i < posts.size(); i++) {


			String postText = posts.get(i).getPostText();

			String postAuthorId = posts.get(i).getPersonId();
			String postAuthor = personConnector.getPerson(postAuthorId).getName() + " "
					+ personConnector.getPerson(postAuthorId).getSurname();

			String postId = posts.get(i).getPostId();
			ArrayList<Comment> comments = connector.commentDB.getPostComments(postId);

			out.println("<div class='panel panel-info posts'>");

			String html = "<div class=\"panel-heading w3-teal\" >" + postAuthor + "</div> <div class=\"panel-body\">"
					+ postText + "</div>";
			out.println(html);
			out.println("<ul class=\"list-group\">");
			for (int j = 0; j < comments.size(); j++) {
				String commentText = comments.get(j).getCommentText();

				String commentAuthorId = comments.get(j).getPersonID();
				String commentAuthor = personConnector.getPerson(commentAuthorId).getName() + " "
						+ personConnector.getPerson(commentAuthorId).getSurname();

				String commentBody = "<div class=\"w3-card-4\"> <div class=\"w3-container\"> <img src=\"img_avatar3.png\" alt=\"Avatar\" class=\"w3-left w3-circle\" style=\"width: 10%;\"> <h4>"
						+ commentAuthor + "</h4> <p style=\"padding-left: 11%;\">" + commentText
						+ "</p> </div> </div>";
				String commentHtml = " <li class=\"list-group-item\">" + commentBody + "</li>";
				out.println(commentHtml);
			}
			out.println("</ul>");

			String commentForm = "<form class=\"comments-form\"> <input class=\"postId\" type=\"hidden\" name = \"postId\" value = \""
					+ postId
					+ "\" >  <textarea class=\"comment-textarea\"> </textarea> <input type=\"submit\"class=\"w3-button w3-teal\" value=\"Add Comment\" ></form>";
			out.println(commentForm);

			out.println("</div>");
		}
		
	%>

	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	<script type="text/javascript" src='js/posts.js'></script>
	<script type="text/javascript" src='js/comments.js' type="text/javascript"></script>
</body>
</html>