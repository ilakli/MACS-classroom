package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Post;

public class PostDB {

	private DBConnection db;
	
	public PostDB() {
		db = new DBConnection();
	}

	/**
	 * 
	 * @param classroomId
	 * @param personId
	 * @param postText
	 * @return
	 */
	public boolean addPost(String classroomId, String personId, String postText) {
		String query = String.format(
				"insert into `classroom_posts` (`classroom_id`, `person_id`, `post_text`) values(%s, %s, '%s');",
				classroomId, personId, postText);

		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	public ArrayList<Post> getPosts(String classroomId) {
		String query = String.format("select * from `classroom_posts` where `classroom_id` = %s;", classroomId);

		MyConnection myConnection = db.getMyConnection(query);
		ResultSet rs = myConnection.executeQuery();
		ArrayList<Post> posts = new ArrayList<Post>();
		try {
			while (rs != null && rs.next()) {
				posts.add(new Post(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return posts;
	}
}
