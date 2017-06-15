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
import defPackage.Seminar;

/**
 * Servlet implementation class AddStudentToSeminarServlet
 */
@WebServlet("/AddStudentToSeminarServlet")
public class AddStudentToSeminarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStudentToSeminarServlet() {
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
		int seminarN = Integer.parseInt(request.getParameter("seminarN"));
		String studentEmail = request.getParameter("studentEmail");
		DBConnection  connection = (DBConnection)request.getServletContext().getAttribute("connection");
			
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		
		Seminar currentSeminar = new Seminar(seminarN,classroomId);
		Person student = connection.getPersonByEmail(studentEmail);
		if(currentSeminar.addStudentToSeminar(student)) {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
					+ EditStatusConstants.ADD_STUDENT_TO_SEMINAR_ACC);	
						 
			view.forward(request, response);  
			System.out.println("Added Student To Seminar: " + currentSeminar.getSeminarN() + " " + studentEmail + 
					" to class with id: " + classroomId);
		}
		else {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
					+ EditStatusConstants.ADD_STUDENT_TO_SEMINAR_REJ);	
						 
			view.forward(request, response);  
			
			System.out.println("Didn't add Student to Seminar: " + currentSeminar.getSeminarN() + " " + studentEmail + 
					" to class with id: " + classroomId);
	
		}
	}

}
