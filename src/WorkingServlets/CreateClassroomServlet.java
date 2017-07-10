package WorkingServlets;

import java.io.IOException;

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
		System.out.println(lecturerId);
		AllConnections db = (AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
		
		String classroomID = db.classroomDB.addClassroom(className,lecturerId);
		
		if(!classroomID.equals(DBConnection.DATABASE_ERROR)){
			db.lecturerDB.addLecturer(lecturerEmail, classroomID);
			MyDrive service = ((MyDrive) request.getServletContext().getAttribute("drive"));
			
			String folderId = service.createFolder("Classroom#" + classroomID + "#" + className);
			db.driveDB.addClassroomFolder(classroomID, folderId);
//			System.out.println("Folder ID: " + folderId);
			
			service.createFolder("Assignments", folderId);
		}
		
		if(classroomID.equals( DBConnection.DATABASE_ERROR)){
			RequestDispatcher dispatch = request.getRequestDispatcher("createClassroom.jsp");
			dispatch.forward(request, response);
		} else {
			RequestDispatcher dispatch = request.getRequestDispatcher("stream.jsp?" +
					Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID);
			dispatch.forward(request, response);
		}
		
	}

}
