package defPackage;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentAssignment {
	private String ID;
	private String classroomID;
	private String personID;
	private String assignmentTitle;
	private Integer assignmentGrade;
	private boolean isApproved;
	
	public StudentAssignment(String ID,String classroomID, String personID, String assignmentTitle,
			Integer assignmentGrade, boolean isApproved){
		this.ID = ID;
		this.classroomID = classroomID;
		this.assignmentTitle = assignmentTitle;
		this.personID = personID;
		this.assignmentGrade = assignmentGrade;
		this.isApproved = isApproved;
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
	public Integer getAssignmentGrade(){
		return this.assignmentGrade;
	}
	
	/**
	 * @return - if grade is was already approved by seminarist
	 */
	public boolean getApproval() {
		return this.isApproved;
	}
}
