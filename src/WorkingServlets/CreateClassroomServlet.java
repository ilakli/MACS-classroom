package WorkingServlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.client.json.JsonFactory;

import Listeners.ContextListener;
import database.AllConnections;
import database.DBConnection;
import defPackage.Classroom;
import defPackage.MailConnector;
import defPackage.MyDrive;

/**
 * Servlet implementation class CreateClassroomServlet
 */
@WebServlet("/CreateClassroomServlet")
public class CreateClassroomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateClassroomServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String className = request.getParameter("newClassroomName");
		String lecturerId = request.getParameter("lecturerID");
		String lecturerEmail = request.getParameter("lecturerEmail");

		AllConnections db = (AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
		
		String classroomID = db.classroomDB.addClassroom(className,lecturerId);
		
		if(!classroomID.equals(DBConnection.DATABASE_ERROR)){
			db.lecturerDB.addLecturer(lecturerEmail, classroomID);
			MyDrive service = ((MyDrive) request.getServletContext().getAttribute("drive"));
			
			String folderId = service.createFolder("Classroom#" + classroomID + "#" + className);
			db.driveDB.addClassroomFolder(classroomID, folderId);
			
			service.createFolder("Assignments", folderId);
			service.createFolder("Students Assignments", folderId);
		}
		
		if(classroomID.equals( DBConnection.DATABASE_ERROR)){
			response.sendRedirect("stream.jsp?" +
					Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID);
		} else {
			ArrayList<String> emails = new ArrayList<String>();
			emails.add(lecturerEmail);
			String subject = "Macs Classroom: You have created new classroom: " + className;
			String mailText = "You have created new classroom " + className + ";\nYou can see link here:\n " +
					"http://localhost:8080/MACS-classroom/stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID;
			MailConnector mail = new MailConnector(emails, subject, mailText);
			mail.sendMail();
			response.sendRedirect("stream.jsp?" +
					Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID);
		}
		
	}

}
