package defPackage;

import java.util.Date;

public class AssignmentComment {
	
	private String studentAssignmentID;
	private String personID;
	private String commentText;
	private Date commentDate;
	
	public AssignmentComment(String studentAssignmentID, String personID, String commentText, Date commentDate){
		this.studentAssignmentID = studentAssignmentID;
		this.personID = personID;
		this.commentText = commentText;
		this.commentDate = commentDate;
	}
	
	/**
	 * @return student assignment ID
	 */
	public String studentAssignmentID(){
		return studentAssignmentID;
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
