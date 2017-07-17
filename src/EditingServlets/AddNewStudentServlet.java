package EditingServlets;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");		
		String email = request.getParameter("email");
				
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		Classroom currentClassroom = connection.classroomDB.getClassroom(classroomId);
		
		System.out.println("taken: " + email);
		
		String emails[] = email.split("\\s+"); 
		
				
		for(String e:emails){
			Matcher mathcer = pattern.matcher(e.toUpperCase());
			if(mathcer.matches()){	
				connection.personDB.addPersonByEmail(e);
				currentClassroom.classroomAddStudent(e);
			}
		}
		
		if(currentClassroom.areSectionsAudoDistributed()){
			currentClassroom.fillSectionsWithFreeStudents();
		}
		
		if(currentClassroom.areSeminarsAudoDistributed()){
			currentClassroom.fillSeminarsWithFreeStudents();
		}		
	}

}
