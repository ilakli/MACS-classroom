package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Assignment;
import defPackage.Material;

public class AssignmentDB {
	
	private DBConnection db;
	
	public AssignmentDB() {
		db = new DBConnection();
	}
	
	/**
	 * adds new assignment to database with given parameters
	 * 
	 * @param classroomID
	 * @param assignmentName
	 * @param assignmentTitle
	 * @param assignmentInstructions
	 * @return - true if assignment was added successfully, false otherwise
	 */
	public boolean addAssignment(String classroomID, String assignmentName,
								String assignmentTitle, String assignmentInstructions){
		
		if (assignmentName.isEmpty()) return false;
		
		String query = String.format("insert into `classroom_assignments` values (%s,'%s','%s','%s');",
						classroomID, assignmentName, assignmentTitle, assignmentInstructions);
		
		System.out.println("DOING: " + query);
		
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
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
				assignments.add(new Assignment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
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
	
}
