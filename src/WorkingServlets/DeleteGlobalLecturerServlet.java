package WorkingServlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AllConnections;
import defPackage.Classroom;

/**
 * Servlet implementation class DeleteGlobalLecturerServlet
 */
@WebServlet("/DeleteGlobalLecturerServlet")
public class DeleteGlobalLecturerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteGlobalLecturerServlet() {
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
		System.out.println("Deleting");
		

		AllConnections connection = (AllConnections) request.getServletContext().getAttribute("connection");

		Enumeration<String> emails = request.getParameterNames();

		System.out.println("Deleting5 " + emails.hasMoreElements());
		while (emails.hasMoreElements()) {
			System.out.print("Here");
			String nextParameterName = emails.nextElement();
			System.out.println("Param: " + nextParameterName);

			String e = request.getParameter(nextParameterName);
			System.out.println("E is: " + e);
			
			
			connection.lecturerDB.deleteGlobalLecturer(e);
		}
		System.out.println("Deleting6");
		response.sendRedirect("index.jsp");

	}

}
