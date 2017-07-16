package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Classroom;
import defPackage.Person;

public class ClassroomDB {

	private DBConnection db;
	private LecturerDB lecturerDB;
	private SeminaristDB seminaristDB;
	private SectionLeaderDB sectionLeaderDB;
	private StudentDB studentDB;
	private PersonDB personDB;

	public ClassroomDB(AllConnections allConnections) {
		db = allConnections.db;
		lecturerDB = allConnections.lecturerDB;
		seminaristDB = allConnections.seminaristDB;
		sectionLeaderDB = allConnections.sectionLeaderDB;
		studentDB = allConnections.studentDB;
		personDB = allConnections.personDB;

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
				String classroomCreatorId = rs.getString("classroom_creator_id");
				classroom = new Classroom(classroomName, classroomId,classroomCreatorId);
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
	public String addClassroom(String classroomName, String creatorId) {
		String classroomId = DBConnection.DATABASE_ERROR;
		Connection con = db.getConnection();
		try {
			con.setAutoCommit(false);
			String query = String.format("insert into `classrooms` (`classroom_name`, `classroom_creator_id`) values ('%s', %s);", 
					classroomName, creatorId);
			PreparedStatement insertClassroom = con.prepareStatement(query);
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
				String classroomCreatorId = classroomsTable.getString("classroom_creator_id");
				classrooms.add(new Classroom(classroomName, classroomID,classroomCreatorId));
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
	 * returns person`s all classrooms
	 * 
	 * @param p
	 *            - person
	 * @return person's classrooms
	 */
	public ArrayList<Classroom> getPersonsClassrooms(Person p) {
		ArrayList<Classroom> classrooms = new ArrayList<Classroom>();
		String classroomsQuery = "select * from classrooms;";
		MyConnection stmnt = db.getMyConnection(classroomsQuery);
		try {
			ResultSet classroomsTable = stmnt.executeQuery();
			while (classroomsTable.next()) {
				String classroomID = classroomsTable.getString("classroom_id");
				String classroomName = classroomsTable.getString("classroom_name");
				String classroomCreatorId = classroomsTable.getString("classroom_creator_id");
				Classroom classroom = new Classroom(classroomName, classroomID, classroomCreatorId);
				if (classroom.classroomPersonExists(p.getEmail())) {
					classrooms.add(classroom);
				}
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
	 * 
	 * @param email
	 * @return classroom arraylist
	 */
	public ArrayList<Classroom> getClassroomsByPerson(String email) {
		ArrayList<Classroom> classrooms = new ArrayList<Classroom>();
		String query = String
				.format("select classrooms.classroom_name, classrooms.classroom_id from classroom_students inner join persons on persons.person_email = '%s' "
						+ "and persons.person_id = classroom_students.person_id inner join classrooms on classrooms.classroom_id = "
						+ "classroom_students.classroom_id", email);
		MyConnection myConnection = db.getMyConnection(query);
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs.next()) {
				classrooms.add(new Classroom(rs.getString("classroom_name"), rs.getString("classroom_id")
						, rs.getString("classroom_creator_id")));
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
	 * sets new value to distribution of students into sections for classroom
	 * with given ID
	 * 
	 * @param newValue
	 *            - new value for this parameter
	 */
	public void setClassroomSectionDistribution(String classroomID, boolean newValue) {
		String sqlCode = String.format(
				"update `classrooms` set classroom_section_auto_distribution = %s" + " where classroom_id = %s",
				Boolean.toString(newValue), classroomID);

		MyConnection update = db.getMyConnection(sqlCode);
		this.db.executeUpdate(update);
	}

	/**
	 * sets new value to distribution of students into seminars for classroom
	 * with given ID
	 * 
	 * @param newValue
	 *            - new value for this parameter
	 */
	public void setClassroomSeminarDistribution(String classroomID, boolean newValue) {
		String sqlCode = String.format(
				"update `classrooms` set classroom_seminar_auto_distribution = %s" + " where classroom_id = %s",
				Boolean.toString(newValue), classroomID);

		MyConnection update = db.getMyConnection(sqlCode);
		this.db.executeUpdate(update);
	}

	/**
	 * returns if classroom with given ID is auto distributed to seminars
	 * 
	 * @param classroomID
	 *            - ID of classroom
	 */
	public boolean getClassroomSeminarDistribution(String classroomID) {
		String sqlCode = "select classroom_seminar_auto_distribution from classrooms " + "where classroom_id = "
				+ classroomID + ";";
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
	 * 
	 * @param classroomID
	 *            - ID of classroom
	 */
	public boolean getClassroomSectionDistribution(String classroomID) {
		String sqlCode = "select classroom_section_auto_distribution from classrooms " + "where classroom_id = "
				+ classroomID + ";";
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
	 * 
	 * @param classroomID
	 *            - ID of classroom
	 * @param newValue
	 *            - new value
	 */
	public void setClassroomsNumberOfReschedulings(String classroomID, int newValue) {
		String sqlCode = String.format(
				"update `classrooms` set classroom_reschedulings_num = %s where classroom_id = %s", newValue,
				classroomID);

		MyConnection update = db.getMyConnection(sqlCode);
		this.db.executeUpdate(update);
	}

	/**
	 * @param classroomID
	 *            - ID of classroom
	 * @return - number of reschedulings allowed in this classroom
	 */
	public int getClassroomsNumberOfReshcedulings(String classroomID) {
		String sqlCode = "select classroom_reschedulings_num from classrooms " + "where classroom_id = " + classroomID
				+ ";";
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
	 * 
	 * @param classroomID
	 *            - ID of classroom
	 * @param newValue
	 *            - new value
	 */
	public void setClassroomsReschedulingLength(String classroomID, int newValue) {
		String sqlCode = String.format(
				"update `classrooms` set classroom_reschedulings_length = %s" + " where classroom_id = %s", newValue,
				classroomID);

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
				|| sectionLeaderDB.sectionLeaderExists(email, classroomId)
				|| studentDB.studentExists(email, classroomId);
	}

	/**
	 * @param classroomID
	 *            - ID of classroom
	 * @return - length of rescheduling allowed in classroom with given ID
	 */
	public int getClassroomsReschedulingLength(String classroomID) {
		String sqlCode = "select classroom_reschedulings_length from classrooms " + "where classroom_id = "
				+ classroomID + ";";

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

	public int reschedulingsUsed(String personId, String classroomID) {
		String query = String.format("select * from `classroom_students` where `classroom_id`=%s and `person_id`=%s;",
				classroomID, personId);
		MyConnection myConnection = db.getMyConnection(query);

		ResultSet rs = myConnection.executeQuery();
		try {
			if (rs.next()) {
				return rs.getInt("reschedulings_used");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		return -1;

	}

	public boolean useRescheduling(String personId, String classroomID) {
		int reschedulingsUsed = reschedulingsUsed(personId, classroomID);
		int maxRes = getClassroomsNumberOfReshcedulings(classroomID);
		if (maxRes <= reschedulingsUsed)
			return false;
		String query = String.format("update `classroom_students` set `reschedulings_used`= %s"
				+ " where `classroom_id`=%s and `person_id`=%s;", reschedulingsUsed + 1, classroomID, personId);

		MyConnection myConnection = db.getMyConnection(query);

		return db.executeUpdate(myConnection);
	}
	
	/**
	 * This method returns all the persons from classroom;
	 * @param classroomID - id of the classroom
	 * @return - members from the classroom;
	 */
	public ArrayList<Person> ClassroomAllPersons(String classroomID){
		String query = String.format("select * from (select `person_id`  from classroom_lecturers s\n"+
				"where `classroom_id` = %s\n"+
				"union\n"+
				"select person_id  from classroom_seminarists sl\n"+
				"where `classroom_id` = %s\n"+
				"union\n"+
				"select person_id  from classroom_section_leaders sl\n"+
				"where `classroom_id` = %s\n"+
				"union\n"+
				"select person_id  from classroom_students sl\n"+
				"where `classroom_id` = %s) as k\n"+
					"inner join persons as p\n"+
					"on  p.person_id = k.person_id;\n", classroomID,classroomID,classroomID,classroomID );
		ArrayList<Person> allPerosns = personDB.getPersons(query);
		return allPerosns;
				
	}
}
