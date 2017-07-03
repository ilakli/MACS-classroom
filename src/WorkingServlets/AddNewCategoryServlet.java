package WorkingServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Listeners.ContextListener;
import database.AllConnections;
import database.CategoryDB;
import defPackage.Classroom;

/**
 * Servlet implementation class AddNewCategoryServlet
 */
@WebServlet("/AddNewCategoryServlet")
public class AddNewCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewCategoryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Came In Add Category Servlet");
		
		String categoriesAsString = request.getParameter("categories");
		String categories[] = categoriesAsString.split("#"); 
		String classroomId = request.getParameter(Classroom.ID_ATTRIBUTE_NAME);
		
		System.out.println("Atts are: " + categoriesAsString + " " + classroomId);
	
		CategoryDB categoryDB = ((AllConnections)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME)).categoryDB;
		
		boolean status = true;
		
		for(int i=0;i<categories.length;i++){
			categoryDB.addCategory(classroomId, categories[i]);
		}
		
		response.sendRedirect("about.jsp?" + Classroom.ID_ATTRIBUTE_NAME + "=" + classroomId);
		
		
	}

}
