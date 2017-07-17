package WorkingServlets;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AllConnections;
import database.LecturerDB;
import database.PersonDB;

/**
 * Servlet implementation class AddNewGlobalLecturerServlet
 */
@WebServlet("/AddNewGlobalLecturerServlet")
public class AddNewGlobalLecturerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddNewGlobalLecturerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
		
		AllConnections connection = (AllConnections) request.getServletContext().getAttribute("connection");
		
		PersonDB personDB = connection.personDB;
		LecturerDB lecturerDB = connection.lecturerDB;

		String emails[] = email.split("\\s+");

		for (int i = 0; i < emails.length; i++) {
			Matcher mathcer = pattern.matcher(emails[i].toUpperCase());
			if(mathcer.matches()){
				personDB.addPersonByEmail(emails[i]);
				lecturerDB.addGlobalLecturer(emails[i]);
			}
		}

		response.sendRedirect("addLecturer.html");
	}

}
