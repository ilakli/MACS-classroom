package EditingServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dummys.PersonGeneratorDummy;
import defPackage.Classroom;
import database.AllConnections;
import defPackage.Person;
import defPackage.Section;

/**
 * Servlet implementation class AddStudentToSectionServlet
 */
@WebServlet("/AddStudentToSectionServlet")
public class AddStudentToSectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStudentToSectionServlet() {
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
		System.out.println("Already Here");
		int sectionN = Integer.parseInt(request.getParameter("sectionN"));
		String studentEmail = request.getParameter("studentEmail");
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
			
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		
		Section currentSection = new Section(sectionN,classroomId,connection);
		
		String emails[] = studentEmail.split("\\s+"); 

		for(String e:emails){  
			
			if(connection.sectionDB.sectionExists(sectionN, classroomId)  
					&& connection.studentDB.studentExists(e, classroomId)
					&& currentSection.addStudentToSection(e)) {
				
				connection.sectionDB.updateSectionSize(sectionN, classroomId);
			}
		
		} 
		
		
		RequestDispatcher view = request.getRequestDispatcher("sections.jsp?classroomID=" + classroomId);	
		view.forward(request, response);     
	
	}

}
