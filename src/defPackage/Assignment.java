package defPackage;

public class Assignment {
	private String classroomID;
	private String assignmentName;
	private String assignmentTitle;
	private String assignmentInstructions;
	
	public Assignment(String classroomID, String assignmentName,
						String assignmentTitle, String assignmentInstructions) {
		this.assignmentName = assignmentName;
		this.classroomID = classroomID;
		this.assignmentTitle = assignmentTitle;
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
	public String getName() {
		return this.assignmentName;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classroomID == null) ? 0 : classroomID.hashCode());
		result = prime * result + ((assignmentName == null) ? 0 : assignmentName.hashCode());
		result = prime * result + ((assignmentTitle == null) ? 0 : assignmentTitle.hashCode());
		result = prime * result + ((assignmentInstructions == null) ? 0 : assignmentInstructions.hashCode());
		return result;
	}

}
