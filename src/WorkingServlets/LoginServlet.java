package WorkingServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Listeners.ContextListener;
import database.AllConnections;
import database.PersonDB;
import defPackage.Person;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
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
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String imgUrl = request.getParameter("image");
		System.out.println("Img Url Is: " + imgUrl);
		System.out.println("loginServlet");
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);

		
		Person person = connection.personDB.getPersonByEmail(email);
		
		if(person == null){
			System.out.println("person==null");
			connection.personDB.addPerson(firstName, lastName, email,imgUrl);
			person = connection.personDB.getPersonByEmail(email);
		}else {
			System.out.println("person!=null");
			if(person.getName() == null) {
				System.out.println("person.getName==null");
				person.setNameAndSurname(firstName, lastName);
				person.setImageUrl(imgUrl);
			}
		}
		
		request.getSession().setAttribute("currentPerson", person);	
	}

}
