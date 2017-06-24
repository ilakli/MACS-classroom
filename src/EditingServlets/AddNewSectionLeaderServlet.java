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
 * Servlet implementation class AddNewSectionLeaderServlet
 */
@WebServlet("/AddNewSectionLeaderServlet")
public class AddNewSectionLeaderServlet extends HttpServlet {
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
		
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
		
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		Classroom currentClassroom = connection.classroomDB.getClassroom(classroomId);
		
		String emails[] = email.split("\\s"); 
		
		boolean status = true;
		if(emails.length == 0) status = false;
		
		for(String e:emails){
			connection.personDB.addPersonByEmail(e);
			status = currentClassroom.classroomAddSectionLeader(e);
		}  
		
		if(status){
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
				+ EditStatusConstants.ADD_NEW_SECTION_LEADER_ACC);	
						 
			view.forward(request, response);  
		} else {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
				+ EditStatusConstants.ADD_NEW_SECTION_LEADER_REJ);	
						 
			view.forward(request, response);  
		}
		
		
	}

}
