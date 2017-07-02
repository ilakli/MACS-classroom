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
	private LecturerDB lecturerDB;
	private SeminaristDB seminaristDB;
	private SectionLeaderDB sectionLeaderDB;
	private StudentDB studentDB;
	
	public ClassroomDB() {
		db = new DBConnection();
		lecturerDB = new LecturerDB();
		seminaristDB = new SeminaristDB();
		sectionLeaderDB = new SectionLeaderDB();
		studentDB = new StudentDB();
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
				String classroomName = rs.getString("classroom_name");
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

			if (rs.next()) {				
				classroomId = rs.getString(1);
			}
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
				String classroomID = classroomsTable.getString("classroom_id");
				String classroomName = classroomsTable.getString("classroom_name");

				classrooms.add(new Classroom(classroomName, classroomID));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (stmnt != null) {				
				stmnt.closeConnection();
			}
		}

		return classrooms;
	}
	/**
	 * returns classroom arraylist associated with given person's email
	 * @param email
	 * @return classroom arraylist
	 */
	public ArrayList <Classroom> getClassroomsByPerson(String email) {
		ArrayList <Classroom> classrooms = new ArrayList <Classroom>();
		String query = String.format(
				"select classrooms.classroom_name, classrooms.classroom_id from classroom_students inner join persons on persons.person_email = '%s' "
				+ "and persons.person_id = classroom_students.person_id inner join classrooms on classrooms.classroom_id = "
				+ "classroom_students.classroom_id", email);
		MyConnection myConnection = db.getMyConnection(query);
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs.next()) {
				classrooms.add(new Classroom(rs.getString("classroom_name"), rs.getString("classroom_id")));
			}
		} catch (SQLException | NullPointerException e) {
			
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		return classrooms;
	}

	/**
	 * sets new value to distribution of students into sections
	 * for classroom with given ID 
	 * @param newValue - new value for this parameter
	 */
	public void setClassroomSectionDistribution(String classroomID, boolean newValue) {
		String sqlCode = String.format("update `classrooms` set classroom_section_auto_distribution = %s"
				+ " where classroom_id = %s", Boolean.toString(newValue), classroomID);
		
		MyConnection update = db.getMyConnection(sqlCode);
		this.db.executeUpdate(update);
	}
	
	/**
	 * sets new value to distribution of students into 
	 * seminars for classroom with given ID 
	 * @param newValue - new value for this parameter
	 */
	public void setClassroomSeminarDistribution(String classroomID, boolean newValue) {
		String sqlCode = String.format("update `classrooms` set classroom_seminar_auto_distribution = %s"
				+ " where classroom_id = %s", Boolean.toString(newValue), classroomID);
		
		MyConnection update = db.getMyConnection(sqlCode);
		this.db.executeUpdate(update);
	}
	
	/**
	 * returns if classroom with given ID is auto distributed to seminars
	 * @param classroomID - ID of classroom
	 */
	public boolean getClassroomSeminarDistribution(String classroomID) {
		String sqlCode = "select classroom_seminar_auto_distribution from classrooms "
				+ "where classroom_id = " + classroomID + ";";
		MyConnection myConnection = db.getMyConnection(sqlCode);
		boolean result;
		try {
			ResultSet rs = myConnection.executeQuery();
			rs.next();
			result = rs.getBoolean(1);
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (myConnection != null) {				
				myConnection.closeConnection();
			}
		}
		return result;
	}
	
	/**
	 * returns if classroom with given ID is auto distributed to sections
	 * @param classroomID - ID of classroom
	 */
	public boolean getClassroomSectionDistribution(String classroomID) {
		String sqlCode = "select classroom_section_auto_distribution from classrooms "
				+ "where classroom_id = " + classroomID + ";";
		MyConnection myConnection = db.getMyConnection(sqlCode);
		boolean result;
		try {
			ResultSet rs = myConnection.executeQuery();
			rs.next();
			result = rs.getBoolean(1);
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		return result;
	}

	/**
	 * sets the number of reschedulings allowed in this classroom to new value
	 * @param classroomID - ID of classroom
	 * @param newValue - new value
	 */
	public void setClassroomsNumberOfReschedulings(String classroomID, int newValue) {
		String sqlCode = String.format(
				"update `classrooms` set classroom_reschedulings_num = %s where classroom_id = %s", 
				newValue, classroomID);
		
		MyConnection update = db.getMyConnection(sqlCode);
		this.db.executeUpdate(update);
	}
	
	/**
	 * @param classroomID - ID of classroom
	 * @return - number of reschedulings allowed in this classroom
	 */
	public int getClassroomsNumberOfReshcedulings(String classroomID) {
		String sqlCode = "select classroom_reschedulings_num from classrooms "
				+ "where classroom_id = " + classroomID + ";";
		MyConnection myConnection = db.getMyConnection(sqlCode);
		int result;
		try {
			ResultSet rs = myConnection.executeQuery();
			rs.next();
			result = rs.getInt(1);
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
			result = 0;
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		return result;
	}
	
	/**
	 * sets length of rescheduling allowed in classroom to new value 
	 * @param classroomID - ID of classroom
	 * @param newValue - new value
	 */
	public void setClassroomsReschedulingLength(String classroomID, int newValue) {
		String sqlCode = String.format("update `classrooms` set classroom_reschedulings_length = %s"
				+ " where classroom_id = %s", newValue, classroomID);
		
		MyConnection update = db.getMyConnection(sqlCode);
		this.db.executeUpdate(update);
	}

	/**
	 * checks if person with given email exists in given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean personExistsInClassroom(String email, String classroomId) {
		return lecturerDB.lecturerExists(email, classroomId) || seminaristDB.seminaristExists(email, classroomId)
				|| sectionLeaderDB.sectionLeaderExists(email, classroomId) || studentDB.studentExists(email, classroomId);
	}
	
	/**
	 * @param classroomID - ID of classroom
	 * @return - length of rescheduling allowed in classroom with given ID
	 */
	public int getClassroomsReschedulingLength(String classroomID) {
		String sqlCode = "select classroom_reschedulings_length from classrooms "
				+ "where classroom_id = " + classroomID + ";";
		
		MyConnection myConnection = db.getMyConnection(sqlCode);
		int result;
		try {
			ResultSet rs = myConnection.executeQuery();
			rs.next();
			result = rs.getInt(1);
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
			result = 0;
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		return result;
	}
}
