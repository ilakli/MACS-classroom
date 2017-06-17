package EditingServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dummys.PersonGeneratorDummy;
import database.AllConnections;
import defPackage.Classroom;
import defPackage.Person;


/**
 * Servlet implementation class AddNewStudentServlet
 */
@WebServlet("/AddNewStudentServlet")
public class AddNewStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		String email = request.getParameter("email");
		Person p = PersonGeneratorDummy.createPersonByEmail(email);
		
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
		
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		
		Classroom currentClassroom = connection.classroomDB.getClassroom(classroomId);
		if(currentClassroom.classroomAddStudent(email)) {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
		+ EditStatusConstants.ADD_NEW_STUDENT_ACC);	
			 
			view.forward(request, response);    
			
			System.out.println("Added Student: " + p.getName() + " " + p.getSurname() + " " + email + " to class with id: " + classroomId);
		}
		else {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
		+ EditStatusConstants.ADD_NEW_STUDENT_REJ);	
			
			view.forward(request, response);    
			
			System.out.println("Person Already Existed IN This Classroom: " 
			+ p.getName() + " " + p.getSurname() + " " + email + " to class with id: " + classroomId);
		}
	}

}
