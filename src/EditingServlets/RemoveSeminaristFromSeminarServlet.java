package EditingServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AllConnections;
import defPackage.Classroom;
import defPackage.Person;

/**
 * Servlet implementation class RemoveSeminaristFromSeminarServlet
 */
@WebServlet("/RemoveSeminaristFromSeminarServlet")
public class RemoveSeminaristFromSeminarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveSeminaristFromSeminarServlet() {
        super();
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
		String classroomId = request.getParameter("classroomID");
		int seminarN = Integer.parseInt(request.getParameter("seminarN"));
		
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");

		String seminarId = connection.seminarDB.getSeminarId(seminarN, classroomId);
		Person p = connection.seminaristDB.getSeminarist(seminarId);
		assert (p != null);
		connection.seminaristDB.deleteSeminaristFromSeminar(p.getEmail(), classroomId, seminarId);

		response.sendRedirect(String.format("seminars.jsp?%s=%s", 
				Classroom.ID_ATTRIBUTE_NAME, classroomId));
	}

}
