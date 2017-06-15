package WorkingServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Listeners.ContextListener;
import defPackage.Classroom;
import defPackage.DBConnection;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		System.out.println("It Really Does The Request");
		String postId = request.getParameter("postId");
		String personId = request.getParameter("personId");
		String commentText = request.getParameter("commentText");
		
		System.out.println("They look like this: " + postId + personId + commentText);
		
		DBConnection connection = (DBConnection)request.getServletContext().getAttribute(ContextListener.CONNECTION_ATTRIBUTE_NAME);
		
		connection.addPostComment(postId, personId, commentText);
		out.print("a");
		
	}

}
