package EditingServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.drive.model.File;

import database.AllConnections;
import defPackage.Classroom;
import defPackage.MailConnector;
import defPackage.MyDrive;
import defPackage.Person;

/**
 * Servlet implementation class AddNewSeminaristServlet
 */
@WebServlet("/AddNewSeminaristServlet")
public class AddNewSeminaristServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");	
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
		MyDrive service = (MyDrive) request.getServletContext().getAttribute("drive");
		
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		Classroom currentClassroom = connection.classroomDB.getClassroom(classroomId);
		
		String emails[] = email.split("\\s"); 
		ArrayList<String> goodEmails = new ArrayList<String>();
		
		boolean status = true;
		for(String e:emails){
			Matcher mathcer = pattern.matcher(e.toUpperCase());
			if(mathcer.matches()){	
				connection.personDB.addPersonByEmail(e);
				status = currentClassroom.classroomAddSeminarist(e);
				if (status) {
					String folderId = connection.driveDB.getClassroomFolder(classroomId);
	
					int atIndex = e.indexOf("@");
					if (atIndex == -1) atIndex = e.length();
					String mailPrefix = e.substring(0, atIndex);
					String seminaristFolderId = service.createFolder(mailPrefix, folderId);
					connection.driveDB.addSeminaristFolder(classroomId, e, seminaristFolderId);
					goodEmails.add(e);
				}
			}
		}
		if(goodEmails.size()>0){
			String subject = "Macs Classroom: You added in a classroom as a Seminarist";
			String text ="Macs Classroom: You added in a classroom: " + currentClassroom.getClassroomName() +
					" as a Seminarist" +"\nGo to the lick:\n" +
					"http://localhost:8080/MACS-classroom/stream.jsp?" + 
					Classroom.ID_ATTRIBUTE_NAME + "=" + currentClassroom.getClassroomID();
			new MailConnector(goodEmails, subject, text);
		}
	}

}
