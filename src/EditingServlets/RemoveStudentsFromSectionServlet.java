package EditingServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AllConnections;
import defPackage.Classroom;

/**
 * Servlet implementation class RemoveStudentsFromSectionServlet
 */
@WebServlet("/RemoveStudentsFromSectionServlet")
public class RemoveStudentsFromSectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveStudentsFromSectionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classroomId = request.getParameter("classroomID");
		String[] emails = request.getParameterValues("studentsEmails");
		
		AllConnections connection = (AllConnections) request.getServletContext().getAttribute("connection");
		
		if (emails != null) {
			for (String email: emails) {
				int currentSectionN = connection.sectionDB.getSection(classroomId, email).getSectionN();
				System.out.print("//////////////////" +currentSectionN +  " seec/////////////////////////");
				connection.sectionDB.removeStudent(classroomId, email);
				connection.sectionDB.updateSectionSize(1,classroomId);
			}			
		}
		
		
		System.out.println("movida aq");
		
		response.sendRedirect(String.format("sections.jsp?%s=%s", 
				Classroom.ID_ATTRIBUTE_NAME, classroomId));
	}

}
