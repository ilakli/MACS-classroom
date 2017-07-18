package ConcurrentClasses;

import java.io.File;

import defPackage.MyDrive;

public class AssignmentUploadConcurrent implements Runnable {
	
	private MyDrive service;
	private String studentEmail;
	private File fileToUpload;
	private String fileType;
	private String classroomId;
	private String assignmentName;
	
	public AssignmentUploadConcurrent(MyDrive service, String studentEmail, File fileToUpload, String fileType,
			String classroomId, String assignmentName) {
		this.service = service;
		this.studentEmail = studentEmail;
		this.fileToUpload = fileToUpload;
		this.fileType = fileType;
		this.classroomId = classroomId;
		this.assignmentName = assignmentName;
	}
	
	public void run() {
		service.uploadAssignment(studentEmail, fileToUpload, fileType, classroomId, assignmentName);
		fileToUpload.delete();
	}
}