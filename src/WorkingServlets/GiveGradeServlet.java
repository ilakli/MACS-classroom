package WorkingServlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AllConnections;
import defPackage.Assignment;
import defPackage.Classroom;
import defPackage.MailConnector;

/**
 * Servlet implementation class GiveGradeServlet
 */
@WebServlet("/GiveGradeServlet")
public class GiveGradeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GiveGradeServlet() {
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
		String studentID = request.getParameter("studentID");
		String classroomID = request.getParameter("classroomId");
		String assignmentID = request.getParameter("assignmentID");
		String grade = request.getParameter("newGrade");
		String isSeminaris = request.getParameter("isSeminarist");
		
		
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
		Assignment assignment = connection.assignmentDB.getAssignment(assignmentID);
		String studentEmail = connection.personDB.getPerson(studentID).getEmail();
		ArrayList<String> emails = new ArrayList<String>();
		emails.add(studentEmail);
		String classroomName = connection.classroomDB.getClassroom(classroomID).getClassroomName();
		String subject = "Macs classroom: Assignment " + assignment.getTitle() + " in Classroom " + classroomName + " was graded.";
		connection.studentAssignmentDB.setStudnetAssignmentGrade(classroomID, studentID, assignmentID, grade, isSeminaris);
		
		String link = String.format("studentsOneAssignment.jsp?classroomID=%s&studentID=%S&assignmentID=%s", 
				classroomID, studentID ,assignmentID);
		
		String mailText = "Your grade in this assignment is " + grade + "\nGo to the Link:\n"+
				"http://localhost:8080/MACS-classroom/"+link;
		if(!emails.isEmpty()){
			new MailConnector(emails, subject, mailText);			
		}
		response.sendRedirect(link);
	}

}
