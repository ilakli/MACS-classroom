package database;

import java.util.ArrayList;

import Dummys.PersonGeneratorDummy;
import database.DBConnection.MyConnection;
import defPackage.Person;

public class LecturerDB {

	private DBConnection db;
	private PersonDB personDB;

	public LecturerDB(AllConnections allConnections) {
		db = allConnections.db;
		personDB = allConnections.personDB;
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
	 * 
	 * @return all global lecturers.
	 */
	public ArrayList<Person> getGlobalLecturers() {
		String query = String.format("select * from `persons` where `person_id`in(select * from `lecturers`);");
		ArrayList<Person> globalLecturers = personDB.getPersons(query);
		return globalLecturers;
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
	 * checks if global lecturer with given email exists
	 * 
	 * @param email
	 * @return
	 */
	public boolean globalLecturerExists(String email) {
		String personId = personDB.getPersonId(email);
		String query = String.format("select * from `lecturers` where `person_id`=%s;", personId);
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
	 * adds global lecturer with given email
	 * 
	 * @param email
	 * @return true - if addition was successful, false - if lecturer already
	 *         exists or there is no such person
	 */
	public boolean addGlobalLecturer(String email) {
		String personId = personDB.getPersonId(email);
		if (personId.equals("")) {
			return false;
		}
		if (globalLecturerExists(email)) {
			return false;
		}
		String query = String.format("insert into `lecturers` (`person_id`) values (%s);", personId);
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

		String query = String.format(
				"delete from `classroom_lecturers` where `classroom_id` = %s and `person_id` = %s;", classroomId,
				personId);

		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}
	
	
	/**
	 * deletes lecturer with given email from the list of global lecturers
	 * @param email
	 * @return true - if lecturer was removed successfully, false otherwise
	 */
	public boolean deleteGlobalLecturer(String email){
		String personId = personDB.getPersonId(email);

		String preQuery = String.format("delete from `classroom_lecturers` where `person_id` = %s;", personId);
		String query = String.format("delete from `lecturers` where `person_id` = %s;", personId);
		
		MyConnection myConnection = db.getMyConnection(preQuery);
		db.executeUpdate(myConnection);
		
		myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);

	}
}

