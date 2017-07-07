package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Assignment;
import defPackage.Classroom;
import defPackage.Function;
import defPackage.Position;

public class FunctionDB {
	
	private DBConnection db;
	
	public FunctionDB(AllConnections allConnections) {
		db = allConnections.db;
	}
	
	/**
	 * @return ArrayList of all possible functions in classroom
	 */
	public ArrayList<Function> getAllFunctions(){
		
		String query = String.format("select * from `functions`;");
		
		MyConnection myConnection = db.getMyConnection(query);
		ArrayList<Function> functions= new ArrayList<Function>();
		
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				functions.add(new Function(rs.getString(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {				
				myConnection.closeConnection();
			}
		}
		
		return functions;
	}
	
	/**
	 * returns if in given classroom given position has permission
	 * on given function
	 * @param c - classroom
	 * @param p - permission
	 * @param f - function
	 * @return
	 */
	public boolean hasPremission(Classroom c, Position p, Function f){
		String query = String.format("select * from `classroom_position_function` where "
				+ "classroom_id = %s and position_id = %s and function_id = %s;", 
				c.getClassroomID(), p.getID(), f.getID());
			
		MyConnection myConnection = db.getMyConnection(query);
		return !db.isResultEmpty(myConnection);
	}
	
	/**
	 * This method deletes all the functions for classroom members in the classroom;
	 * @param classroomId - ID of the class; 
	 */
	public void deleteAllPermissions(String classroomId){
		String query = String.format("delete from `classroom_position_function` where "
				+ "classroom_id = %s;", classroomId);
		MyConnection myConnection = db.getMyConnection(query);
		try {
			myConnection.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method adds permission to a position in a classroom; 
	 * @param classroomId - class ID
	 * @param positionId - position ID
	 * @param functionId - function ID
	 */
	public void addPermission(String classroomId, String positionId, String functionId){
		String query = String.format("insert into `classroom_position_function` values (%s, %s, %s);",
				classroomId, positionId, functionId);
		MyConnection myConnection = db.getMyConnection(query);
		try {
			myConnection.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
