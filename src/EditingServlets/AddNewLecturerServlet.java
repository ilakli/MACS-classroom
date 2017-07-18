package EditingServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AllConnections;
import defPackage.Classroom;
import defPackage.MailConnector;
import defPackage.Person;

/**
 * Servlet implementation class AddNewLecturerServlet
 */
@WebServlet("/AddNewLecturerServlet")
public class AddNewLecturerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		// Simple expression to find a valid e-mail address in a file
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
		
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
		
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		Classroom currentClassroom = connection.classroomDB.getClassroom(classroomId);
		
		String emails[] = email.split("\\s");
		ArrayList<String> goodEmails = new ArrayList<String>();
		
		for(String e:emails){
			Matcher mathcer = pattern.matcher(e.toUpperCase());
			if(mathcer.matches()){
				if(currentClassroom.classroomAddLecturer(e)){
					goodEmails.add(e);
				}
			}
		}
		
		if(goodEmails.size()>0){
			String subject = "Macs Classroom: You added in a classroom as a Lecturer";
			String text ="Macs Classroom: You added in a classroom: " + currentClassroom.getClassroomName() +
					" as a Lecturer" +"\nGo to the lick:\n" +
					"http://localhost:8080/MACS-classroom/stream.jsp?" + 
					Classroom.ID_ATTRIBUTE_NAME + "=" + currentClassroom.getClassroomID();
			new MailConnector(goodEmails, subject, text);
		}
	}

}
