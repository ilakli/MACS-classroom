package EditingServlets;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.drive.model.File;

import database.AllConnections;
import defPackage.Classroom;
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
		
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
		MyDrive service = (MyDrive) request.getServletContext().getAttribute("drive");
		
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		Classroom currentClassroom = connection.classroomDB.getClassroom(classroomId);
		
		String emails[] = email.split("\\s"); 
		
		boolean status = true;
		if(emails.length == 0) status = false;
		
		for(String e:emails){
			connection.personDB.addPersonByEmail(e);
			status = currentClassroom.classroomAddSeminarist(e);
			if (status) {
				String folderId = connection.driveDB.getClassroomFolder(classroomId);

				int atIndex = e.indexOf("@");
				if (atIndex == -1) atIndex = e.length();
				String mailPrefix = e.substring(0, atIndex);
				String seminaristFolderId = service.createFolder(mailPrefix, folderId);
				connection.driveDB.addSeminaristFolder(classroomId, e, seminaristFolderId);
			}
		}
		
		if(status){
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
					+ EditStatusConstants.ADD_NEW_SEMINARIST_ACC);	
			
			view.forward(request, response);  
		} else {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
					+ EditStatusConstants.ADD_NEW_SEMINARIST_REJ);	
						 
			view.forward(request, response);  
		}
	
	}

}
