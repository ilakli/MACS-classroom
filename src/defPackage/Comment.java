package defPackage;

import java.util.Date;

public class Comment {
	private String commentID;
	private String postID;
	private String personID;
	private String commentText;
	private Date commentDate;
	
	public Comment(String commentID, String postID, String personID, String commentText, Date commentDate){
		this.commentID = commentID;
		this.postID = postID;
		this.personID = personID;
		this.commentText = commentText;
		this.commentDate = commentDate;
	}
	
	/**
	 * @return comment ID
	 */
	public String getCommentID(){
		return commentID;
	}
	
	/**
	 * @return post ID
	 */
	public String getPostID(){
		return postID;
	}
	
	/**
	 * @return person(author) ID
	 */
	public String getPersonID(){
		return personID;
	}
	
	/**
	 * @return comment text
	 */
	public String getCommentText(){
		return commentText;
	}
	
	/**
	 * This method returns time when the comment was added;
	 * @return - date of the comment;
	 */
	public Date getCommentDate(){
		return this.commentDate;
	}
	
}
