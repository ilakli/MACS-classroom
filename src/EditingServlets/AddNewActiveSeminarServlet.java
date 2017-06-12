package EditingServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import defPackage.Classroom;
import defPackage.DBConnection;

/**
 * Servlet implementation class AddNewActiveSeminar
 */
@WebServlet("/AddNewActiveSeminarServlet")
public class AddNewActiveSeminarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewActiveSeminarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		System.out.println("AddNewActiveSeminarServlet");
		String activeSeminarName = request.getParameter("activeSeminarName");
		String seminarName = request.getParameter("seminarName");
		String time = request.getParameter("seminarTime");
		String location = request.getParameter("seminarLocation");
		
		DBConnection  connection = (DBConnection)request.getServletContext().getAttribute("connection");
			
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		Classroom currentClassroom = connection.getClassroom(classroomId);
		if(currentClassroom.classroomAddActiveSeminar(activeSeminarName, seminarName, time, location)) {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
					+ EditStatusConstants.ADD_NEW_ACTIVE_SEMINAR_ACC);	
						 
			view.forward(request, response);  
			
			System.out.println("Added Active Seminar: " + activeSeminarName+ " " + 
					seminarName + " " + time + " " + location + " to class with id: " + classroomId);
		}
		else {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
					+ EditStatusConstants.ADD_NEW_ACTIVE_SEMINAR_REJ);	
						 
			view.forward(request, response);  
			
			System.out.println("Seminar Already Existed IN This Classroom: "+ activeSeminarName+ " " + 
					seminarName + " " + time+ " " + location + " to class with id: " + classroomId);
		}
	}

}