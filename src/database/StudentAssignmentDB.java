package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import database.DBConnection.MyConnection;
import defPackage.Assignment;
import defPackage.StudentAssignment;

public class StudentAssignmentDB {

private DBConnection db;
	
	public StudentAssignmentDB() {
		db = new DBConnection();
	}
	
	
	public boolean turnInAssignment(String classroomID, String personID, String assignmentTitle, 
			String fileName){
		String query = String.format("insert into `student_assignments`(`classroom_id`,`person_id`,"
				+ "`assignment_title`,`file_name`) "
				+ "values (%s, %s,'%s', '%s' );", classroomID, personID, assignmentTitle, fileName);
		System.out.println("DOING: " + query);
		
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
		
	}
	
	public StudentAssignment getStudentAssignment(String classroomID, String personID, String assignmentTitle){
		String query = String.format("select * from `student_assignments` where `classroom_id` = %s and "
				+ "person_id = %s and `assignment_title` = '%s';", classroomID, personID, assignmentTitle );
		System.out.println(query);
		MyConnection myConnection = db.getMyConnection(query);
		StudentAssignment assignment = null;
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				
				String fileName = rs.getString("file_name");
				int assignmentGrade = rs.getInt("assignment_grade");
				assignment = new StudentAssignment(classroomID, personID,assignmentTitle, 
						 fileName, assignmentGrade);
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
	
}
