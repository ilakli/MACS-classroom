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
 * Servlet implementation class AddNewLecturerServlet
 */
@WebServlet("/AddNewLecturerServlet")
public class AddNewLecturerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		
		
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
		
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		Classroom currentClassroom = connection.classroomDB.getClassroom(classroomId);
		
		
		String emails[] = email.split("\\s"); 
		
		boolean status = true;
		if(emails.length == 0) status = false;
		
		for(String e:emails){  
			Person p = PersonGeneratorDummy.createPersonByEmail(e);
			if(currentClassroom.classroomAddLecturer(e)) {
				System.out.println("Added Lecturer: " + p.getName() + " " + 
						p.getSurname() + " " + e + " to class with id: " + classroomId);
			}
			else {
				status = false;
				
				System.out.println("Person Already Existed IN This Classroom: " +  
						p.getName() + " " +  p.getSurname() + " " + e + "    class with id: " + classroomId);
			}
		}  
		
		if(status){
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
				+ EditStatusConstants.ADD_NEW_LECTURER_ACC);	
						 
			view.forward(request, response);   
		} else {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
				+ EditStatusConstants.ADD_NEW_LECTURER_REJ);	
						 
			view.forward(request, response);   
		
		}

			  
		
	}

}
