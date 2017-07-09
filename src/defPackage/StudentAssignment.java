package defPackage;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.AllConnections;

public class StudentAssignment {
	private String ID;
	private String classroomID;
	private String personID;
	private String assignmentTitle;
	private Date deadlineWithReschedulings;
	private int assignmentGrade;
	
	private AllConnections db;
	public StudentAssignment(String ID,String classroomID, String personID, String assignmentTitle, 
			Date deadlineWithReschedulings, int assignmentGrade){
		this.ID = ID;
		this.classroomID = classroomID;
		this.assignmentTitle = assignmentTitle;
		this.personID = personID;
		this.assignmentGrade = assignmentGrade;
		this.deadlineWithReschedulings = deadlineWithReschedulings;
		db = new AllConnections();
	}
	
	public String getStudentAssignmentId(){
		return this.ID;
	}
	
	/**
	 * @return - ID of classroom
	 */
	public String getClassroomID() {
		return this.classroomID;
	}
		
	/**
	 * @return - title of assignment
	 */
	public String getTitle() {
		return this.assignmentTitle;
	}
	
	/**
	 * 
	 * @return - id of the student(owner of the assignment)
	 */
	public String getPersonId(){
		return this.personID;
	}
	
	/**
	 * 
	 * @return - grade of the assignment
	 */
	public int getAssignmentGrade(){
		return this.assignmentGrade;
	}
	
	/**
	 * 
	 * @return - deadline for current student after taking rescheduling for the assignment
	 */
	public Date getDeadlineWithReschedulings(){
		return this.deadlineWithReschedulings;
	}
	
	
	/**
	 * 
	 * @param newDeadline - string with new deadline after rescheduling
	 * @return - true if deadline has been changed successfully, false otherwise
	 */
	public boolean changeDeadlineWithReschedulings(String newDeadline){
		return db.studentAssignmentDB.changeDeadlineWithReschedulings(newDeadline, classroomID, personID, assignmentTitle);
	}
}
