package defPackage;

import java.util.Date;

public class Post {
	private String postId;
	private String classroomId;
	private String personId;
	private String postText;
	private Date postDate;

	public Post(String postId, String classroomId, String personId, String postText, Date postDate) {
		this.postId = postId;
		this.classroomId = classroomId;
		this.personId = personId;
		this.postText = postText;
		this.postDate = postDate;
	}

	/**
	 * Method for getting the id associated with this post
	 * 
	 * @return returns the id associated with this post.
	 */
	public String getPostId() {
		return this.postId;
	}

	/**
	 * Method for getting the id of classroom associated with this post
	 * 
	 * @return returns the classroom id associated with this post.
	 */
	public String getClassroomId() {
		return this.classroomId;
	}

	/**
	 * Method for getting the id of person associated with this post
	 * 
	 * @return returns the person id associated with this post.
	 */
	public String getPersonId() {
		return this.personId;
	}

	/**
	 * Method for getting the text associated with this post
	 * 
	 * @return returns the text associated with this post.
	 */
	public String getPostText() {
		return this.postText;
	}
	
	/**
	 * This method gets time when the post was added
	 * 
	 * @return date of the post.
	 */
	public Date getPostDate() {
		return this.postDate;
	}
}
