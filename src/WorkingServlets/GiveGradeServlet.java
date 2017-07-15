package WorkingServlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AllConnections;
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
		String studentID = request.getParameter("studentId");
		String classroomID = request.getParameter("classroomId");
		String assignmentTitle = request.getParameter("assignmentTitle");
		String grade = request.getParameter("newGrade");
		String studentEmail = request.getParameter("studentEmail");
		String isSeminaris = request.getParameter("isSeminarist");
				
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute("connection");
		ArrayList<String> emails = new ArrayList<String>();
		emails.add(studentEmail);
		String classroomName = "ba";
		String subject = "Macs classroom: Assignment " + assignmentTitle + " in Classroom " + classroomName + " was graded.";
		String mailText = "Your grade in this assignment is " + grade;
		MailConnector mail = new MailConnector(emails, subject, mailText);
		mail.sendMail();
		connection.studentAssignmentDB.setStudnetAssignmentGrade(classroomID, studentID, assignmentTitle, grade, isSeminaris);
		
		String link = String.format("studentsOneAssignment.jsp?classroomID=%s&studentEmail=%S&assignmentTitle=%s", 
				classroomID, studentEmail ,assignmentTitle);
		response.sendRedirect(link);
	}

}
