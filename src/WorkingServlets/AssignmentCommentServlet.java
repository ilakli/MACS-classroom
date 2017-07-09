package WorkingServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Listeners.ContextListener;
import database.AllConnections;

/**
 * Servlet implementation class AssignmentCommentServlet
 */
@WebServlet("/AssignmentCommentServlet")
public class AssignmentCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignmentCommentServlet() {
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
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
		
		PrintWriter pw = response.getWriter();
		pw.println(new Date().toString());
		String studentAssignmentId = request.getParameter("studentAssignmentId");
		String personId = request.getParameter("personId");
		String commentText = request.getParameter("commentText");
		String isStaffComment = request.getParameter("isStaffComment"); 
		
		if (isStaffComment.equals("true")){
			connection.commentDB.addStudentAssignmentStaffComment(studentAssignmentId, personId, commentText);
		} else {
			connection.commentDB.addStudentAssignmentComment(studentAssignmentId, personId, commentText);
		}
		
	}

}
