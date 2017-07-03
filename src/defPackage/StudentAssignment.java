package defPackage;

import java.util.Date;

public class StudentAssignment {
	private String classroomID;
	private String personID;
	private String assignmentTitle;
	private String fileName;
	
	public StudentAssignment(String classroomID, String personID, String assignmentTitle, String fileName){
		this.classroomID = classroomID;
		this.assignmentTitle = assignmentTitle;
		this.personID = personID;
		this.fileName = fileName;
	}
	
	
}
