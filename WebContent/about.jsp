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

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.css">

<script
	src="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.js"></script>

<link rel="stylesheet" href="css/category.css">

<link rel="icon" href="favicon.ico" type="image/x-icon" />

<title>About</title>
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
}

.material-add-form {
	display: none;
}

.material-add-form>* {
	margin: 0.5% !important;
}
.material-add-button {
	margin: 0.5% !important;
	display: block !important;
}

.category-add-button {
	display: block !important;
	margin: 0.5% !important;
}
.segments {
	display: inlin-block;
	width: 60% !important;
	margin: 2% auto !important;
}
.segment a {
	display: block !important;
}
</style>

</head>

<body>
	<%!private String generateMaterial(String materialName) {
		System.out.println("Material Name is: " + materialName);

		String result = "<a href=\"DownloadServlet?" + DownloadServlet.DOWNLOAD_PARAMETER + "=" + materialName + "\">"
				+ materialName + "</a>";

		return result;
	}%>

	<%
		System.out.println("Already Here");
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomID);

		Person currentPerson = (Person) request.getSession().getAttribute("currentPerson");
		boolean isAdmin = connector.personDB.isAdmin(currentPerson);
		boolean isStudent = currentClassroom.classroomStudentExists(currentPerson.getEmail());
		boolean isSectionLeader = currentClassroom.classroomSectionLeaderExists(currentPerson.getEmail());
		boolean isSeminarist = currentClassroom.classroomSeminaristExists(currentPerson.getEmail());
		boolean isLecturer = currentClassroom.classroomLecturerExists(currentPerson.getEmail());

		CategoryDB categoryDB = ((AllConnections) request.getServletContext()
				.getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME)).categoryDB;
		ArrayList<Category> allCategories = categoryDB.getCategorys(classroomID);
	%>
	<input type="hidden" id="classroomID" value="<%=classroomID%>">
	<div class="ui block header head-panel">
		<a href="index.jsp"> -
			<h3 class="ui header head-text">Macs Classroom</h3>
		</a> <a class="sign-out" href="DeleteSessionServlet" onclick="signOut();">Sign
			out</a>
	</div>

	<div class="ui menu">
		<a
			href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>
			class="header item"> <%=currentClassroom.getClassroomName()%>

		</a> <a class="item"
			href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Stream</a>
		<a class="active item"
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
						class="item"> Seminars </a> -
				</div>

			</div>

		</div>

	</div>

	<%
		if (isAdmin || isLecturer || isSeminarist) {
	%>

	<button class="positive ui button category-add-button">Add
		Category</button>
	<div class="ui modal">
		<div class="header">Add Category</div>
		<div class="content">
			<div class="field">
				<div class="categories">
					<input type="text" value="" placeholder="Add Category" />
				</div>
			</div>
		</div>
		<div class="actions">
			<button class="ui teal button categoryAddButton">Add</button>
			<input type="hidden" value="AddNewCategoryServlet">
			<button class="ui red button cancel">Cancel</button>
		</div>
	</div>
	<script>
		$(".category-add-button").click(function() {
			$('.ui.modal').modal('show');
		});
	</script>

	<button class="positive ui button material-add-button">Add
		Material</button>
	<form action="UploadServlet" method="POST"
		enctype="multipart/form-data" class="material-add-form">
		<input name=<%=Classroom.ID_ATTRIBUTE_NAME%> type="hidden"
			value=<%=classroomID%> id="classroomID" />

		<div class="ui selection dropdown">
			<input type="hidden" name="materialCategory"> <i
				class="dropdown icon"></i>
			<div class="default text">Select Category</div>
			<div class="menu">
				<%
					for (int i = 0; i < allCategories.size(); i++) {

							Category currentCategory = allCategories.get(i);
							String currentCategoryName = currentCategory.getCategoryName();
							String currentCategoryId = currentCategory.getCategoryId();

							out.println("<div class=\"item\" data-value='" + currentCategoryId + "'>" + currentCategoryName
									+ "</div>");
						}
				%>
			</div>
		</div>
		
		<div>
			<label for="file" class="ui icon button"> <i
				class="file icon"></i> Open File
			</label> <input type="file" id="file" name="file" size=30
				style="display: none">
		</div>
		
		<input type="submit" class="ui teal button" value="Add">
		<script>
			$(".ui.dropdown").dropdown();
		</script>
	</form>
	
	<script>
		$(".material-add-button").click(function() {
			$(this).next().toggle();
		});
	</script>
	<%
		}
	%>

	<%
		MaterialDB materialDB = ((AllConnections) request.getServletContext()
				.getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME)).materialDB;

		for (int i = 0; i < allCategories.size(); i++) {
			Category currentCategory = allCategories.get(i);

			out.print("<div class=\"ui segments\"> <div class=\"ui segment\"> <div class=\"ui medium header\">"
					+ currentCategory.getCategoryName() + "</div> </div>");
			out.print("<div class=\"ui secondary segment\">");
			List<Material> associatedMaterials = materialDB.getMaterialsForCategory(classroomID,
					currentCategory.getCategoryId());

			for (int j = 0; j < associatedMaterials.size(); j++) {
				String materialName = associatedMaterials.get(j).getMaterialName();
				String htmlMaterial = generateMaterial(materialName);

				out.print(htmlMaterial);
			}
			out.print("</div>");
			out.print("</div>");
		}
	%>

	<script type="text/javascript" src='js/categoryMultiInput.js'></script>

	<script type="text/javascript" src='js/categoryAdd.js'></script>

</body>

</html>