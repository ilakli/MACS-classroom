package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Comment;

public class CommentDB {
	
	private DBConnection db;
	
	public CommentDB() {
		db = new DBConnection();
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
		ResultSet rs = myConnection.executeQuery();
		
		try {
			while (rs.next()){
				Comment comment = new Comment(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4));
				comments.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return comments;
	}
}
