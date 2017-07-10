package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Person;
import defPackage.Section;
import defPackage.Seminar;

public class SeminarDB {

	private DBConnection db;
	private PersonDB personDB;
	private AllConnections allConnections;
	public SeminarDB(AllConnections allConnections) {
		this.allConnections = allConnections;
		db = allConnections.db;
		personDB = allConnections.personDB;
	}

	/**
	 * adds seminar to the database
	
	 * @param classroomId
	 * @return - true if seminar was added successfully, false otherwise
	 */
	public boolean addSeminar (String classroomId) {
		int seminarN = getSeminars(classroomId).size();
		String query = String.format("insert into `seminars` (`classroom_id`, `seminar_n`) values (%s, %s);",
				classroomId, seminarN);
		System.out.println(query);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	/**
	 * adds given student to given seminar
	 * 
	 * @param seminarN
	 * @param studentEmail
	 * @param classroomId
	 * @return true - if student was successfully added, false otherwise
	 */
	public boolean addStudentToSeminar(int seminarN, String studentEmail, String classroomId) {
		String seminarId = getSeminarId(seminarN, classroomId);
		String personId = personDB.getPersonId(studentEmail);
		if (seminarId.equals("") || personId.equals("")) {
			return false;
		}
		String query = String.format(
				"insert into `student-seminar` (`classroom_id`, `person_id`, `seminar_id`) values(%s, %s, %s);",
				classroomId, personId, seminarId);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	/**
	 * adds given students to given seminar
	 * @param i
	 * @param arrayList
	 * @param classroomID
	 */
	
	public boolean addStudentsToSeminar(int seminarN, ArrayList<Person> students, String classroomId) {
		String seminarId = getSeminarId(seminarN, classroomId);
		if (seminarId.equals("")) {
			return false;
		}
		if (students.isEmpty()) {
			return true;
		}
		
		String query = "insert into `student-seminar` (`classroom_id`, `person_id`, `seminar_id`) values\n";
		
		for (int i = 0; i < students.size(); i++){
			Person p = students.get(i);
			query += "(" + classroomId + ", " + p.getPersonID() + ", " + seminarId + ")";
			if (i + 1 < students.size()) query += ",\n";
		}
		
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	/**
	 * removes given student from given seminar
	 * 
	 * @param seminarN
	 * @param studentEmail
	 * @param classroomId
	 * @return true - if student was successfully added, false otherwise
	 */
	public boolean deleteStudentFromSeminar(int seminarN, String studentEmail, String classroomId) {
		String seminarId = getSeminarId(seminarN, classroomId);
		String personId = personDB.getPersonId(studentEmail);
		if (seminarId.equals("") || personId.equals("")) {
			return false;
		}
		String query = String.format(
				"delete from `student-seminar` where `classroom_id` = %s and `person_id` = %s and `seminar_id` = %s;",
				classroomId, personId, seminarId);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}
	/**
	 * checks whether given students belongs to given seminar
	 * @param personId
	 * @param seminarId
	 * @return true if belongs, false otherwise
	 */
	public boolean studentExists (String personId, String seminarId) {
		if (personId.equals("") || seminarId.equals("")) {
			return false;
		}
		String query = String.format(
				"select * from `student-seminar` where `person_id` = %s and `seminar_id` = %s;", 
				personId, seminarId);
		MyConnection myConnection = db.getMyConnection(query);
		return !db.isResultEmpty(myConnection);
	}

	/**
	 * 
	 * @param seminarN
	 * @param classroomId
	 * @return seminarId based on classroomId and seminarN
	 */
	public String getSeminarId(int seminarN, String classroomId) {
		String query = String.format(
				"select `seminar_id` from `seminars` where `seminar_n` = %s and `classroom_id` = %s;", seminarN,
				classroomId);
		MyConnection myConnection = db.getMyConnection(query);
		String seminarId = "";
		try {
			ResultSet rs = myConnection.executeQuery();
			if (rs != null && rs.next())
				seminarId = rs.getString(1);
		} catch (SQLException e) {
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		return seminarId;
	}
	
	/**
	 * checks if seminar in given classroom with given id exists
	 * 
	 * @param seminarN
	 * @param classroomId
	 * @return true - if seminar exists, false - otherwise
	 */
	public boolean seminarExists(int seminarN, String classroomId) {
		String query = String.format("select * from `seminars` where `seminar_n` = %s and `classroom_id` = %s;",
				seminarN, classroomId);
		MyConnection myConnection = db.getMyConnection(query);
		return !db.isResultEmpty(myConnection);
	}
	
	/**
	 * deletes the last seminar in this classroom from database
	 * @param classroomId
	 * @return true - if seminar was successfully deleted, false - otherwise
	 */
	public boolean deleteSeminar(String classroomId) {
		int seminarN = getSeminars(classroomId).size() - 1;
		if (seminarN == -1) {
			return false;
		}
		String seminarID = getSeminarId(seminarN, classroomId);

		String preQuery0 = String.format(
				"delete from `seminar-seminarists` where `seminar_id` = '%s' and `classroom_id` = %s;", 
				seminarID, classroomId);
		String preQuery1 = String.format("delete from `seminars_timetable` where `seminar_id` = '%s';", seminarID);
		String preQuery2 = String.format(
				"delete from `student-seminar` where `seminar_id` = '%s' and `classroom_id` = %s;", 
				seminarID, classroomId);
		String query = String.format("delete from `seminars` where `seminar_n` = %s and `classroom_id` = %s;",
				seminarN, classroomId);

		MyConnection myConnection = db.getMyConnection(preQuery0);
		db.executeUpdate(myConnection);

		myConnection = db.getMyConnection(preQuery1);
		db.executeUpdate(myConnection);

		myConnection = db.getMyConnection(preQuery2);
		db.executeUpdate(myConnection);

		myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}
	
	/**
	 * adds seminarist to seminar in database
	 * 
	 * @param seminarN
	 * @param email
	 * @param classroomId
	 * @return true - if seminarists was successfully added, false otherwise
	 */
	public boolean addSeminaristToSeminar(int seminarN, String email, String classroomId) {
		String seminarId = getSeminarId(seminarN, classroomId);
		String personId = personDB.getPersonId(email);
		if (seminarId.equals("") || personId.equals("")) {
			return false;
		}
		String query = String.format(
				"insert into `seminar-seminarists` (`seminar_id`, `classroom_id`, `person_id`) values(%s, %s, %s);",
				seminarId, classroomId, personId);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	/**
	 * 
	 * @param classroomId
	 *            -Id of seminars classroom
	 * @return returns ArrayList of seminars associated with given classroom
	 */
	public ArrayList<Seminar> getSeminars(String classroomId) {
		String query = String.format("select * from seminars where `classroom_id`=%s;", classroomId);
		ArrayList<Seminar> seminars = new ArrayList<Seminar>();
		MyConnection myConnection = db.getMyConnection(query);
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs.next()) {
				seminars.add(new Seminar(rs.getInt("seminar_n"), classroomId, rs.getInt("seminar_size"),allConnections));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		
		return seminars;
	}
	
	/**
	 * returns seminar that contains smallest number of students in it
	 * @param seminars - ArrayList of seminars
	 * @return smallest Seminar
	 */
	public Seminar getSmallestSeminar(String classroomID) {
		ArrayList<Seminar> seminars = getSeminars(classroomID);
		if (seminars.isEmpty()) {
			return null;
		}
		
		Seminar seminar = null;
		int curMin = Integer.MAX_VALUE;
		
		for (Seminar sem : seminars){
			int curSize = sem.getSeminarStudents().size();
			
			if (curSize <= curMin){
				curMin = curSize;
				seminar = sem;
			}
		}
		return seminar;
	}

	/**
	 * updates seminar_size in database
	 * @param seminarN
	 * @param classroomID
	 * @param seminarSize
	 */
	public void updateSeminarSize(int seminarN, String classroomId, int seminarSize) {
		String seminarId = getSeminarId(seminarN, classroomId);
		String query = String.format(
				"update `seminars` set `seminar_size` = %s where `seminar_id` = %s and `classroom_id` = %s",
				seminarSize, seminarId, classroomId);
		MyConnection myConnection = db.getMyConnection(query);
		db.executeUpdate(myConnection);
	}
	
	
	/**
	 * @param seminarist - seminarist person
	 * @param classroomID - id of classroom
	 * @return - seminarist's seminar group
	 */
	public Seminar getSeminarBySeminarist(Person seminarist, String classroomID) {
		
		String query = String.format("select s.seminar_n, s.seminar_size "
				+ "from `seminar-seminarists` ss, `seminars` s "
				+ "where ss.classroom_id = %s and ss.person_id = %s and s.seminar_id = ss.seminar_id"
				, classroomID, seminarist.getPersonID()); 
				
		Seminar seminar = null;
		
		MyConnection myConnection = db.getMyConnection(query);
		try {
			ResultSet rs = myConnection.executeQuery();
			if (rs != null && rs.next()) {
				seminar = new Seminar(rs.getInt("seminar_n"), classroomID, rs.getInt("seminar_size"), allConnections);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		
		return seminar;
		
	}
}
