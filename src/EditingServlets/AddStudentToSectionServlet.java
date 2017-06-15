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
		int sectionN = Integer.parseInt(request.getParameter("sectionN"));
		String studentEmail = request.getParameter("studentEmail");
		DBConnection  connection = (DBConnection)request.getServletContext().getAttribute("connection");
			
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		
		Section currentSection = new Section(sectionN,classroomId);
		Person student = connection.getPersonByEmail(studentEmail);
		if(currentSection.addStudentToSection(student)) {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
					+ EditStatusConstants.ADD_STUDENT_TO_SECTION_ACC);	
						 
			view.forward(request, response);  
			System.out.println("Added Student To Section: " + currentSection.getSectionN() + " " + studentEmail + 
					" to class with id: " + classroomId);
		}
		else {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
					+ EditStatusConstants.ADD_STUDENT_TO_SECTION_REJ);	
						 
			view.forward(request, response);  
			
			System.out.println("Didn't add Student to Section: " + currentSection.getSectionN() + " " + studentEmail + 
					" to class with id: " + classroomId);
	
		}
	
	}

}
