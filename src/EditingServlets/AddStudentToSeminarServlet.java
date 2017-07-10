package EditingServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import defPackage.Classroom;
import database.AllConnections;
import defPackage.Person;
import defPackage.Section;
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
		System.out.println("Already Here");
		int seminarN = Integer.parseInt(request.getParameter("seminarN"));
		String studentEmail = request.getParameter("studentEmail");
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
			
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		
		Seminar currentSeminar = new Seminar(seminarN,classroomId,connection);
		
		String emails[] = studentEmail.split("\\s+"); 
		System.out.println(classroomId + " " + studentEmail + " " + seminarN +" WE ARE GOOD");
		boolean status = true;
		if(emails.length == 0) status = false;
		
		for(String e:emails){  
			
			if(connection.seminarDB.seminarExists(seminarN, classroomId)  
					&& connection.studentDB.studentExists(e, classroomId)
					&& currentSeminar.addStudentToSeminar(e)) {
				
				System.out.println("Added Student To Section: " + currentSeminar.getSeminarN() + " " + e + 
					" to class with id: " + classroomId);
			}
			else {
				status =  false;
					
				System.out.println("Didn't add Student to Section: " + currentSeminar.getSeminarN() + " " + e + 
						" to class with id: " + classroomId);
			}
		} 
		
		if(status){
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
				+ EditStatusConstants.ADD_STUDENT_TO_SECTION_ACC);	
							 
			view.forward(request, response);     
		} else {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
					+ EditStatusConstants.ADD_STUDENT_TO_SECTION_REJ);	
							 
			view.forward(request, response);  
		}
	}

}
