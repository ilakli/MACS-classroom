package defPackage;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files.Copy;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

import database.AllConnections;
import database.DriveDB;

import javax.servlet.http.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

public class MyDrive {

	private static String CLIENT_ID = "548672842662-t9cr8fb2l6288ikja6367v4ck3drlk3j.apps.googleusercontent.com";
	private static String CLIENT_SECRET = "EMXr3ltR8h3UaHOZxeqKWs0k";
	public static final String GOOGLE_SHAREABLE_LINK = "https://drive.google.com/open?id=";
	private Drive service;
	private AllConnections allConnections;
	private JsonBatchCallback<Permission> callback;

	public MyDrive() throws IOException {

		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET,
				Arrays.asList(new String[] { DriveScopes.DRIVE }))
				.setAccessType("online").setApprovalPrompt("auto").build();

		Credential credential = new AuthorizationCodeInstalledApp(
	            flow, new LocalServerReceiver()).authorize("ipopk15@freeuni.edu.ge");
		
		// Create a new authorized API client
		Drive service = new Drive.Builder(httpTransport, jsonFactory,
				credential).build();

		this.service = service;
		
		allConnections = new AllConnections();
		
		callback = new JsonBatchCallback<Permission>() {
		    @Override
		    public void onFailure(GoogleJsonError e,
		                          HttpHeaders responseHeaders)
		            throws IOException {
		        // Handle error
		        System.err.println(e.getMessage());
		    }

		    @Override
		    public void onSuccess(Permission permission,
		                          HttpHeaders responseHeaders)
		            throws IOException {
		        System.out.println("Permission ID: " + permission.getId());
		    }
		};
	}
	
	/**
	 * 
	 * @return drive service
	 */

	public Drive getDrive() {
		return service;
	}
	
	/**
	 * 
	 * @param folderName
	 * @return id of newly created folder
	 */
	
	public String createFolder (String folderName) {
		File fileMetaData = new File();
		fileMetaData.setName(folderName);
		fileMetaData.setMimeType("application/vnd.google-apps.folder");
		
		File folder = null;
		try {
			folder = service.files().create(fileMetaData)
					.setFields("id")
					.execute();
			Permission userPermission = new Permission().setType("anyone").setRole("writer");
			BatchRequest batch = service.batch();
			service.permissions().create(folder.getId(), userPermission).queue(batch, callback);
			batch.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return folder != null ? folder.getId() : "";
	}

	/**
	 * 
	 * @param checkerFolderId
	 * @param assignmentName
	 * @return - either seminarists or section leaders given assignments folder
	 */
	
	private String getCheckerFolderLink (String checkerFolderId, String assignmentName) {
		String folderLink = "";
		try {
			FileList fl = service.files().list().setQ(String.format("'%s' in parents", checkerFolderId)).execute();
			folderLink = GOOGLE_SHAREABLE_LINK + findFileId(fl, assignmentName);
		} catch (IOException e) {
		}
		return folderLink;
	}
	
	/**
	 * 
	 * @param classroomId
	 * @param sectionLeaderEmail
	 * @param assignmentName
	 * @return - section leaders shareable folder link with all its students works
	 */
	
	public String getSectionLeaderFolderLink (String classroomId, String sectionLeaderEmail, String assignmentName) {
		String sectionLeaderFolderId = allConnections.driveDB.getSectionLeaderFolder(classroomId, sectionLeaderEmail);
		String link = getCheckerFolderLink (sectionLeaderFolderId, assignmentName);
		
		return link;
	}
	
	/**
	 * 
	 * @param classroomId
	 * @param seminaristEmail
	 * @param assignmentName
	 * @return - seminarist shareable folder link with all its students works
	 */
	public String getSeminaristFolderLink (String classroomId, String seminaristEmail, String assignmentName) {
		String seminaristFolderId = allConnections.driveDB.getSeminaristFolder(classroomId, seminaristEmail);
		String link = getCheckerFolderLink (seminaristFolderId, assignmentName);
		
		return link;
	}
	
	/**
	 * 
	 * @param classroomId
	 * @param assignmentName
	 * @return - shareable link of all students works on given assignment
	 */
	
	public String getLecturerFolderLink (String classroomId, String assignmentName) {
		String studentsAssignmentsFolder = getStudentsAssignmentsFolderId(classroomId);
		String lecturerFolderLink = "";
		try {
			FileList fl = service.files().list()
					.setQ(String.format("'%s' in parents", studentsAssignmentsFolder))
					.execute();
			String currentAssignmentFolderId = findFileId(fl, assignmentName);
			lecturerFolderLink = GOOGLE_SHAREABLE_LINK + currentAssignmentFolderId;
		} catch (IOException e) {
		}
		return lecturerFolderLink;
	}
	
	/**
	 * finds file in FileList with given name
	 * @param fl
	 * @param fileName
	 * @return fileId if file is present, "" otherwise
	 */
	
	private String findFileId (FileList fl, String fileName) {
		String result = "";
		for (File f: fl.getFiles()) {
			if (f.getName().equals(fileName)) {
				result = f.getId();
				break;
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param email
	 * @return prefix of given mail
	 */
	
	private String getMailPrefix (String email) {
		int atIndex = email.indexOf("@");
		if (atIndex == -1) {
			atIndex = email.length();
		}
		return email.substring(0, atIndex);
	}
	
	/**
	 * copies assignments to either seminarist or section leader
	 * @param students
	 * @param checkerFolder
	 * @param classroomId
	 * @param assignmentName
	 */
	
	private void copyAssignmentsToChecker(java.util.List<Person> students, String checkerFolder, String classroomId, String assignmentName) {
		String studentsAssignmentsFolderId = getStudentsAssignmentsFolderId(classroomId);
		HashSet <String> studentEmailPrefixes = new HashSet <String>();
		for (Person student: students) {
			String mailPrefix = getMailPrefix(student.getEmail());
			studentEmailPrefixes.add(mailPrefix);
		}
		
		try {
			FileList fl = service.files().list()
					.setQ(String.format("'%s' in parents", checkerFolder))
					.execute();
			
			String currentAssignmentFolderId = findFileId(fl, assignmentName);
			if (!currentAssignmentFolderId.equals("")) {
				service.files().delete(currentAssignmentFolderId).execute();
				service.files().emptyTrash().execute();
			}
			currentAssignmentFolderId = createFolder(assignmentName, checkerFolder);
			
			fl = service.files().list()
					.setQ(String.format("'%s' in parents", studentsAssignmentsFolderId))
					.execute();
			String studentsCurrentAssignmentFolder = findFileId(fl, assignmentName);
			
			fl = service.files().list()
					.setQ(String.format("'%s' in parents", studentsCurrentAssignmentFolder))
					.execute();

			for (File f: fl.getFiles()) {

				if (studentEmailPrefixes.contains(f.getName())) {
					
					FileList fl2 = service.files().list()
							.setQ(String.format("'%s' in parents", currentAssignmentFolderId))
							.execute();
					
					String currentStudentFolder = findFileId(fl2, f.getName());
					if (currentStudentFolder.equals("")) {
						currentStudentFolder = createFolder(f.getName(), currentAssignmentFolderId);
					}
					
					fl2 = service.files().list()
							.setQ(String.format("'%s' in parents", f.getId()))
							.execute();
					
					for (File f2: fl2.getFiles()) {
						
						File copiedFile = new File();
						copiedFile.setName(f2.getName());
						copiedFile.setParents(Collections.singletonList(currentStudentFolder));
						
						service.files().copy(f2.getId(), copiedFile).execute();
					}
				}
			}
		} catch (IOException e) {
		}
	}
	
	/**
	 * copies assignments to seminarist
	 * @param seminarStudents
	 * @param assignmentName
	 * @param seminaristEmail
	 * @param classroomId
	 */
	
	public void copyAssignmentsToSeminarist (java.util.List<Person> seminarStudents, String assignmentName, String seminaristEmail, String classroomId) {
		
		String seminaristFolder = allConnections.driveDB.getSeminaristFolder(classroomId, seminaristEmail);
		copyAssignmentsToChecker(seminarStudents, seminaristFolder, classroomId, assignmentName);
	}
	
	/**
	 * copies assignments to sectionLeader
	 * @param sectionStudents
	 * @param assignmentName
	 * @param sectionLeaderEmail
	 * @param classroomId
	 */
	
	public void copyAssignmentsToSectionLeader (java.util.List<Person> sectionStudents, String assignmentName, String sectionLeaderEmail, String classroomId) {

		String sectionLeaderFolder = allConnections.driveDB.getSectionLeaderFolder(classroomId, sectionLeaderEmail);
		copyAssignmentsToChecker(sectionStudents, sectionLeaderFolder, classroomId, assignmentName);		
	}
	
	/**
	 * creates folder in parentFolder 
	 * @param folderName
	 * @param parentFolderId
	 * @return new folders id
	 */
	
	public String createFolder (String folderName, String parentFolderId) {
		
		File fileMetaData = new File();
		fileMetaData.setName(folderName);
		fileMetaData.setMimeType("application/vnd.google-apps.folder");
		fileMetaData.setParents(Collections.singletonList(parentFolderId));
		
		File folder = null;
		try {
			folder = service.files().create(fileMetaData)
					.setFields("id, parents")
					.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return folder != null ? folder.getId() : "";
	}

	/**
	 * uploads given file to given folder
	 * @param fileName
	 * @param fl
	 * @param fileType
	 * @param folderId
	 */
	
	public void uploadFile (String fileName, java.io.File fl, String fileType, String folderId) {
		try {
			
			String mimeType = fileType;
			
			File file = new File();
			file.setName(fileName);
			file.setMimeType(mimeType);
			file.setParents(Collections.singletonList(folderId));

			java.io.File fileToUpload = fl;
			FileContent fileToUploadContent = new FileContent(mimeType, fileToUpload);
			File fileInFolder = service.files().create(file, fileToUploadContent)
						.setFields("id, parents")
						.execute();

			Permission userPermission = new Permission().setType("anyone").setRole("writer");
			BatchRequest batch = service.batch();
			service.permissions().create(fileInFolder.getId(), userPermission).queue(batch, callback);
			batch.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param classroomId
	 * @return 'Assignments' folder id
	 */
	
	public String getAssignmentFolderId (String classroomId) {
		String classroomFolder = allConnections.driveDB.getClassroomFolder(classroomId);
		String assignmentFolderId = "";
		try {
			FileList fl = service.files().list().setQ(String.format("'%s' in parents", classroomFolder)).execute();
			assignmentFolderId = findFileId(fl, "Assignments");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return assignmentFolderId;
	}
	
	/**
	 * 
	 * @param classroomId
	 * @param assignmentName
	 * @param studentEmail
	 * @return ready html with links to students assignments
	 */
	
	public ArrayList<String> getHtmlForStudentUploads (String classroomId, String assignmentName, String studentEmail) {
		
		ArrayList<String> result = new ArrayList<String>();
		String studentsAssignmentsFolderId = getStudentsAssignmentsFolderId(classroomId);
		
		try {
			FileList fl = service.files().list().setQ(String.format("'%s' in parents", studentsAssignmentsFolderId)).execute();

			String assignmentFolderId = findFileId(fl, assignmentName);
			if (assignmentFolderId.equals("")) {
				return result;
			}

			fl = service.files().list().setQ(String.format("'%s' in parents", assignmentFolderId)).execute();

			String mailPrefix = getMailPrefix(studentEmail);
			String studentFolderId = findFileId(fl, mailPrefix);
			if (studentFolderId.equals("")) {
				return result;
			}
			
			fl = service.files().list().setQ(String.format("'%s' in parents", studentFolderId)).execute();
			for (File f: fl.getFiles()) {
				result.add(String.format("<a href=\"%s%s\"> %s </a>\n", GOOGLE_SHAREABLE_LINK, f.getId(), f.getName()));
			}
		} catch (IOException e) {
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param classroomId
	 * @return 'Students Assignments' folder id
	 */
	
	public String getStudentsAssignmentsFolderId (String classroomId) {
		String classroomFolderId = allConnections.driveDB.getClassroomFolder(classroomId);
		String folderId = "";
		try {
			FileList fl = service.files().list().setQ(String.format("'%s' in parents", classroomFolderId)).execute();
			folderId = findFileId(fl, "Students Assignments");
		} catch (IOException e) {
		}
		return folderId;
	}

	/**
	 * uploads students assignment to google drive
	 * @param studentEmail
	 * @param fileToUpload
	 * @param fileType
	 * @param classroomId
	 * @param assignmentTitle
	 */
	
	public void uploadAssignment(String studentEmail, java.io.File fileToUpload, String fileType, String classroomId, String assignmentTitle) {
		String studentsAssignmentFolder = getStudentsAssignmentsFolderId(classroomId);
		String studentEmailPrefix = getMailPrefix(studentEmail);
		try {
			FileList fl = service.files().list().setQ(String.format("'%s' in parents", studentsAssignmentFolder)).execute();

			String currentAssignmentFolderId = findFileId(fl, assignmentTitle);
			if (currentAssignmentFolderId.equals("")) {
				currentAssignmentFolderId = createFolder(assignmentTitle, studentsAssignmentFolder);
			}
			
			fl = service.files().list().setQ(String.format("'%s' in parents", currentAssignmentFolderId)).execute();

			String studentFolderId = findFileId(fl, studentEmailPrefix);
			if (studentFolderId.equals("")) {
				studentFolderId = createFolder(studentEmailPrefix, currentAssignmentFolderId);
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String currentTime = dateFormat.format(date);
			
			uploadFile(studentEmailPrefix + " " + currentTime, fileToUpload, fileType, studentFolderId);

		} catch (IOException e) {
		}
	
	}
	
	/**
	 * gets file id of fileName in 'Assignments'
	 * @param classroomId
	 * @param fileName
	 * @return fileId on google drive
	 */
	
	public String getAssignmentFileId (String classroomId, String fileName) {
		String assignmentFileId = "";
		String classroomFolderId = allConnections.driveDB.getClassroomFolder(classroomId);
		
		try {
			FileList fl = service.files().list().setQ(String.format("'%s' in parents", classroomFolderId)).execute();

			String assignmentFolderId = findFileId(fl, "Assignments");
			if (assignmentFolderId.equals("")) {
				return "";
			}
			
			fl = service.files().list().setQ(String.format("'%s' in parents", assignmentFolderId)).execute();
			assignmentFileId = findFileId(fl, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return assignmentFileId;
	}
	
	/**
	 * finds material id in given category
	 * @param classroomId
	 * @param categoryName
	 * @param materialName
	 * @return materialId
	 */
	
	public String findMaterialId (String classroomId, String categoryName, String materialName) {
		String materialId = "";
		String categoryFolderId = allConnections.driveDB.getCategoryFolder(classroomId, categoryName);
		
		try {
			FileList fl = service.files().list().setQ(String.format("'%s' in parents", categoryFolderId)).execute();
			materialId = findFileId(fl, materialName);
		} catch (IOException e) {
		}
		
		return materialId;
	}
	
	public static void main(String[] args) throws IOException {
		MyDrive drv = new MyDrive();
		drv.createFolder("rachiriginda");
		System.out.println(drv.getLecturerFolderLink("1", "davaleba1"));
//		drv.uploadFile("ragaca", "C:/Users/PC/Desktop/Pj8iWKG.jpg", "0BzefYzRpjMBPQkpVLXQtS3FDbGc");
//		FileList fl = drv.service.files().list()
//				.setQ("'0BzefYzRpjMBPQkpVLXQtS3FDbGc' in parents")
//				.execute();
//		for (File f: fl.getFiles()) {
//			System.out.println(f.getName() + " " + f.getId());
//		}
	}
}
