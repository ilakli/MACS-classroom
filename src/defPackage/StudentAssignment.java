package defPackage;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentAssignment {
	private String ID;
	private String classroomID;
	private String personID;
	private String assignmentTitle;
	private int assignmentGrade;
	
	public StudentAssignment(String ID,String classroomID, String personID, String assignmentTitle, int assignmentGrade){
		this.ID = ID;
		this.classroomID = classroomID;
		this.assignmentTitle = assignmentTitle;
		this.personID = personID;
		this.assignmentGrade = assignmentGrade;
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
}
