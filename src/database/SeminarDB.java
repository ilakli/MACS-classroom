package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Seminar;

public class SeminarDB {

	private DBConnection db;
	private PersonDB personDB;
	private SeminaristDB seminaristDB;
	
	public SeminarDB() {
		db = new DBConnection();
		personDB = new PersonDB();
		seminaristDB = new SeminaristDB();
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
		ResultSet rs = myConnection.executeQuery();
		String seminarId = "";
		try {
			if (rs != null && rs.next())
				seminarId = rs.getString(1);
		} catch (SQLException e) {
		}
		myConnection.closeConnection();
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
		if (seminarId.equals("") || personId.equals(""))
			return false;
		String query = String.format(
				"insert into `seminar-seminarists` (`seminar_id`, `classroom_id`, `person_id`) values(%s, %s, %s);",
				seminarId, classroomId, personId);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	/**
	 * deletes seminarist with given email from given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return true - if deletion was successful, false - if there was no such
	 *         seminarist or database crashed
	 */
	public boolean deleteSeminarist(String email, String classroomId) {
		if (!seminaristDB.seminaristExists(email, classroomId)) {
			return false;
		}
		String personId = personDB.getPersonId(email);

		String preQuery = String.format(
				"delete from `seminar-seminarists` where `classroom_id` = %s and `person_id` = %s;", classroomId,
				personId);
		String query = String.format(
				"delete from `classroom_seminarists` where `classroom_id` = %s and `person_id` = %s;", classroomId,
				personId);

		MyConnection myConnection = db.getMyConnection(preQuery);
		db.executeUpdate(myConnection);

		myConnection = db.getMyConnection(query);
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
		System.out.println(query);
		ArrayList<Seminar> seminars = new ArrayList<Seminar>();
		ResultSet rs = db.getResultSet(query);

		try {
			while (rs.next()) {
				seminars.add(new Seminar( rs.getInt("seminar_n"), classroomId));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
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
}
