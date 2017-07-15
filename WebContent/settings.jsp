<%@page import="WorkingServlets.DownloadServlet"%>
<%@page import="defPackage.Material"%>
<%@page import="defPackage.Function"%>
<%@page import="defPackage.Person"%>
<%@page import="defPackage.Position"%>
<%@page import="java.util.ArrayList"%>
<%@page import="defPackage.Classroom"%>
<%@page import="database.DBConnection"%>
<%@page import="database.AllConnections"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="icon" href="favicon.ico" type="image/x-icon" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.css">
<script
	src="https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.js"></script>
<title>Settings</title>
<style>
pre {
	height: 200px;
}

.ui.menu {
	margin-top: 0;
}

.block.header {
	margin: 0;
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
.button-margin{
	margin: 0.5% !important;
	float: right;
}
.sign-out {
	float: right;
	margin-top: 0.8%;
	margin-right: 0.7%;
}
</style>
</head>
<body>

	<%!private String checkboxValue(boolean b) {
		if (b)
			return "checked";
		return "";
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

		int numberOfSeminars = currentClassroom.getNumberOfSeminars();
		boolean autoSeminarDistribution = currentClassroom.areSeminarsAudoDistributed();
		int numberOfSections = currentClassroom.getNumberOfSections();
		boolean autoSectionDistribution = currentClassroom.areSectionsAudoDistributed();
		int numberOfReschedulings = currentClassroom.getNumberOfReschedulings();
		int reschedulingLength = currentClassroom.getReschedulingLength();

		Person currentPerson = (Person) request.getSession().getAttribute("currentPerson");
		boolean isAdmin = connector.personDB.isAdmin(currentPerson);
		boolean isStudent = currentClassroom.classroomStudentExists(currentPerson.getEmail());
		boolean isSectionLeader = currentClassroom.classroomSectionLeaderExists(currentPerson.getEmail());
		boolean isSeminarist = currentClassroom.classroomSeminaristExists(currentPerson.getEmail());
		boolean isLecturer = currentClassroom.classroomLecturerExists(currentPerson.getEmail());
		
		if(!isLecturer && !isAdmin)
			 response.sendError(400, "Not Permitted At All");
		
		ArrayList<Function> functions = connector.functionDB.getAllFunctions();
		ArrayList<Position> positions = connector.positionDB.getAllPositions();
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
		</a> <a class="item"
			href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Stream</a>


		<a class="item"
			href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>About</a>

		<a class="item"
			href=<%="assignments.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID%>>Assignments</a>

		<%
			if (isAdmin || isLecturer) {
		%>
		<a class="active item"
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

	<script>
		var isNotNum = function(string){
			if(string.length === 0)
				return true;
			
			for(var i = 0; i<string.length; i++){
				if(string[i]<'0' || string[i]>'9' )
					return true;
			}
			return false;
		}; 
		
		function validateForm() {
			if (isNotNum(document.frm.seminars.value) ) {
				alert("Number of seminars must be integer");
				document.frm.seminars.focus();
				return false;
			} else if (isNotNum(document.frm.sections.value)) {
				alert("Number of sections must be integer");
				document.frm.sections.focus();
				return false;
			} else if (isNotNum(document.frm.numResch.value)){
				alert("Number of Rescheduling must be integer");
				document.frm.numResch.focus();
				return false;
			} else if (isNotNum(document.frm.lengthResch.value)){
				alert("Length of Rescheduling must be integer");
				document.frm.lengthResch.focus();
				return false;
			} 
			
		}
	</script>
	<%
		response.setHeader(Classroom.ID_ATTRIBUTE_NAME, classroomID);
	%>
	<form name="frm" method="post" action="ChangeSettingsServlet"
		class="ui form" onSubmit="return validateForm()">
		<table class="ui table">
			<tr>
				<th></th>
				<th></th>
			</tr>
			<tr>
				<td>
					<div class="ui big white circular label">Number of Seminars</div>
				</td>
				<td class="right aligned collapsing">
					<div class="ui input focus">
						<input type="text" name="seminars" value=<%=numberOfSeminars%> />
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="ui big white circular label">Automatic
						distribution of students into Seminars</div>
				</td>
				<td class="right aligned collapsing">
					<div class="ui toggle checkbox">
						<input type="checkbox" name="disSeminars"
							<%=checkboxValue(autoSeminarDistribution)%> /> <label></label>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="ui big white circular label">Number of Sections</div>
				</td>
				<td class="right aligned collapsing">
					<div class="ui input focus">
						<input type="text" name="sections" value=<%=numberOfSections%> />
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="ui big white circular label">Automatic
						distribution of students into Sections</div>
				</td>
				<td class="right aligned collapsing">
					<div class="ui toggle checkbox">
						<input type="checkbox" name="disSSections"
							<%=checkboxValue(autoSectionDistribution)%> /> <label></label>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="ui big white circular label">Number of Assignment
						Rescheduling</div>
				</td>
				<td class="right aligned collapsing">
					<div class="ui input focus">
						<input type="text" name="numResch"
							value=<%=numberOfReschedulings%> />
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="ui big white circular label">Length in Days of
						Assignment Rescheduling</div>
				</td>
				<td class="right aligned collapsing">
					<div class="ui input focus">
						<input type="text" name="lengthResch"
							value=<%=reschedulingLength%> />
					</div>
				</td>
			</tr>
			
			<!--<table>
		    <thead>
		      <tr>
		        <th></th>
		        <%for (int i = 0; i < positions.size(); i++) {
				Position p = positions.get(i);
				out.println("<th>" + p.getName() + "</th>");
			}%>
		      </tr>
		    </thead>
		    <tbody>
		      <%for (Function f : functions) {
				out.println("<tr>");
				out.println("<th>" + f.getName() + "</th>");
				for (int i = 0; i < positions.size(); i++) {
					Position p = positions.get(i);
					out.println("<td> <input type=\"checkbox\" name=\"permission\" value=\"" + String.valueOf(p.getID())
							+ "-" + String.valueOf(f.getID()) + "\""
							+ checkboxValue(connector.functionDB.hasPremission(currentClassroom, p, f)) + "> </td>");
				}

				out.println("</tr>");
			}%> 
		      
		     </tbody>
		</table>
		-->
		</table>
		
		
		
		<input type="submit" class="ui white right button button-margin" value="Save">
		<input type="hidden" name=<%=Classroom.ID_ATTRIBUTE_NAME%>
			value=<%=classroomID%>>

	</form>


</body>
</html>