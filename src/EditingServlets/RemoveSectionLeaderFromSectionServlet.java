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
 * Servlet implementation class RemoveSectionLeaderFromSectionServlet
 */
@WebServlet("/RemoveSectionLeaderFromSectionServlet")
public class RemoveSectionLeaderFromSectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveSectionLeaderFromSectionServlet() {
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
		int sectionN = Integer.parseInt(request.getParameter("sectionN"));
		
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
		
		String sectionId = connection.sectionDB.getSectionId(sectionN, classroomId);
		Person p = connection.sectionLeaderDB.getSectionLeader(sectionId);
		assert (p != null);
		connection.sectionLeaderDB.deleteSectionLeaderFromSection(p.getEmail(), classroomId, sectionId);
		
		response.sendRedirect(String.format("http://localhost:8080/MACS-classroom/editSectionsAndSeminars.jsp?%s=%s", 
				Classroom.ID_ATTRIBUTE_NAME, classroomId));
	}

}
