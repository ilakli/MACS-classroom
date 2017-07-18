package WorkingServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dummys.PersonGeneratorDummy;
import Listeners.ContextListener;
import database.AllConnections;
import defPackage.Classroom;
import defPackage.MailConnector;
import defPackage.Person;

/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostServlet() {
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
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		String postText = request.getParameter("postText");
		String personId = ((Person)request.getSession().getAttribute("currentPerson")).getPersonID();
		
		if(postText != null && !postText.equals("")){
			AllConnections connection = (AllConnections)request.getServletContext()				
					.getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
			Classroom currentClassroom = connection.classroomDB.getClassroom(classroomId);
			Person sender = connection.personDB.getPerson(personId);
			List<Person> allPerosns = connection.classroomDB.ClassroomAllPersons(currentClassroom.getClassroomID());
			ArrayList<String> emails  = new ArrayList <String>(); 
			for(Person person : allPerosns){
				if(!person.getEmail().equals(sender.getEmail())){
					emails.add(person.getEmail());
				}
			}
			
			Person currentPerson =  connection.personDB.getPerson(personId);
			
			String subject = "Macs Classroom: " + currentPerson.getName() + " " + currentPerson.getSurname() +
					" Posted in Classroom - " + currentClassroom.getClassroomName();
			String mailText = postText + "\nGo to the Link: \n" + 
					"http://localhost:8080/MACS-classroom/stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId;
			if(!emails.isEmpty()){
				new MailConnector(emails, subject, mailText);				
			}				
			connection.postDB.addPost(classroomId, personId, postText);
		//response.sendRedirect("stream.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId);
		}
	}
}
