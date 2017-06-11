package defPackage;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import EditingServlets.EditStatusConstants;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 800 * 1024;
	private int maxMemSize = 500 * 1024;
	private File file;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() {
		// Get the file location where it would be stored.
		filePath = System.getProperty("user.dir");
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
		
		String classroomId = "";
		
		isMultipart = ServletFileUpload.isMultipartContent(request);

		if (!isMultipart) {
			return;
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(maxMemSize);
		factory.setRepository(new File("c:\\temp"));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxFileSize);

		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			Iterator<FileItem> i = fileItems.iterator();

			for(FileItem item : fileItems) {
				
				if (!item.isFormField()) {

					String fileName = item.getName();

					if (fileName.lastIndexOf("\\") >= 0) {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
					} else {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
					}
					item.write(file);
				}else {
					classroomId = item.getString();
				}
			}
		
			
		} catch (Exception ex) {
			
		}
		
		String address = "about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId;
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
	
		dispatcher.forward(request, response);
	}

}
