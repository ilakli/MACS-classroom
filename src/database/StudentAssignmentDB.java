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
	private int getStudentAssignmentID(String classroomID, String personID, String assignmentID){
		String query = String.format("select * from `student_assignments` where `classroom_id` = %s and "
				+ "person_id = %s and `assignment_title` = '%s';", classroomID, personID, assignmentID );
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
	 * @param assignmentID - ID of the assignment;
	 * @return - true if connection was added, false otherwise;
	 */
	public boolean addStudentAssignment(String classroomID, String personID, String assignmentID, 
			String deadlineWithReschedulings){
		String query = String.format("insert into `student_assignments` ( `classroom_id`, `person_id`," +
				 "`assignment_id` ) values (%s, %s, %s );", 
				 classroomID, personID, assignmentID, deadlineWithReschedulings);
		
		MyConnection myConnection = db.getMyConnection(query);
		
		
		if(!db.executeUpdate(myConnection)) return false;
		if(!deadlineWithReschedulings.equals("")) {
			return changeDeadlineWithReschedulings(deadlineWithReschedulings, classroomID, personID, assignmentID);
		}
		return true;
		
		

	}
	
	/**
	 * This method adds new file for the student and assignment;
	 * @param classroomID
	 * @param personID
	 * @param assignmentTitle
	 * @param fileName
	 * @return
	 */
	public boolean turnInAssignment(String classroomID, String personID, String assignmentID, String fileName){
		int student_assignment_id = getStudentAssignmentID(classroomID, personID, assignmentID);
		if(student_assignment_id == -1){
			return false;
		}			
		String query = String.format("insert into `student_uploaded_assignments`( `student_assignment_id`, "
				+ " `file_name`) "
				+ "values (%s, '%s');", student_assignment_id, fileName);
		
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
		
	}
	
	/**
	 * This method gets connection between student and assignment;
	 * @param classroomID - id of the classroom;
	 * @param personID - id of the person;
	 * @param assignmentID - ID of the assignment
	 * @return - connection between student and assignment;
	 */
	public StudentAssignment getStudentAssignment(String classroomID, String personID, String assignmentID){
		String query = String.format("select * from `student_assignments` where `classroom_id` = %s and "
				+ "person_id = %s and `assignment_id` = %s;", classroomID, personID, assignmentID );
		
		MyConnection myConnection = db.getMyConnection(query);
		StudentAssignment assignment = null;
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				Date deadlineWithReschedulings = null;
				java.sql.Date sqlDate =rs.getDate("deadline_with_reschedulings");

				if(sqlDate!=null) {
					deadlineWithReschedulings = new java.util.Date(sqlDate.getTime());
				}
				String id = rs.getString("student_assignment_id");
				boolean isApproved = rs.getBoolean("assignment_approved");
				String assignmentGrade = rs.getString("assignment_grade");
				if (rs.wasNull()) assignmentGrade = null;
				
				assignment = new StudentAssignment(id,classroomID, personID,assignmentID, 
						 assignmentGrade, isApproved, deadlineWithReschedulings);
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
	 * This method returns StudentAssignment object from it's id; 
	 * @param studentAssignmentId - id of the StudentAssignment;
	 * @return - object of StudentAssignment;
	 */
	public StudentAssignment getStudentAssignment(String studentAssignmentId){
		String query = String.format("select * from `student_assignments` where  "
				+ "`student_assignment_id` = %s ;", studentAssignmentId );
		
		MyConnection myConnection = db.getMyConnection(query);
		StudentAssignment assignment = null;
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				Date deadlineWithReschedulings = null;
				java.sql.Date sqlDate =rs.getDate("deadline_with_reschedulings");

				if(sqlDate!=null) {
					deadlineWithReschedulings = new java.util.Date(sqlDate.getTime());
				}
				String id = rs.getString("student_assignment_id");
				String classroomID = rs.getString("classroom_id");
				String personID = rs.getString("person_id");
				String assignmentID = rs.getString("assignment_id");
				boolean isApproved = rs.getBoolean("assignment_approved");
				String assignmentGrade = rs.getString("assignment_grade");
				if (rs.wasNull()) assignmentGrade = null;
				
				assignment = new StudentAssignment(id,classroomID, personID,assignmentID, 
						 assignmentGrade, isApproved, deadlineWithReschedulings);
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
	
	/**
	 * 
	 * @param deadlineWithReschedulings - string with new deadline after rescheduling
	 * @param classroomID
	 * @param personID
	 * @param assignmentID
	 * @return - true if deadline has been changed successfully, false otherwise
	 */
	public boolean changeDeadlineWithReschedulings(String deadlineWithReschedulings, String classroomID, String personID, String assignmentID){
		if(deadlineWithReschedulings != null &&!deadlineWithReschedulings.equals("") ){
				String query1 = String.format("update `student_assignments` set `deadline_with_reschedulings` = '%s' "
						+ "where `classroom_id` = %s and `assignment_id` = %s  and `person_id` = %s; ", 
						deadlineWithReschedulings, classroomID, assignmentID, personID);
				MyConnection myConnection = db.getMyConnection(query1);
				return db.executeUpdate(myConnection);
			}
		else return false;
	}
	
	/**
	 * This method changed students grade in database;
	 * @param classroomID - id of the classroom;
	 * @param personID - id of the person;
	 * @param assignmentID - id of the assignment;
	 * @param grade - new grade for this assignment;
	 * @return 
	 */
	public boolean setStudnetAssignmentGrade(String classroomID, String personID, String assignmentID, String grade, String isSeminarist){
		String query1 = String.format("update `student_assignments` set `assignment_grade` = '%s' , `assignment_approved` = %s "
				+ "where `classroom_id` = %s and `assignment_id` = %s  and `person_id` = %s; ", 
				grade, isSeminarist,classroomID, assignmentID, personID);
		
		MyConnection myConnection = db.getMyConnection(query1);
		return db.executeUpdate(myConnection);
	}
}
