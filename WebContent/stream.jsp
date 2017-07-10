<%@page import="database.PersonDB"%>
<%@page import="defPackage.Comment"%>
<%@page import="defPackage.Person"%>
<%@page import="defPackage.Post"%>
<%@page import="java.util.ArrayList"%>
<%@page import="defPackage.Classroom"%>
<%@page import="database.AllConnections"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
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

<link rel="stylesheet" href="css/comments.css" type="text/css">
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<title>Stream</title>

<style type="text/css">

.ui.button.commentButton {
	height: 4em !important;
}

.ui.reply.form.comment textarea {
	width: 40em !important;
	height: 5em !important;
    min-height: 5em !important;
    max-height: 30em !important;
    font-size: 0.8em !important;
}
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
}
</style>
</head>
<body>
	
	<%!private String generatePostHTML(Post p, AllConnections connector, Person currentPerson) {
		
		Person author = connector.personDB.getPerson(p.getPersonId());
		
		String result = "<div class = \"event\">" +
							"<div class = \"label\">" +
								"<img src =\"" + author.getPersonImgUrl() + "\">" +
							"</div>" +
							"<div class = \"content\">" +
								"<div class = \"summary\">" +
									"<a>" + author.getName() + " " + author.getSurname() + "</a>"  +
									"<div class = \"date\"> " + p.getPostDate().toString() + "</div>" +
								"</div>" +
								"<div class = \"extra text\">" +
									"<pre>" + p.getPostText() + "</pre>" + 
								"</div>" +
							"</div>" +
						"</div>";
		
		ArrayList<Comment> comments = connector.commentDB.getPostComments(p.getPostId());
		
		result += "<div class = \"ui comments\" id=\"" + p.getPostId() + "post\">";
		for (Comment comment : comments) {
			Person commentAuthor = connector.personDB.getPerson(comment.getPersonID());
			
			result += "<div class = \"comment\">" +
						"<a class = \"avatar\">" + 
							"<img src = \"" + commentAuthor.getPersonImgUrl() + "\">" +
						"</a>" +
						"<div class = \"content\">" + 
							"<a class = \"author\">" + commentAuthor.getName() + " " + commentAuthor.getSurname() + "</a>" +
							"<div class = \"metadata\">" +
								"<span class = \"date\">" + comment.getCommentDate().toString() + "</span>" +
							"</div>" +
							"<div class = \"text\">" +
								"<pre>" + comment.getCommentText() + "</pre>" +
							"</div>" +
						"</div>" + 
					"</div>";
		}
		result += "</div>";
		
		result += "<form class=\"ui reply form comment\">" +
						    "<div class=\"field\">" +
						      "<textarea></textarea>" +
						      "<div class=\"ui button commentButton\">" +
						    	  "<i class=\"icon edit\"></i> <p>Add Comment</p>" +
							  "</div>" +
							  "<textarea style=\"display:none\" name = \"postId\">" + p.getPostId() + "</textarea>" +
							  "<textarea style=\"display:none\" name = \"personId\">" + currentPerson.getPersonID() + "</textarea>" +
							  "<textarea style=\"display:none\" name = \"personImgURL\">" + currentPerson.getPersonImgUrl() + "</textarea>" +
							  "<textarea style=\"display:none\" name = \"personName\">" + currentPerson.getName() + "</textarea>" +
							  "<textarea style=\"display:none\" name = \"personSurname\">" + currentPerson.getSurname() + "</textarea>" +
							  
						    "</div>" +
				 "</form>";
		return result;
	}%>	
	
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
	
	<!-- ADDING POST -->
	
	<form class="ui reply form post" id = "POST_ADDING_FORM">		
		 	    <div class="field">
			      <textarea id="POST_TEXT"></textarea>
			    </div>
					
				<div class="ui primary submit labeled icon button" id = "ADD_POST_BUTTON">
					<textarea style="display:none" id="CLASSROOM_ID"><%=currentClassroom.getClassroomID()%></textarea>
					<textarea style="display:none" id="PERSON_ID"><%=currentPerson.getPersonID()%></textarea>
					<i class="icon edit"></i> Post
			  	</div>
	</form>
	
	<!-- END OF ADDING POST -->
	
	<!-- POSTS -->
		<%
			ArrayList <Post> posts = connector.postDB.getPosts(currentClassroom.getClassroomID());
			out.println("<div class=\"ui feed\">");
			for (Post post : posts){
				String htmlCode = generatePostHTML(post, connector, currentPerson);
				out.println(htmlCode);

				out.println("</br></br></br>");
			}
			out.println("</div>");
		
		%>
		
	<!-- END OF POSTS -->
	
	
	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	<script type="text/javascript" src='js/posts.js'></script>
	<script type="text/javascript" src='js/addComments.js' type="text/javascript"></script>
</body>
</html>