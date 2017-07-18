package ConcurrentClasses;

import java.io.File;

import defPackage.MyDrive;

public class FileUploadConcurrent implements Runnable {

	private MyDrive service;
	private String assignmentName;
	private File file;
	private String fileType;
	private String assignmentFolderId;
	private String classroomId;
	
	public FileUploadConcurrent(MyDrive service, String assignmentName, File file, String fileType, String assignmentFolderId, String classroomId) {
		this.service = service;
		this.assignmentName = assignmentName;
		this.file = file;
		this.fileType = fileType;
		this.assignmentFolderId = assignmentFolderId;
		this.classroomId = classroomId;
	}
	
	@Override
	public void run() {
		service.uploadFile(assignmentName, file, fileType, assignmentFolderId);
		String studentsAssignmentsFolderId = service.getStudentsAssignmentsFolderId(classroomId);
		service.createFolder(assignmentName, studentsAssignmentsFolderId);
	}
}
