<%@page import="database.PersonDB"%>
<%@page import="defPackage.Comment"%>
<%@page import="defPackage.Person"%>
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

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.css">
<script
	src="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.js"></script>
	
<link rel="stylesheet" href="css/style.css">

<link rel="stylesheet" href="css/comments.css" type="text/css">
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<title>Stream</title>
<style>
.ui.menu {
	margin-top: 0;
}
.block.header {
	margin: 0;
}
.sign-out {
	float: right;
	margin-top: 0.8%;
	margin-right: 0.7%;
}
.head-panel {
	display: block;
	margin: 0 !important;
	padding: 0 !important;
}

.head-text {
	display: inline-block;
	border: solid;
	padding: .78571429rem 1rem !important;
	!
	important;
}
</style>
</head>
<body>
	<%
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomID);
		PersonDB personConnector = connector.personDB;

		Person currentPerson = (Person) request.getSession().getAttribute("currentPerson");
		boolean isAdmin = connector.personDB.isAdmin(currentPerson);
		boolean isStudent = currentClassroom.classroomStudentExists(currentPerson.getEmail());
		boolean isSectionLeader = currentClassroom.classroomSectionLeaderExists(currentPerson.getEmail());
		boolean isSeminarist = currentClassroom.classroomSeminaristExists(currentPerson.getEmail());
		boolean isLecturer = currentClassroom.classroomLecturerExists(currentPerson.getEmail());
	%>
	<div class="ui block header head-panel">
	<a href="index.jsp">
		<h3 class="ui header head-text">Macs Classroom</h3>
	</a>
	  <a class="sign-out" href="DeleteSessionServlet" onclick="signOut();">Sign out</a>
	</div>
	<div class="ui menu">
		<a
			href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>
			class="header item"> <%=currentClassroom.getClassroomName()%>
		</a> <a class="active item"
			href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Stream</a>


		<a class="item"
			href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>About</a>

		<a class="item"
			href=<%="assignments.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Assignments</a>

		<%
			if (isAdmin || isLecturer) {
		%>
		<a class="item"
			href=<%="settings.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Settings</a>
		<%
			}
		%>
		<div class="right menu">
			<a class="item"
				href=<%="people.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>
				People </a>
			<div class="ui dropdown item">
				Groups <i class="dropdown icon"></i>
				<div class="menu">
					<a
						href=<%="sections.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>
						class="item"> Sections </a> <a
						href=<%="seminars.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>
						class="item"> Seminars </a>
				</div>
			</div>
			<script>
				$('.ui.dropdown').dropdown();
			</script>
		</div>
	</div>
	
	
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
							value=<%=classroomID%>>
						<button type="submit" class="btn btn-success">Add
						</button>
					</form>
				</div>

			</div>
		</div>

	</div>
	<%

		ArrayList<Post> posts = connector.postDB.getPosts(classroomID);
		for (int i = 0; i < posts.size(); i++) {


			String postText = posts.get(i).getPostText() + posts.get(i).getPostDate();

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
				String commentText = comments.get(j).getCommentText() + comments.get(j).getCommentDate();

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
	<%
		
	%>
	<input type="hidden" id="currentPerson" value='<%=currentPerson.getPersonID() %>'>
  	<input type="hidden" id="currentPersonName" value='<%= currentPerson.getName() + " "  + currentPerson.getSurname() %>'>
  		

	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	<script type="text/javascript" src='js/posts.js'></script>
	<script type="text/javascript" src='js/addComments.js' type="text/javascript"></script>
</body>
</html>