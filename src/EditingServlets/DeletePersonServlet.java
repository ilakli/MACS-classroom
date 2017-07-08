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

		System.out.println("Deleting");
		// String email = request.getParameter("email");

		AllConnections connection = (AllConnections) request.getServletContext().getAttribute("connection");

		System.out.println("Deleting2");
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);

		Classroom classroom = connection.classroomDB.getClassroom(classroomId);
		System.out.println("Deleting3");
		Enumeration<String> emails = request.getParameterNames();
		// String emails[] = email.split("\\s+");

		boolean status = true;
		System.out.println("Deleting4 " + emails.hasMoreElements());
		if (!emails.hasMoreElements()) {
			System.out.print("Bad Motherfucker");
			status = false;
		}

		System.out.println("Deleting5 " + emails.hasMoreElements());
		while (emails.hasMoreElements()) {
			System.out.print("Here");
			String nextParameterName = emails.nextElement();
			System.out.println("Param: " + nextParameterName);
			if (nextParameterName.equals(Classroom.ID_ATTRIBUTE_NAME))
				continue;

			String e = request.getParameter(nextParameterName);
			System.out.println("E is: " + e);
			if (classroom.classroomDeleteLecturer(e) || classroom.classroomDeleteSectionLeader(e)
					|| classroom.classroomDeleteSeminarist(e) || classroom.classroomDeleteStudent(e)) {
				System.out.println("Deleted person: " + " " + e + " to class with id: " + classroomId);
			} else {
				status = false;

				System.out.println(
						"Person didn't exist IN This Classroom: " + " " + e + "    class with id: " + classroomId);
			}
		}
		System.out.println("Deleting6");
		response.sendRedirect("people.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId);

	}

}
