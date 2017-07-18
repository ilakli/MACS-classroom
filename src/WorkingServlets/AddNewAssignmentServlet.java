package WorkingServlets;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;

import ConcurrentClasses.FileUploadConcurrent;
import Dummys.PersonGeneratorDummy;
import Listeners.ContextListener;
import database.AllConnections;
import defPackage.Classroom;
import defPackage.MailConnector;
import defPackage.MyDrive;
import defPackage.Person;

/**
 * Servlet implementation class AddNewAssignmentServlet
 */
@WebServlet("/AddNewAssignmentServlet")
public class AddNewAssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String filePath = "";
	private int maxFileSize = 800 * 1024;
	private int maxMemSize = 500 * 1024;
	private File file;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classroomID = "";
		String assignmentTitle = "";
		String assignmentInstructions = "";
		
		String assignmentDeadline = "";

		filePath = request.getServletContext().getRealPath("/");
		
		System.out.println(filePath + " Is the filepath");

		if (!ServletFileUpload.isMultipartContent(request)){
			return;
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(maxMemSize);
		factory.setRepository(new File("c:\\temp"));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxFileSize);
		String fileName = "";
		String fileType = "";
		
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			
			for (FileItem item : fileItems) {

				if (!item.isFormField()) {
					fileName = item.getName();
					fileType = item.getContentType();
					
					if (fileName.lastIndexOf("\\") >= 0) {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
					} else {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
					}
					item.write(file);
				} else {
					String fieldName = item.getFieldName();
					
					if (fieldName.equals("classroomID")){
						classroomID = item.getString();
					} else if (fieldName.equals("assignmentTitle")){
						assignmentTitle = item.getString();
					} else if (fieldName.equals("assignmentInstructions")){
						assignmentInstructions = item.getString();
					} else if (fieldName.equals("deadline")){
						assignmentDeadline = item.getString();
					}
				}
			}

		} catch (Exception ex) {}
		
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
		MyDrive service = (MyDrive)request.getServletContext().getAttribute("drive");

		String assignmentFolderId = service.getAssignmentFolderId(classroomID);
		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		
		Classroom currentClassroom = connection.classroomDB.getClassroom(classroomID);
		List<Person> allPerosns = connection.classroomDB.ClassroomAllPersons(currentClassroom.getClassroomID());
		ArrayList<String> emails  = new ArrayList <String>(); 
		for(Person person : allPerosns){
			emails.add(person.getEmail());
		}
		String subject ="Macs Classroom: New Assignment in the classroom: " + currentClassroom.getClassroomName();
		String mailText ="New Assignment added in the classroom: " + currentClassroom.getClassroomName() +
				"\nGo to the Link:\nhttp://localhost:8080/MACS-classroom/" + "assignments.jsp?" 
				+ Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID;
		if(!emails.isEmpty()){
			new MailConnector(emails, subject, mailText);			
		}
		
		connection.assignmentDB.addAssignment(classroomID,  assignmentTitle, assignmentInstructions, assignmentDeadline, fileName);
		new Thread(new FileUploadConcurrent(service, assignmentTitle, file, fileType, assignmentFolderId, classroomID)).start();
//		service.uploadFile(assignmentTitle, file, fileType, assignmentFolderId);

		response.sendRedirect("assignments.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
