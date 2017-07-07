package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.DBConnection.MyConnection;
import defPackage.Assignment;
import defPackage.StudentAssignment;

public class StudentAssignmentDB {

private DBConnection db;
	
	public StudentAssignmentDB(AllConnections allConnections) {
		db = allConnections.db;
	}
	/**
	 * This method gets id of the connection between student and assignment;
	 * @param classroomID - id of the classroom;
	 * @param personID - id of the person;
	 * @param assignmentTitle - title of the assignment;
	 * @return - id from the connection between student and assignment;
	 */
	private int getStudentAssignmentID(String classroomID, String personID, String assignmentTitle){
		String query = String.format("select * from `student_assignments` where `classroom_id` = %s and "
				+ "person_id = %s and `assignment_title` = '%s';", classroomID, personID, assignmentTitle );
		System.out.println(query);
		MyConnection myConnection = db.getMyConnection(query);
		int studnetAssignmentID = -1;
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				studnetAssignmentID = rs.getInt("student_assignment_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {				
				myConnection.closeConnection();
			}
		}

		return studnetAssignmentID;
		
	}
	
	/**
	 * This method adds connection between student and assignment into database;
	 * @param classroomID - id of the classroom; 
	 * @param personID - id of the person;
	 * @param assignmentTitle - title of the assignment;
	 * @return - true if connection was added, false otherwise;
	 */
	public boolean addStudentAssignment(String classroomID, String personID, String assignmentTitle){
		String query = String.format("insert into `student_assignments` ( `classroom_id`, `person_id`," +
				 "`assignment_title`) values (%s, %s, '%s' );", classroomID, personID, assignmentTitle);
		System.out.println("DOING: " + query);
		
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}
	
	/**
	 * This method adds new file for the student and assignment;
	 * @param classroomID
	 * @param personID
	 * @param assignmentTitle
	 * @param fileName
	 * @return
	 */
	public boolean turnInAssignment(String classroomID, String personID, String assignmentTitle, String fileName){
		int student_assignment_id = getStudentAssignmentID(classroomID, personID, assignmentTitle);
		if(student_assignment_id == -1){
			return false;
		}			
		String query = String.format("insert into `student_uploaded_assignments`( `student_assignment_id`, "
				+ " `file_name`) "
				+ "values (%s, '%s');", student_assignment_id, fileName);
		System.out.println("DOING: " + query);
		
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
		
	}
	
	/**
	 * This method gets connection between student and assignment;
	 * @param classroomID - id of the classroom;
	 * @param personID - id of the person;
	 * @param assignmentTitle - title of the assignment
	 * @return - connection between student and assignment;
	 */
	public StudentAssignment getStudentAssignment(String classroomID, String personID, String assignmentTitle){
		String query = String.format("select * from `student_assignments` where `classroom_id` = %s and "
				+ "person_id = %s and `assignment_title` = '%s';", classroomID, personID, assignmentTitle );
		System.out.println(query);
		MyConnection myConnection = db.getMyConnection(query);
		StudentAssignment assignment = null;
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				
				String id = rs.getString("student_assignment_id");
				int assignmentGrade = rs.getInt("assignment_grade");
				assignment = new StudentAssignment(id,classroomID, personID,assignmentTitle, 
						 assignmentGrade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {				
				myConnection.closeConnection();
			}
		}

		return assignment;
		
	}
	
	/**
	 * This method takes all the names of the sent files from this assignment;
	 * @param student_assignment_id - id of the connection between student and assignment;
	 * @return - list of the names of the sent files from this assignment
	 */
	public List <String> getStudentSentFiles(String student_assignment_id){
		List <String> allFiles = new ArrayList <String>();
		
		String query = String.format("select * from `student_uploaded_assignments` where `student_assignment_id` = %s "
				, student_assignment_id);
		System.out.println(query);
		MyConnection myConnection = db.getMyConnection(query);
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				allFiles.add(rs.getString("file_name"));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {				
				myConnection.closeConnection();
			}
		}
		return allFiles;
	}
	
}
