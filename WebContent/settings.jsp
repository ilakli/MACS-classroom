<%@page import="WorkingServlets.DownloadServlet"%>
<%@page import="defPackage.Material"%>
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
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<title>Settings</title>
</head>
<body>
	<%!private String checkboxValue(boolean b){
		if (b) return "checked";
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
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomId);
		int numberOfSeminars = currentClassroom.getNumberOfSeminars();
		boolean autoSeminarDistribution = currentClassroom.areSeminarsAudoDistributed();
		int numberOfSections = currentClassroom.getNumberOfSections();
		boolean autoSectionDistribution = currentClassroom.areSectionsAudoDistributed();
		int numberOfReschedulings = currentClassroom.getNumberOfReschedulings();
		int reschedulingLength = currentClassroom.getReschedulingLength();
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
				href=<%="stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>Stream</a></li>
			<li><a
				href=<%="about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>About</a></li>
			<li><a
				href=<%="formation.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>Formation</a></li>
			<li><a
				href=<%="edit.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>Edit</a></li>
			<li class="active"><a
				href=<%="settings.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId%>>Settings</a></li>
		</ul>
	</div>
	</nav>

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
	<% response.setHeader(Classroom.ID_ATTRIBUTE_NAME, classroomId); %>
	<form name="frm" method="post" action= "ChangeSettingsServlet" 
		  onSubmit="return validateForm()">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="30%"></td>
				<td width="78%"></td>
			</tr>
			<tr>
				<td height="50" >Number of Seminars</td>
				<td height="50" ><input type="text" name="seminars" value = <%=numberOfSeminars%> /></td>
			</tr>
			<tr>
				<td height="50" >Automatic distribution of students into Seminars</td>
				<td height="50" ><input type="checkbox" name="disSeminars" <%=checkboxValue(autoSeminarDistribution)%>/></td>
			</tr>
			<tr>
				<td height="50" >Number of Sections</td>
				<td height="50" ><input type="text" name="sections" value = <%=numberOfSections%> /></td>
			</tr>
			
			<tr>
				<td height="50" >Automatic distribution of students into Sections</td>
				<td height="50" ><input type="checkbox" name="disSSections" <%=checkboxValue(autoSectionDistribution)%>/></td>
			</tr>
			<tr>
				<td height="50" >Number of Assignment Rescheduling </td>
				<td height="50" ><input type="text" name="numResch" value = <%=numberOfReschedulings%> /></td>
			</tr>
			<tr>
				<td height="50" >Length in Days of Assignment  Rescheduling </td>
				<td height="50" ><input type="text" name="lengthResch" value = <%=reschedulingLength%> /></td>
			</tr>
			
			<tr>
				<td height="50" >Do you want to save new values for your class?</td>
				<td height="50" ><input type="submit" name="save" value="Save"></td>
			</tr>
		</table>
		<input type="hidden" name = <%= Classroom.ID_ATTRIBUTE_NAME  %> value = <%= classroomId %>>  
	</form>
	
</body>
</html>