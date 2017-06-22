package database;

import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Person;

public class LecturerDB {
	
	private DBConnection db;
	private PersonDB personDB;

	public LecturerDB() {
		db = new DBConnection();
		personDB = new PersonDB();
	}

	/**
	 * 
	 * @param classroomID
	 *            - ID of lecturers classroom
	 * @return lecturers of current classroom
	 */
	public ArrayList<Person> getLecturers(String classroomId) {
		String query = String.format("select * from `classroom_lecturers` where `classroom_id`=%s;", classroomId);
		ArrayList<Person> lecturers = personDB.getPersons(query);
		return lecturers;
	}

	/**
	 * checks if lecturer with given email exists in given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean lecturerExists(String email, String classroomId) {
		String personId = personDB.getPersonId(email);
		String query = String.format("select * from `classroom_lecturers` where `classroom_id`=%s and `person_id`=%s;",
				classroomId, personId);
		MyConnection myConnection = db.getMyConnection(query);
		return !db.isResultEmpty(myConnection);
	}

	/**
	 * adds lecturer with given email to the given class
	 * 
	 * @param email
	 * @param classroomId
	 * @return true - if addition was successful, false - if lecturer already
	 *         exists or there is no such person
	 */
	public boolean addLecturer(String email, String classroomId) {
		String personId = personDB.getPersonId(email);
		if (personId.equals("")) {
			return false;
		}
		if (lecturerExists(email, classroomId)) {
			return false;
		}
		String query = String.format("insert into `classroom_lecturers` (`classroom_id`, `person_id`) values (%s, %s);",
				classroomId, personId);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	/**
	 * deletes lecturer with given email from given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return true - if deletion was successful, false - if there was no such
	 *         lecturer or database crashed
	 */
	public boolean deleteLecturer(String email, String classroomId) {
		if (!lecturerExists(email, classroomId)) {
			return false;
		}
		String personId = personDB.getPersonId(email);

		String preQuery = String.format("delete from `lectures` where `classroom_id` = %s and `person_id` = %s;",
				classroomId, personId);

		String query = String.format(
				"delete from `classroom_lecturers` where `classroom_id` = %s and `person_id` = %s;", classroomId,
				personId);

		MyConnection myConnection = db.getMyConnection(preQuery);
		db.executeUpdate(myConnection);

		myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}
}
