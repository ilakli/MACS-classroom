package EditingServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import defPackage.Classroom;

import database.AllConnections;;

/**
 * Servlet implementation class DeletePersonServlet
 */
@WebServlet("/DeletePersonServlet")
public class DeletePersonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeletePersonServlet() {
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
		
		String email = request.getParameter("email");

		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
		
		
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		
		Classroom classroom = connection.classroomDB.getClassroom(classroomId);
		
		String emails[] = email.split("\\s+"); 
		
		boolean status = true;
		if(emails.length == 0) status = false;
		
		for(String e:emails){  
			
			if(classroom.classroomDeleteLecturer(e) || classroom.classroomDeleteSectionLeader(e)
					|| classroom.classroomDeleteSeminarist(e) || classroom.classroomDeleteStudent(e)) {
				System.out.println("Deleted person: "  + " " + e + " to class with id: " + classroomId);
			}
			else {
				status = false;
				
				System.out.println("Person didn't exist IN This Classroom: " + " " + e + "    class with id: " + classroomId);
			}
		}  
		
		if(status){
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
				+ EditStatusConstants.DEL_PERSON_ACC);	
						 
			view.forward(request, response);   
		} else {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
				+ EditStatusConstants.DEL_PERSON_REJ);	
						 
			view.forward(request, response);   
		
		}
		
		
		
	}

}
