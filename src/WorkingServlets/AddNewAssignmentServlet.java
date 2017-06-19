package WorkingServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dummys.PersonGeneratorDummy;
import Listeners.ContextListener;
import database.AllConnections;
import defPackage.Classroom;

/**
 * Servlet implementation class AddNewAssignmentServlet
 */
@WebServlet("/AddNewAssignmentServlet")
public class AddNewAssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewAssignmentServlet() {
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
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		String assignmentText = request.getParameter("assignmentText");
		
		
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
		
		//connection.postDB.addPost(classroomId, personId, postText);
		response.sendRedirect("assignments.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId);
	}

}