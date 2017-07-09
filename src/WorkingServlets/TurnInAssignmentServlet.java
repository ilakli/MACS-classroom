package WorkingServlets;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import Listeners.ContextListener;
import database.AllConnections;
import defPackage.Classroom;
import defPackage.StudentAssignment;

/**
 * Servlet implementation class TurnInAssignmentServlet
 */
@WebServlet("/TurnInAssignmentServlet")
public class TurnInAssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String filePath = "";
	private int maxFileSize = 800 * 1024;
	private int maxMemSize = 500 * 1024;
	private File file;
       
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HEREEE BROOOOOO");
		String classroomID = "";
		String assignmentTitle = "";
		String studentEmail = "";
		String fileName = "";
		String numReschedulings = "";
		

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
		
		
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			
			for (FileItem item : fileItems) {

				if (!item.isFormField()) {
					System.out.println("file not null");
					fileName = item.getName();
					
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
						System.out.println(classroomID + "  classroomID");
					} else if (fieldName.equals("assignmentTitle")){
						assignmentTitle = item.getString();
						System.out.println(assignmentTitle + "  assignmentTitle");
					} else if (fieldName.equals("studentEmail")){
						studentEmail = item.getString();
					}  else if (fieldName.equals("numReschedulings")){
						numReschedulings = item.getString();
						System.out.println("==============================");
						System.out.println("numresched: " + numReschedulings);
						System.out.println("==============================");
					} 
				}
			}

		} catch (Exception ex) {}
		
		System.out.println("==============================");
		System.out.println("assignment title: " + assignmentTitle);
		System.out.println("==============================");
		
		
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		
		String personID = connection.personDB.getPersonId(studentEmail);
		
		connection.studentAssignmentDB.turnInAssignment(classroomID, personID, assignmentTitle, fileName);

		if(numReschedulings!=null && !numReschedulings.equals("")){
			int nRes = Integer.parseInt(numReschedulings);
			System.out.println(nRes + " nRes");
			
			if(nRes != 0){
				for(int i = 0; i < nRes; i++){
					connection.classroomDB.useRescheduling(studentEmail, classroomID);
				}
				
				Classroom currentClassroom = connection.classroomDB.getClassroom(classroomID);
				StudentAssignment assignment = connection.studentAssignmentDB.getStudentAssignment(
						classroomID, personID, assignmentTitle);
				
				Calendar c = Calendar.getInstance();
				c.setTime(assignment.getDeadlineWithReschedulings());
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				c.add(Calendar.DATE, nRes * currentClassroom.getReschedulingLength() );  
	
				String newDate = format1.format(c.getTime());
				
				assignment.changeDeadlineWithReschedulings(newDate);
			
			}
		}
		
		response.sendRedirect("studentsOneAssignment.jsp?"+Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID+
				"&studentEmail="+studentEmail+"&assignmentTitle="+assignmentTitle+"&status=done");
	}

}
