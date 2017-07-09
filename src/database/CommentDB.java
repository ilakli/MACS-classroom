package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.DBConnection.MyConnection;
import defPackage.Comment;
import defPackage.AssignmentComment;

public class CommentDB {
	
	private DBConnection db;
	
	public CommentDB(AllConnections allConnections) {
		db = allConnections.db;
	}
	
	/**
	 * adds comment to the post.
	 * @param postID - ID of the post
	 * @param personID - ID of person
	 * @param comment_text - comment's text
	 */
	public void addPostComment(String postID, String personID, String comment_text) {
		String query = String.format(
				"insert into `post_comments` (`post_id`, `person_id`, `comment_text`)"
				+ "values(%s, %s, '%s')", postID, personID, comment_text);
		
		MyConnection myConnection = db.getMyConnection(query);
		db.executeUpdate(myConnection);
	}
	
	/**
	 * returns list of comments of post with given ID
	 * @param postID - ID of post
	 * @return list of comments
	 */
	public ArrayList<Comment> getPostComments(String postID){
		ArrayList<Comment> comments = new ArrayList<Comment>();
		String query = String.format("select * from `post_comments` where post_id = %s;", postID);
		
		MyConnection myConnection = db.getMyConnection(query);
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs.next()){
				Date commentDate = null;
				Timestamp sqlDate =rs.getTimestamp("comment_date");
				if(sqlDate!=null) {
					commentDate = new java.util.Date(sqlDate.getTime());
				}
				Comment comment = new Comment(rs.getString("comment_id"), rs.getString("post_id"), 
						rs.getString("person_id"),rs.getString("comment_text"), commentDate);
				comments.add(comment);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		
		return comments;
	}
	
	/**
	 * adds new comment to student's assignment
	 * @param studentAssignmentId - id of student's assignment
	 * @param personId - id of comment's author
	 * @param commentText - comment text
	 */
	public void addStudentAssignmentComment(String studentAssignmentId, String personId, String commentText) {
		String query = String.format(
				"insert into `assignment_comment` (`student_assignment_id`, `person_id`, `comment_text`)"
				+ "values(%s, %s, '%s')", studentAssignmentId, personId, commentText);

		MyConnection myConnection = db.getMyConnection(query);
		db.executeUpdate(myConnection);
	}
	
	/**
	 * adds new comment to student's assignment's staff comments page
	 * @param studentAssignmentId - id of student's assignment
	 * @param personId - id of comment's author
	 * @param commentText - comment text
	 */
	public void addStudentAssignmentStaffComment(String studentAssignmentId, String personId, String commentText) {
		String query = String.format(
				"insert into `assignment_staff_comment` (`student_assignment_id`, `person_id`, `comment_text`)"
				+ "values(%s, %s, '%s')", studentAssignmentId, personId, commentText);

		MyConnection myConnection = db.getMyConnection(query);
		db.executeUpdate(myConnection);
	}
	
	/**
	 * @param studentAssignmentId - student's assignment's id
	 * @return - list of all its comments
	 */
	public List<AssignmentComment> getStudentAssignmentComments(String studentAssignmentId){
		
		ArrayList<AssignmentComment> assignmentComments = new ArrayList<AssignmentComment>();
		String query = String.format("select * from `assignment_comment`"
				+ " where student_assignment_id = %s ORDER BY comment_date;", studentAssignmentId);
		
		MyConnection myConnection = db.getMyConnection(query);
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs.next()){
				Date commentDate = null;
				Timestamp sqlDate =rs.getTimestamp("comment_date");
				if(sqlDate!=null) {
					commentDate = new java.util.Date(sqlDate.getTime());
				}
				AssignmentComment assignmentComment = new AssignmentComment(studentAssignmentId, rs.getString("person_id"),
										rs.getString("comment_text"), commentDate);
				
				assignmentComments.add(assignmentComment);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		
		return assignmentComments;	
	}
	
	
	/**
	 * @param studentAssignmentId - student's assignment's id
	 * @return - list of all its staff comments
	 */
	public List<AssignmentComment> getStudentAssignmentStaffComments(String studentAssignmentId){
		
		ArrayList<AssignmentComment> assignmentComments = new ArrayList<AssignmentComment>();
		String query = String.format("select * from `assignment_staff_comment`"
				+ " where student_assignment_id = %s ORDER BY comment_date;", studentAssignmentId);
		
		MyConnection myConnection = db.getMyConnection(query);
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs.next()){
				Date commentDate = null;
				Timestamp sqlDate =rs.getTimestamp("comment_date");
				if(sqlDate!=null) {
					commentDate = new java.util.Date(sqlDate.getTime());
				}
				AssignmentComment assignmentComment = new AssignmentComment(studentAssignmentId, rs.getString("person_id"),
										rs.getString("comment_text"), commentDate);
				
				assignmentComments.add(assignmentComment);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		
		return assignmentComments;	
	}
}
