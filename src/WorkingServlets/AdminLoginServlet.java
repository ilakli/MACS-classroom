package WorkingServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Listeners.ContextListener;
import database.AllConnections;
import defPackage.Classroom;
import defPackage.Person;

/**
 * Servlet implementation class AdminLoginServlet
 */
@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
		
		if (username.equals("admin") && password.equals("admin")) {
			Person person = connection.personDB.getPersonByEmail("admin@admin.admin");
			
			if(person == null){
				System.out.println("no admin");
				connection.personDB.addPerson("admin", "admin", "admin@admin.admin");
				person = connection.personDB.getPersonByEmail("admin@admin.admin");
				
			}else {
				System.out.println("admin");
				if(person.getName() == null) {
					System.out.println("person.getName==null");
					person.setNameAndSurname("admin", "admin");
				}
			}
			
			request.getSession().setAttribute("currentPerson", person);
			
			
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
		} else {
			RequestDispatcher dispatch = request.getRequestDispatcher("homepage.html");
			dispatch.forward(request, response);
		}
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


}
