package defPackage;


public class Comment {
	String commentID;
	String postID;
	String personID;
	String commentText;
	
	public Comment(String commentID, String postID, String personID, String commentText){
		this.commentID = commentID;
		this.postID = postID;
		this.personID = personID;
		this.commentText = commentText;
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
	
}
