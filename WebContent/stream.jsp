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
	height: 2em !important;
	width: 20% !important;
	margin-left: 3% !important;
	margin-top: 1.5% !important;
	display: inline-table !important;
}

.ui.reply.form.comment textarea {
	width: 75% !important;
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

body {
	background-color: #ebebe9;

}

#POST_ADDING_FORM {
	margin-left: 25% !important;
	margin-right: 25% !important;
	margin-bottom: 7% !important;
}

#ADD_POST_BUTTON {
	float: right !important;
}

#POST_TEXT {
	max-height: 8em !important;
}

.postHead, .postComments {
	
	margin-top: 0% !impoertant;
	max-width: 50% !important;
	margin-left: 25% !important;
	margin-right: 25% !important;
}

.postCommentAdding {
	
}

.postHead{
	border-top-left-radius: 0.3em !important;
	border-top-right-radius: 0.3em !important;
	background-color: white !important;
}

.postComments {
	background-color: #f6f7f9 !important;
	margin-top: 0% !important;
}

.comment {
	padding-left: 2% !important;
	padding-top: 1% !important;
	padding-bottom: 1% !important;
}

.label {
	margin-left: 2% !important;
	margin-top: 2% !important;
}

.avatar > img {
	border-radius: 50% !important;	
}

.metadata{
	float: right !important;
	padding-right: 2% !important;
} 

.postDate {
	float: right !important;
	margin-right: 2% !important;
}


</style>
</head>
<body>
	
	<%!private String generatePostHTML(Post p, AllConnections connector, Person currentPerson, boolean isClassroomFinished) {
		
		Person author = connector.personDB.getPerson(p.getPersonId());
		
		String dateString = p.getPostDate().toGMTString();
		dateString = dateString.substring(0, dateString.lastIndexOf(':'));
		
		
		String result =  "<div class = \"event postHead\">" +
								"<div class = \"label\">" +
									"<img src =\"" + author.getPersonImgUrl() + "\">" +
								"</div>" +
								"<div class = \"content\">" +
									"<div class = \"summary\">" +
										"<a>" + author.getName() + " " + author.getSurname() + "</a>"  +
										"<div class = \"date postDate\"> " + dateString + "</div>" +
									"</div>" +
									"<div class = \"extra text\">" +
										"<pre>" + p.getPostText() + "</pre>" + 
									"</div>" +
								"</div>" +
							"</div>";
		
		ArrayList<Comment> comments = connector.commentDB.getPostComments(p.getPostId());
		
		result += "<div class = \"ui comments postComments\" id=\"" + p.getPostId() + "post\">";
		for (Comment comment : comments) {
			Person commentAuthor = connector.personDB.getPerson(comment.getPersonID());
			
			dateString = comment.getCommentDate().toGMTString();
			dateString = dateString.substring(0, dateString.lastIndexOf(':'));
			
			result += "<div class = \"comment\">" +
						"<a class = \"avatar\">" + 
							"<img src = \"" + commentAuthor.getPersonImgUrl() + "\">" +
						"</a>" +
						"<div class = \"content\">" + 
							"<a class = \"author\">" + commentAuthor.getName() + " " + commentAuthor.getSurname() + "</a>" +
							"<div class = \"metadata\">" +
								"<span class = \"date\">" + dateString + "</span>" +
							"</div>" +
							"<div class = \"text\">" +
								"<pre>" + comment.getCommentText() + "</pre>" +
							"</div>" +
						"</div>" + 
					"</div>";
		}
		
		if (!isClassroomFinished) {
			
			result +=  "<div class = \"postCommentAdding\" id =\"" + p.getPostId()  + "commentAdding\">" +
					"<form class=\"ui reply form comment\">" +
							    "<div class=\"field\">" +
							      "<textarea></textarea>" +
							      "<div class=\"ui button commentButton\">" +
							    	  "Comment" +
								  "</div>" +
								  "<textarea style=\"display:none\" name = \"postId\">" + p.getPostId() + "</textarea>" +
								  "<textarea style=\"display:none\" name = \"personId\">" + currentPerson.getPersonID() + "</textarea>" +
								  "<textarea style=\"display:none\" name = \"personImgURL\">" + currentPerson.getPersonImgUrl() + "</textarea>" +
								  "<textarea style=\"display:none\" name = \"personName\">" + currentPerson.getName() + "</textarea>" +
								  "<textarea style=\"display:none\" name = \"personSurname\">" + currentPerson.getSurname() + "</textarea>" +
								  
							    "</div>" +
					 "</form>" +
					"</div>";
		}		
		
		result += "</div>";
		
		return result;
	}%>	
	
	<%
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomID);
		PersonDB personConnector = connector.personDB;

		Person currentPerson = (Person) request.getSession().getAttribute("currentPerson");
		if(currentPerson == null){
			response.sendError(400, "Not Permitted At All");
			return;
		}
		
		boolean isAdmin = connector.personDB.isAdmin(currentPerson);
		boolean isStudent = currentClassroom.classroomStudentExists(currentPerson.getEmail());
		boolean isSectionLeader = currentClassroom.classroomSectionLeaderExists(currentPerson.getEmail());
		boolean isSeminarist = currentClassroom.classroomSeminaristExists(currentPerson.getEmail());
		boolean isLecturer = currentClassroom.classroomLecturerExists(currentPerson.getEmail());
		boolean isClassroomFinished = connector.classroomDB.isClassroomFinished(currentClassroom.getClassroomID());

		if(!isAdmin && !isStudent && !isSectionLeader && !isSeminarist && !isLecturer){
			 response.sendError(400, "Not Permitted At All");
			 return;
		}
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
			if ((isAdmin || isLecturer) && !isClassroomFinished) {
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
	
	<%
		if (!isClassroomFinished) {
	%>
	
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
	
	<%
		}
	%>
	
	<!-- END OF ADDING POST -->
	
	
	<!-- POSTS -->
		<%
			ArrayList <Post> posts = connector.postDB.getPosts(currentClassroom.getClassroomID());
			out.println("<div class=\"ui feed\">");
			for (int i=posts.size()-1; i>=0; i--){
				Post post = posts.get(i);
				String htmlCode = generatePostHTML(post, connector, currentPerson, isClassroomFinished);
				out.println(htmlCode);

			}
			out.println("</div>");
		
		%>
		
	<!-- END OF POSTS -->
	
	<script>
		function signOut() {
			var auth2 = gapi.auth2.getAuthInstance();
			auth2.signOut().then(function() {
				console.log('User signed out.');
			});
		}

		function onLoad() {
			gapi.load('auth2', function() {
				gapi.auth2.init();
			});
		}
	</script>
	<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>
	<script type="text/javascript" src='js/posts.js'></script>
	<script type="text/javascript" src='js/addComments.js' type="text/javascript"></script>
	
</body>
</html>