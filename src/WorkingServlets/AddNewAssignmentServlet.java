package WorkingServlets;

import java.io.File;
import java.io.IOException;
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

import Dummys.PersonGeneratorDummy;
import Listeners.ContextListener;
import database.AllConnections;
import defPackage.Classroom;

/**
 * Servlet implementation class AddNewAssignmentServlet
 */
@WebServlet("/AddNewAssignmentServlet")
public class AddNewAssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String filePath;
	private int maxFileSize = 800 * 1024;
	private int maxMemSize = 500 * 1024;
	private File file;
	
	
	public void init(ServletConfig config) {
		// Get the file location where it would be stored.
		filePath = "";
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		String assignmentTitle = request.getParameter("assignmentTitle");
		String assignmentInstructions = request.getParameter("assignmentInstructions");
		AllConnections connection = (AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
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
		
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			
			for (FileItem item : fileItems) {

				if (!item.isFormField()) {
					fileName = item.getName();

					if (fileName.lastIndexOf("\\") >= 0) {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
					} else {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
					}
					item.write(file);
				} else {
					//?classroomId = item.getString();
				}
			}

		} catch (Exception ex) {}
		
		doGet(request, response);
		//connection.postDB.addPost(classroomId, personId, postText);
		//response.sendRedirect("assignments.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
