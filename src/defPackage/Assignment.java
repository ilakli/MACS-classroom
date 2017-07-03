package defPackage;

import java.util.Date;

public class Assignment {
	private String classroomID;
	private String fileName;
	private Date assignmentDeadline;
	private String assignmentTitle;
	private String assignmentInstructions;
	
	public Assignment(String classroomID,String assignmentTitle, 
			String assignmentInstructions, Date assignmentDeadline, String fileName) {

		this.classroomID = classroomID;
		this.assignmentTitle = assignmentTitle;
		this.fileName = fileName;
		this.assignmentDeadline = assignmentDeadline;
		this.assignmentInstructions = assignmentInstructions;
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
	 * @return - assignment instructions
	 */
	public String getInstructions() {
		return this.assignmentInstructions;
	}
	
	/**
	 * @return - deadline of the assignment
	 */
	public Date getDeadline() {
		return this.assignmentDeadline;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classroomID == null) ? 0 : classroomID.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((assignmentTitle == null) ? 0 : assignmentTitle.hashCode());
		result = prime * result + ((assignmentInstructions == null) ? 0 : assignmentInstructions.hashCode());
		return result;
	}

}
