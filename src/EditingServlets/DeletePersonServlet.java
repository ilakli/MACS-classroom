package EditingServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		AllConnections connection = (AllConnections) request.getServletContext().getAttribute("connection");

		
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);

		String position = request.getParameter("position");
		Classroom classroom = connection.classroomDB.getClassroom(classroomId);
		
		Enumeration<String> emails = request.getParameterNames();
		
		if(position.equals("lecturer")){
			while (emails.hasMoreElements()) {
				String nextParameterName = emails.nextElement();
				if (nextParameterName.equals(Classroom.ID_ATTRIBUTE_NAME))
					continue;
				String e = request.getParameter(nextParameterName);
				classroom.classroomDeleteLecturer(e);
			}
		}else if(position.equals("seminarist")){
			while (emails.hasMoreElements()) {
				String nextParameterName = emails.nextElement();
				if (nextParameterName.equals(Classroom.ID_ATTRIBUTE_NAME))
					continue;
				String e = request.getParameter(nextParameterName);
				classroom.classroomDeleteSeminarist(e);
			}
		}else if(position.equals("sectionLeader")){
			while (emails.hasMoreElements()) {
				String nextParameterName = emails.nextElement();
				if (nextParameterName.equals(Classroom.ID_ATTRIBUTE_NAME))
					continue;
				String e = request.getParameter(nextParameterName);
				classroom.classroomDeleteSectionLeader(e);
			}
		}else if(position.equals("student")){
			while (emails.hasMoreElements()) {
				String nextParameterName = emails.nextElement();
				if (nextParameterName.equals(Classroom.ID_ATTRIBUTE_NAME))
					continue;
				String e = request.getParameter(nextParameterName);
				classroom.classroomDeleteStudent(e);
			}
		}
		
		response.sendRedirect("people.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId);

	}

}
