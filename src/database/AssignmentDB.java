package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.DBConnection.MyConnection;
import defPackage.Assignment;
import defPackage.Material;

public class AssignmentDB {
	
	private DBConnection db;
	
	public AssignmentDB(AllConnections allConnections) {
		db = allConnections.db;
	}
	

	
	/**
	 * @param classroomID - ID of classroom
	 * @return - ArrayList of all assignments in this classroom
	 */
	public ArrayList<Assignment> getAssignments(String classroomID) {
		String query = String.format("select * from `classroom_assignments` where `classroom_id` = %s;", classroomID);

		MyConnection myConnection = db.getMyConnection(query);
		ArrayList<Assignment> assignments = new ArrayList<Assignment>();
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				String assignmetID = rs.getString("assignment_id");
				String assignmentTitle = rs.getString("assignment_title");
				String assignmentInstructions = rs.getString("assignment_instructions");
				Date assignmentDeadline = null;
				java.sql.Date sqlDate =rs.getDate("assignment_deadline");
				System.out.println(sqlDate);
				if(sqlDate!=null) {
					assignmentDeadline = new java.util.Date(sqlDate.getTime());
					System.out.println(assignmentDeadline);
				}
				String fileName = rs.getString("file_name");
				assignments.add(new Assignment(assignmetID,classroomID,assignmentTitle, 
						assignmentInstructions,assignmentDeadline, fileName) );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {				
				myConnection.closeConnection();
			}
		}

		return assignments;
	}
	
	public Assignment getAssignment(String assignmentID){
		String query = String.format("select * from `classroom_assignments` where "
				+ "`assignment_id` = %s;", assignmentID );
		System.out.println(query);
		MyConnection myConnection = db.getMyConnection(query);
		Assignment assignment = null;
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				String assignmentInstructions = rs.getString("assignment_instructions");
				Date assignmentDeadline = null;
				java.sql.Date sqlDate =rs.getDate("assignment_deadline");
				if(sqlDate!=null) {
					assignmentDeadline = new java.util.Date(sqlDate.getTime());
				}
				String fileName = rs.getString("file_name");
				String classroomID = rs.getString("classroom_id");
				String assignmentTitle = rs.getString("assignment_title");
				assignment = new Assignment(assignmentID,classroomID,assignmentTitle, 
						assignmentInstructions,assignmentDeadline, fileName);
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
	 * adds new assignment to database with given parameters
	 * @param classroomID
	 * @param assignmentTitle
	 * @param assignmentInstructions
	 * @param assignmentDeadline
	 * @param fileName
	 */
	public boolean addAssignment(String classroomID, String assignmentTitle, String assignmentInstructions,
			String assignmentDeadline, String fileName) {
		
		String query = String.format("insert into `classroom_assignments`(`classroom_id`,`assignment_title`,`assignment_instructions`) "
				+ "values (%s,'%s','%s');", classroomID, assignmentTitle, assignmentInstructions);
		System.out.println("DOING: " + query);
		
		MyConnection myConnection = db.getMyConnection(query);
		if( db.executeUpdate(myConnection)==false) return false;
		

		if(!fileName.equals("")){
			String query2 = String.format("update `classroom_assignments` set `file_name` = '%s' "
					+ "where `classroom_id` = '%s' and `assignment_title` = '%s'; ", fileName, classroomID, assignmentTitle);
			myConnection = db.getMyConnection(query2);
			if( db.executeUpdate(myConnection)==false) return false;
		}
		
		if(!assignmentDeadline.equals("") ){
			String query1 = String.format("update `classroom_assignments` set `assignment_deadline` = '%s' "
					+ "where `classroom_id` = '%s' and `assignment_title` = '%s'; ", assignmentDeadline, classroomID, assignmentTitle);
			myConnection = db.getMyConnection(query1);
			System.out.println(query1);
			if( db.executeUpdate(myConnection)==false) return false;
		}
		return true;
		
		
		
	}
	
}
