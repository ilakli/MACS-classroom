package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection.MyConnection;


public class AssignmentGradeDB {
	
	private DBConnection db;
	
	public AssignmentGradeDB(AllConnections allConnections) {
		db = allConnections.db;
	}
	
	/**
	 * This method returns all the grades which are possible to write in your classroom;
	 * @return - List of grades' names;
	 */
	public List<String> getAllGrades(){
		String query = String.format("select * from `assignment_grades`"+
					"ORDER BY `grade_value` ASC;"	);
		System.out.println("xs: " + query);
			
		MyConnection myConnection = db.getMyConnection(query);
		List<String> grades = new ArrayList<String>();
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				grades.add(rs.getString("grade_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {				
				myConnection.closeConnection();
			}
		}
		for(String s: grades){
			System.out.println(s);
		}
		return grades;
	}
}
