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
 * Servlet implementation class DeleteSeminarServlet
 */
@WebServlet("/DeleteSeminarServlet")
public class DeleteSeminarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteSeminarServlet() {
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
		String name = request.getParameter("name");
		
		DBConnection  connection = (DBConnection)request.getServletContext().getAttribute("connection");
			
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		
		Classroom currentClassroom = connection.getClassroom(classroomId);
		if(currentClassroom.classroomDeleteSeminar(name)) {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
					+ EditStatusConstants.DEL_SEMINAR_ACC);	
						 
			view.forward(request, response);  
			System.out.println("Deleted Seminar: " + name + " to class with id: " + classroomId);
		}
		else {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
					+ EditStatusConstants.DEL_SEMINAR_REJ);	
						 
			view.forward(request, response);  
			
			System.out.println("Seminar Didn't Exist IN This Classroom: " 
			+ name + "    class with id: " + classroomId);
		}
	}

}
