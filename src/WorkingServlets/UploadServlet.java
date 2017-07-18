package WorkingServlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
	private int maxFileSize = 100000 * 1024;
	private int maxMemSize = 100000 * 1024;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) {
		// Get the file location where it would be stored.
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
		
		isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			return;
		}
		
		String classroomId = "";
		String categoryId = "";

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(maxMemSize);
		factory.setRepository(new File("c:\\temp"));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxFileSize);
		ArrayList <FileToUpload> filesToUpload = new ArrayList <FileToUpload> ();
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			
			for (FileItem item : fileItems) {
				if (!item.isFormField()) {
					String filePath = item.getName();
					String fileType = item.getContentType();					
					String fileName = filePath.substring(filePath.lastIndexOf('\\') + 1);

					File file;
					
					if (filePath.lastIndexOf("\\") >= 0) {
						file = new File(filePath + filePath.substring(filePath.lastIndexOf("\\")));
					} else {
						file = new File(filePath + filePath.substring(filePath.lastIndexOf("\\") + 1));
					}
					item.write(file);
					
					filesToUpload.add(new FileToUpload(file, fileType, fileName));

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
		MaterialDB materialDB = connection.materialDB;		
		
		final String categoryName = connection.categoryDB.getCategoryName(classroomId, categoryId);
		final String categoryFolder = connection.driveDB.getCategoryFolder(classroomId, categoryName);
		final String CLASSROOM_ID = classroomId;
		final String CATEGORY_ID = categoryId;
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (FileToUpload fileToUpload: filesToUpload) {
					
					materialDB.addMaterial(CLASSROOM_ID, CATEGORY_ID, fileToUpload.getName());
					myDrive.uploadFile(fileToUpload.getName(), fileToUpload.getFile(), fileToUpload.getFileType(), categoryFolder);
					fileToUpload.getFile().delete();
				}				
			}
		}).start();
		
		String address = "about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId;
		// RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		response.sendRedirect(address);
		// dispatcher.forward(request, response);
	}
	
	public class FileToUpload {
		private String fileName;
		private String fileType;
		private File fileToUpload;
		
		public FileToUpload(File fileToUpload, String fileType, String fileName) {
			this.fileName = fileName;
			this.fileType = fileType;
			this.fileToUpload = fileToUpload;
		}
		
		private String getName() {
			return fileName;
		}
		
		private String getFileType() {
			return fileType;
		}
		
		private File getFile() {
			return fileToUpload;
		}
	}

}
