package defPackage;

import java.util.Date;

public class StudentAssignment {
	private String classroomID;
	private String personID;
	private String assignmentTitle;
	private String fileName;
	private int assignmentGrade;
	
	public StudentAssignment(String classroomID, String personID, String assignmentTitle, String fileName, int assignmentGrade){
		this.classroomID = classroomID;
		this.assignmentTitle = assignmentTitle;
		this.personID = personID;
		this.fileName = fileName;
		this.assignmentGrade = assignmentGrade;
	}
	
	
	/**
	 * @return - ID of classroom
	 */
	public String getClassroomID() {
		return this.classroomID;
	}
	
	/**
	 * @return - name of assignment file
	 */
	public String getFileName() {
		return this.fileName;
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
