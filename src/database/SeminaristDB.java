package database;

import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Person;

public class SeminaristDB {
	
	private DBConnection db;
	private PersonDB personDB;

	public SeminaristDB(AllConnections allConnections) {
		db = allConnections.db;
		personDB = allConnections.personDB;
	}

	/**
	 * 
	 * @param classroomID
	 *            - ID of seminarists classroom
	 * @return seminarists of current classroom
	 */
	public ArrayList<Person> getSeminarists(String classroomId) {
		String query = String.format("select * from `classroom_seminarists` where `classroom_id`=%s;", classroomId);
		ArrayList<Person> seminarists = personDB.getPersons(query);
		return seminarists;
	}

	/**
	 * checks if seminarist with given email exists in given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean seminaristExists(String email, String classroomId) {
		String personId = personDB.getPersonId(email);
		String query = String.format(
				"select * from `classroom_seminarists` where `classroom_id`=%s and `person_id`=%s;", classroomId,
				personId);
		MyConnection myConnection = db.getMyConnection(query);
		return !db.isResultEmpty(myConnection);
	}

	/**
	 * adds seminarist with given email to the given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return true - if addition was successful, false - if such seminarist
	 *         already exists or there is no such person
	 */
	public boolean addSeminarist(String email, String classroomId) {
		String personId = personDB.getPersonId(email);
		if (personId.equals("")) {
			return false;
		}
		if (seminaristExists(email, classroomId)) {
			return false;
		}
		String query = String.format(
				"insert into `classroom_seminarists` (`classroom_id`, `person_id`) values (%s, %s);", 
				classroomId, personId);
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
		if (!seminaristExists(email, classroomId)) {
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
	 * deletes seminarist with given email from given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return true - if deletion was successful, false - if there was no such
	 *         seminarist or database crashed
	 */
	public boolean deleteSeminaristFromSeminar(String email, String classroomId, String seminarId) {
		if (!seminaristExists(email, classroomId))
			return false;
		String personId = personDB.getPersonId(email);

		String preQuery = String.format(
				"delete from `seminar-seminarists` where `classroom_id` = %s and `person_id` = %s and "
				+ "seminar_id = %s; ", classroomId, personId, seminarId);
		
		MyConnection myConnection = db.getMyConnection(preQuery);
		return db.executeUpdate(myConnection);
	}
	
	public Person getSeminarist (String seminarId) {
		String query = String.format(
				"select * from `seminar-seminarists` where `seminar_id` = %s;", seminarId);
		ArrayList<Person> seminarists = personDB.getPersons(query);
		return seminarists.isEmpty() ? null : seminarists.get(0);
	}
}
