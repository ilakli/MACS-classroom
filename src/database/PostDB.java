package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import database.DBConnection.MyConnection;
import defPackage.Assignment;
import defPackage.Post;

public class PostDB {

	private DBConnection db;
	
	public PostDB(AllConnections allConnections) {
		db = allConnections.db;
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
		System.out.println("##################################");
		System.out.println("##################################");
		System.out.println("##################################");
		System.out.println("##################################");
		System.out.println("##################################");
		System.out.println("##################################");
		System.out.println(query);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	public ArrayList<Post> getPosts(String classroomId) {
		String query = String.format("select * from `classroom_posts` where `classroom_id` = %s;", classroomId);

		MyConnection myConnection = db.getMyConnection(query);
		ArrayList<Post> posts = new ArrayList<Post>();
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				Date postDate = null;
				Timestamp sqlDate =rs.getTimestamp("post_date");
				if(sqlDate!=null) {
					postDate = new java.util.Date(sqlDate.getTime());
				}
				
				posts.add(new Post(rs.getString("post_id"), rs.getString("classroom_id"),
						rs.getString("person_id"), rs.getString("post_text"), postDate ));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}

		return posts;
	}
}
