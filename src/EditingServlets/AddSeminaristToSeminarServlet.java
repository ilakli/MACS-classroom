package EditingServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AllConnections;

import defPackage.Classroom;
import defPackage.Person;
import defPackage.Seminar;

/**
 * Servlet implementation class AddSeminaristToSeminarServlet
 */
@WebServlet("/AddSeminaristToSeminarServlet")
public class AddSeminaristToSeminarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddSeminaristToSeminarServlet() {
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
		String seminaristEmail = request.getParameter("seminaristEmail");
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
		
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		
		Seminar currentSeminar = new Seminar(seminarN,classroomId,connection);
		if(connection.seminaristDB.seminaristExists(seminaristEmail,classroomId) 
				&& connection.seminarDB.seminarExists(seminarN,classroomId)
				&& currentSeminar.setSeminarist(seminaristEmail)) {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
				+ EditStatusConstants.ADD_SEMINARIST_TO_SEMINAR_ACC);	
						 
			view.forward(request, response);  
			System.out.println("Added seminarist To Seminar: " + currentSeminar.getSeminarN() + " " + seminaristEmail + 
				" to class with id: " + classroomId);
		}
		else {
			RequestDispatcher view = request.getRequestDispatcher("edit.jsp?"+EditStatusConstants.STATUS +"="
				+ EditStatusConstants.ADD_SEMINARIST_TO_SEMINAR_REJ);	
						 
			view.forward(request, response);  
			
			System.out.println("Didn't add seminarist to Seminar: " + currentSeminar.getSeminarN() + " " + seminaristEmail + 
				" to class with id: " + classroomId);
		}
	
	}

}
