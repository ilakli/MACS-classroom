package WorkingServlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Listeners.ContextListener;
import database.AllConnections;
import defPackage.Classroom;
import defPackage.MyDrive;
import defPackage.Person;
import defPackage.Section;
import defPackage.Seminar;

/**
 * Servlet implementation class ShowSectionAssignments
 */
@WebServlet("/ShowSectionAssignments")
public class ShowSectionAssignments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowSectionAssignments() {
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
		System.out.println("entered in servlet############");
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		String assignmentID = request.getParameter("assignmentID");
		String personId = request.getParameter("personID");
			
		AllConnections connection = (AllConnections)request.getServletContext()				
				.getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
		MyDrive service = (MyDrive) request.getServletContext().getAttribute("drive");
		
		Person currentPerson =  connection.personDB.getPerson(personId);
		Classroom currentClassroom = connection.classroomDB.getClassroom(classroomID);
		String assignmentTitle = connection.assignmentDB.getAssignment(assignmentID).getTitle();
		
		String link = "";
		
		if(currentClassroom.classroomSectionLeaderExists(currentPerson.getEmail())){
			Section section = connection.sectionDB.getSectionByLeader(currentPerson, classroomID);
			List<Person> sectionStudents = section.getSectionStudents();
			service.copyAssignmentsToSectionLeader(sectionStudents, assignmentTitle, 
					currentPerson.getEmail(), classroomID);
			link = service.getSectionLeaderFolderLink(classroomID, currentPerson.getEmail(), assignmentTitle);
		}
		
		response.sendRedirect(link);
	}

}
