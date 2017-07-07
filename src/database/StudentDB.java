package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Person;

public class StudentDB {

	private DBConnection db;
	private PersonDB personDB;
	private ClassroomDB classroomDB;
	public StudentDB(AllConnections allConnections) {
		db = allConnections.db;
		personDB = allConnections.personDB;
		classroomDB = allConnections.classroomDB;
	}
	

	/**
	 * 
	 * @param classroomID
	 *            - ID of students classroom
	 * @return students of current classroom
	 */
	public ArrayList<Person> getStudents(String classroomId) {
		String query = String.format("select * from `classroom_students` where `classroom_id`=%s;", classroomId);
		ArrayList<Person> students = personDB.getPersons(query);
		return students;
	}
	
	public ArrayList <Person> getSectionStudents (String sectionId) {
		String query = String.format("select * from `student-section` where `section_id` = %s;", sectionId);
		ArrayList <Person> students = personDB.getPersons(query);
		return students;
	}

	/**
	 * 
	 * @param classroomId
	 * @param seminarId
	 * @return students of current seminar of given classroom
	 */
	public ArrayList<Person> getSeminarStudents(String seminarId) {
		String query = String.format(
				"select * from `student-seminar` where `seminar_id` = %s;", seminarId);
		
		ArrayList<Person> students = personDB.getPersons(query);
		return students;
	}

	/**
	 * checks if student with given email exists in given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean studentExists(String email, String classroomId) {
		String personId = personDB.getPersonId(email);
		String query = String.format("select * from `classroom_students` where `classroom_id`=%s and `person_id`=%s;",
				classroomId, personId);
		MyConnection myConnection = db.getMyConnection(query);
		return !db.isResultEmpty(myConnection);
	}
	

	/**
	 * adds student with given email to the given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return true - if addition was successful, false - if student already
	 *         exists or there is no such person
	 */
	public boolean addStudent(String email, String classroomId) {
		String personId = personDB.getPersonId(email);
		if (personId.equals("")) {
			return false;
		}
		if (studentExists(email, classroomId)) {
			return false;
		}
		String query = String.format("insert into `classroom_students` (`classroom_id`, `person_id`) values (%s, %s);",
				classroomId, personId);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}	

	/**
	 * deletes student with given email from given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return true - if deletion was successful, false - if there was no such
	 *         student or database crashed
	 */
	public boolean deleteStudent(String email, String classroomId) {
		if (!studentExists(email, classroomId)) {
			return false;
		}
		String personId = personDB.getPersonId(email);

		String preQuery0 = String.format(
				"delete from `student-seminar` where `classroom_id` = %s and `person_id` = %s;", classroomId, personId);
		String preQuery1 = String.format(
				"delete from `student-section` where `classroom_id` = %s and `person_id` = %s;", classroomId, personId);
		String query = String.format("delete from `classroom_students` where `classroom_id` = %s and `person_id` = %s;",
				classroomId, personId);

		MyConnection myConnection = db.getMyConnection(preQuery0);
		db.executeUpdate(myConnection);

		myConnection = db.getMyConnection(preQuery1);
		db.executeUpdate(myConnection);

		myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}
	
	
	/**
	 * returns list of students without seminar assigned of classroom with given ID
	 * @param classroomID - ID of classroom
	 * @return List of students that are not assigned to any seminar
	 */
	public ArrayList<Person> getStudentsWithoutSeminar(String classroomID){
		
		String query = String.format("select * from `classroom_students` where "
				+ "classroom_id = %s  and person_id not in ("
				+ "select person_id from `student-seminar` where classroom_id = %s);"
				, classroomID, classroomID);
		
		ArrayList<Person> students = personDB.getPersons(query);
		return students;
	}
		
	/**
	 * returns list of students without section assigned of classroom with given ID
	 * @param classroomID - ID of classroom
	 * @return list of students that are not in any section
	 */
	public ArrayList<Person> getStudentsWithoutSection(String classroomID){
		
		String query = String.format("select * from `classroom_students` where "
				+ "classroom_id = %s  and person_id not in ("
				+ "select person_id from `student-section` where classroom_id = %s);"
				, classroomID, classroomID);
		
		ArrayList<Person> students = personDB.getPersons(query);
		return students;
	}
}
