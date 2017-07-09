package database;

import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Person;

public class SectionLeaderDB {

	private DBConnection db;
	private PersonDB personDB;
	
	public SectionLeaderDB(AllConnections allConnections) {
		db = allConnections.db;
		personDB = allConnections.personDB;
	}

	/**
	 * 
	 * @param classroomID
	 *            - ID of Section Leaders classroom
	 * @return section leaders of current classroom
	 */
	public ArrayList<Person> getSectionLeaders(String classroomId) {
		String query = String.format("select * from `classroom_section_leaders` where `classroom_id`=%s;", classroomId);
		ArrayList<Person> sectionLeaders = personDB.getPersons(query);
		return sectionLeaders;
	}
	
	/**
	 * 
	 * @param sectionId
	 * @return section leaders of given section
	 */
	public Person getSectionLeader(String sectionId) {
		String query = String.format("select * from `section-section_leader` where `section_id` = %s;", sectionId);
		ArrayList <Person> sectionLeaders = personDB.getPersons(query);
		return sectionLeaders.isEmpty() ? null : sectionLeaders.get(0);
	}

	/**
	 * checks if section leader with given email exists in given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean sectionLeaderExists(String email, String classroomId) {
		String personId = personDB.getPersonId(email);
		String query = String.format(
				"select * from `classroom_section_leaders` where `classroom_id`=%s and `person_id`=%s;", classroomId,
				personId);
		MyConnection myConnection = db.getMyConnection(query);
		return !db.isResultEmpty(myConnection);
	}

	/**
	 * adds section leader with given email to the given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return true - if addition was successful, false - if section leaders
	 *         already exists or there is no such person
	 */
	public boolean addSectionLeader(String email, String classroomId) {
		String personId = personDB.getPersonId(email);
		if (personId.equals("")) {
			return false;
		}
		if (sectionLeaderExists(email, classroomId)) {
			return false;
		}
		String query = String.format(
				"insert into `classroom_section_leaders` (`classroom_id`, `person_id`) values (%s, %s);", classroomId,
				personId);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	/**
	 * deletes section leader with given email from given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return true - if deletion was successful, false - if there was no such
	 *         section leader or database crashed
	 */
	public boolean deleteSectionLeader(String email, String classroomId) {
		if (!sectionLeaderExists(email, classroomId)) {
			return false;
		}
		String personId = personDB.getPersonId(email);

		String preQuery = String.format(
				"delete from `section-section_leader` where `classroom_id` = %s and `person_id` =%s;", classroomId,
				personId);
		String query = String.format(
				"delete from `classroom_section_leaders` where `classroom_id` = %s and `person_id` =%s;", classroomId,
				personId);

		MyConnection myConnection = db.getMyConnection(preQuery);
		db.executeUpdate(myConnection);

		myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}
	
	
	/**
	 * deletes section leader with given email from given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return true - if deletion was successful, false - if there was no such
	 *         section leader or database crashed
	 */
	public boolean deleteSectionLeaderFromSection(String email, String classroomId, String sectionId) {
		if (!sectionLeaderExists(email, classroomId)) {
			return false;
		}
		String personId = personDB.getPersonId(email);

		String preQuery = String.format(
				"delete from `section-section_leader` where `classroom_id` = %s and `person_id` =%s and section_id = %s;", 
				classroomId, personId, sectionId);
		
		MyConnection myConnection = db.getMyConnection(preQuery);
		return db.executeUpdate(myConnection);
	}
	
	
	
	/**
	 * returns list of section leaders without a section in classroom with given ID
	 * @param classroomID - ID of classroom
	 * @return list of section leaders that do not have a section
	 */
	public ArrayList<Person> getSectionLeadersWithoutSection(String classroomID){
		
		String query = String.format("select * from `classroom_section_leaders` where "
				+ "classroom_id = %s  and person_id not in ("
				+ "select person_id from `section-section_leader` where classroom_id = %s);"
				, classroomID, classroomID);
		
		ArrayList<Person> sectionLeaders = personDB.getPersons(query);
		return sectionLeaders;
	}
	
}
