package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Classroom;

public class ClassroomDB {
	
	private DBConnection db;
	
	public ClassroomDB() {
		db = new DBConnection();
	}
	
	/**
	 * returns classroom object based on classroomId
	 * 
	 * @param classroomId
	 * @return classroom object
	 */
	public Classroom getClassroom(String classroomId) {
		String query = String.format("select * from `classrooms` where `classroom_id` = %s", classroomId);
		Classroom classroom = null;
		MyConnection myConnection = null;
		try {
			myConnection = db.getMyConnection(query);
			ResultSet rs = myConnection.executeQuery();
			if (rs.next()) {
				String classroomName = rs.getString(2);
				classroom = new Classroom(classroomName, classroomId);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		return classroom;
	}

	/**
	 * adds classroom with given name to the database
	 * 
	 * @param classroomName
	 * @return classromId of newly added classroom
	 */
	public String addClassroom(String classroomName) {
		String classroomId = DBConnection.DATABASE_ERROR;
		Connection con = db.getConnection();
		try {
			con.setAutoCommit(false);

			PreparedStatement insertClassroom = con.prepareStatement(
					String.format("insert into `classrooms` (`classroom_name`) values ('%s');", classroomName));
			insertClassroom.executeUpdate();

			PreparedStatement selectLastIndex = con.prepareStatement("select last_insert_id();");
			ResultSet rs = selectLastIndex.executeQuery();

			if (rs.next())
				classroomId = rs.getString(1);

			con.commit();
			con.close();
		} catch (SQLException e) {
		}

		return classroomId;
	}
	
	/**
	 * 
	 * @return returns ArrayList of current classrooms
	 */
	public ArrayList<Classroom> getClassrooms() {
		ArrayList<Classroom> classrooms = new ArrayList<Classroom>();
		String classroomsQuery = "select * from classrooms;";
		MyConnection stmnt = db.getMyConnection(classroomsQuery);

		try {
			ResultSet classroomsTable = stmnt.executeQuery();
			while (classroomsTable.next()) {
				String classroomID = classroomsTable.getString(1);
				String classroomName = classroomsTable.getString(2);

				classrooms.add(new Classroom(classroomName, classroomID));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return classrooms;
	}
}
