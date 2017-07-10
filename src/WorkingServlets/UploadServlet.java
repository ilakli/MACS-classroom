package WorkingServlets;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import EditingServlets.EditStatusConstants;
import Listeners.ContextListener;
import database.AllConnections;
import database.MaterialDB;
import defPackage.Classroom;
import defPackage.MyDrive;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 100000 * 1024;
	private int maxMemSize = 100000 * 1024;
	private File file;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) {
		// Get the file location where it would be stored.
		filePath = "";
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		filePath = request.getServletContext().getRealPath("/");
		System.out.println(filePath + " Is the filepath");
		String classroomId = "";
		String categoryId = "";
		isMultipart = ServletFileUpload.isMultipartContent(request);

		if (!isMultipart) {
			return;
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(maxMemSize);
		factory.setRepository(new File("c:\\temp"));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxFileSize);
		String filePath = "";
		String fileType = "";
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			
			for (FileItem item : fileItems) {
				if (!item.isFormField()) {
					filePath = item.getName();
					fileType = item.getContentType();
					
					if (filePath.lastIndexOf("\\") >= 0) {
						file = new File(filePath + filePath.substring(filePath.lastIndexOf("\\")));
					} else {
						file = new File(filePath + filePath.substring(filePath.lastIndexOf("\\") + 1));
					}
					item.write(file);

				} else {
					String fieldName = item.getFieldName();
					
					if (fieldName.equals("classroomID")){
						classroomId = item.getString();
					} else if (fieldName.equals("materialCategory")){
						categoryId = item.getString();
					}
				}
			}
		} catch (Exception ex) {

		}
		
		AllConnections connection = (AllConnections) request.getServletContext()
				.getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
		MyDrive myDrive = (MyDrive) request.getServletContext().getAttribute("drive");
		
		String fileName = filePath.substring(filePath.lastIndexOf('\\') + 1);

		System.out.println(fileName + " is fileName And classroomId is: " + classroomId + " And categoryId is: " + categoryId);

		MaterialDB materialDB = connection.materialDB;
		materialDB.addMaterial(classroomId, categoryId, fileName);
		
		String categoryName = connection.categoryDB.getCategoryName(classroomId, categoryId);
		String categoryFolder = connection.driveDB.getCategoryFolder(classroomId, categoryName);
		
		myDrive.uploadFile(fileName, file, fileType, categoryFolder);
		
		String address = "about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId;
		// RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		response.sendRedirect(address);
		// dispatcher.forward(request, response);
	}

}
