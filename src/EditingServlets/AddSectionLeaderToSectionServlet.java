package EditingServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AllConnections;
import defPackage.Classroom;
import defPackage.Person;
import defPackage.Section;

/**
 * Servlet implementation class AddSectionLeaderToSectionServlet
 */
@WebServlet("/AddSectionLeaderToSectionServlet")
public class AddSectionLeaderToSectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddSectionLeaderToSectionServlet() {
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
		int sectionN = Integer.parseInt(request.getParameter("sectionN"));
		String sectionLeaderEmail = request.getParameter("sectionLeaderEmail");
		
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
	
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		
		Section currentSection = new Section(sectionN,classroomId);
		Person leader = connection.personDB.getPersonByEmail(sectionLeaderEmail);
		if(connection.sectionLeaderDB.sectionLeaderExists(sectionLeaderEmail, classroomId)
				&& connection.sectionDB.sectionExists(sectionN, classroomId)
				&& currentSection.setSectionLeader(leader)) {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
				+ EditStatusConstants.ADD_SECTION_LEADER_TO_SECTION_ACC);	
						 
			view.forward(request, response);  
			System.out.println("Added sectionLeader To Section: " +currentSection.getSectionN() + " " + sectionLeaderEmail + 
				" to class with id: " + classroomId);
		}
		else {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
				+ EditStatusConstants.ADD_SECTION_LEADER_TO_SECTION_REJ);	
						 
			view.forward(request, response);  
			
			System.out.println("Didn't add sectionLeader to Section: " + currentSection.getSectionN() + " " + sectionLeaderEmail + 
				" to class with id: " + classroomId);
	
		}
	}

}
