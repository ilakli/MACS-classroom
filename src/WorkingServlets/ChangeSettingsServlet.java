package WorkingServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AllConnections;
import defPackage.Classroom;

/**
 * Servlet implementation class ChangeSettings
 */
@WebServlet("/ChangeSettingsServlet")
public class ChangeSettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Here class info must be changed in database; 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AllConnections connector = (AllConnections) request.getServletContext().getAttribute("connection");
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		
		Classroom currentClassroom = connector.classroomDB.getClassroom(classroomId);
		String numSections = request.getParameter("sections");
		String numSeminars = request.getParameter("seminars");
		String numResch = request.getParameter("numResch");
		String lenResch = request.getParameter("lengthResch");
		String sectionDis = request.getParameter("disSSections");
		String seminarDis = request.getParameter("disSeminars");
		
		
		currentClassroom.setNumberOfSections(Integer.parseInt(numSections));
		currentClassroom.setNumberOfSeminars(Integer.parseInt(numSeminars));
		currentClassroom.setNumberOfReschedulings(Integer.parseInt(numResch));
		currentClassroom.setReschedulingLength(Integer.parseInt(lenResch));
		currentClassroom.setSectionDistribution(sectionDis != null && sectionDis.equals("on"));
		currentClassroom.setSeminarDistribution(seminarDis != null && seminarDis.equals("on"));
		
		RequestDispatcher dispatch = request.getRequestDispatcher("settings.jsp?" + 
				Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId);
		dispatch.forward(request, response);
	}

}
