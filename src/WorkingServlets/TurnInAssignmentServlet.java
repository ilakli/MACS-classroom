package WorkingServlets;

import java.io.File;
import java.io.IOException;
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
		String classroomID = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		String assignmentTitle = request.getParameter("assignmentTitle");
		String studentEmail = request.getParameter("studentEmail");
		String fileName = "";
		String numreschedulings = request.getParameter("numreschedulings");

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
					} else if (fieldName.equals("studentEmail")){
						studentEmail = item.getString();
					} 
				}
			}

		} catch (Exception ex) {}
		
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		
		String personID = connection.personDB.getPersonId(studentEmail);
		
		connection.studentAssignmentDB.turnInAssignment(classroomID, personID, assignmentTitle, fileName);
		
		if(numreschedulings!=null && !numreschedulings.equals("")){
			int nRes = Integer.parseInt(numreschedulings);
			System.out.println(nRes + " nRes");
			for(int i = 0; i < nRes; i++){
				connection.studentDB.useRescheduling(studentEmail, classroomID);
			}
		}
		
		response.sendRedirect("studentsOneAssignment.jsp?"+Classroom.ID_ATTRIBUTE_NAME + "=" + classroomID+
				"&studentEmail="+studentEmail+"&assignmentTitle="+assignmentTitle+"&status=done");
	}

}
