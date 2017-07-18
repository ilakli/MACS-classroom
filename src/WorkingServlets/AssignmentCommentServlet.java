package WorkingServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Listeners.ContextListener;
import database.AllConnections;
import defPackage.Assignment;
import defPackage.Classroom;
import defPackage.MailConnector;
import defPackage.Person;
import defPackage.StudentAssignment;

/**
 * Servlet implementation class AssignmentCommentServlet
 */
@WebServlet("/AssignmentCommentServlet")
public class AssignmentCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignmentCommentServlet() {
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
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
		
		PrintWriter pw = response.getWriter();
		pw.println(new Date().toString());
		String studentAssignmentId = request.getParameter("studentAssignmentId");
		String personId = request.getParameter("personId");
		Person sender = connection.personDB.getPerson(personId);
		String commentText = request.getParameter("commentText");
		String isStaffComment = request.getParameter("isStaffComment");
		
		StudentAssignment studentAssignment = connection.studentAssignmentDB.getStudentAssignment(studentAssignmentId);
		Assignment assignment = connection.assignmentDB.getAssignment(studentAssignment.getAssignmentID());
		String sectionLeaderEmail = connection.studentDB.getSectionLeaderEmail(studentAssignment.getClassroomID(), 
				studentAssignment.getPersonId());
		String seminaristEmail = connection.studentDB.getSeminaristEmail(studentAssignment.getClassroomID(), 
				studentAssignment.getPersonId());
		Person student = connection.personDB.getPerson(studentAssignment.getPersonId());
		Classroom currentClass = connection.classroomDB.getClassroom(studentAssignment.getClassroomID());
		ArrayList<String> emails  = new ArrayList <String>(); 
		
		if(!sectionLeaderEmail.equals(sender.getEmail()) && !sectionLeaderEmail.equals("")){
			emails.add(sectionLeaderEmail);
		}
		if(!seminaristEmail.equals(sender.getEmail()) && !seminaristEmail.equals("")){
			emails.add(seminaristEmail);
		}
		
		
		String subject = "Macs classroom: In the classroom-" + currentClass.getClassroomName() + ", assignment-" +
				assignment.getTitle() +" you have new comment."; 
		String link = String.format("\nhttp://localhost:8080/MACS-classroom/studentsOneAssignment.jsp?classroomID=%s"+
				"&studentID=%S&assignmentID=%s",
				studentAssignment.getClassroomID(), student.getPersonID() ,studentAssignment.getAssignmentID());
		
		String mailCommentText = sender.getName()+ " " +sender.getSurname() + " commented:\n" +
				commentText +"Go to the Link:\n" + link;
		
		if (isStaffComment.equals("true")){			
			connection.commentDB.addStudentAssignmentStaffComment(studentAssignmentId, personId, commentText);
			if(!emails.isEmpty()){
				new MailConnector(emails, subject, mailCommentText);				
			}
		} else {
			if(!student.getEmail().equals(sender.getEmail())){
				emails.add(student.getEmail());
			}
			if(!emails.isEmpty()){
				new MailConnector(emails, subject, mailCommentText);				
			}
			connection.commentDB.addStudentAssignmentComment(studentAssignmentId, personId, commentText);
		}		
	}

}
