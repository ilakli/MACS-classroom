package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection.MyConnection;
import defPackage.Person;
import defPackage.Section;

public class SectionDB {
	
	private DBConnection db;
	private PersonDB personDB;
	
	public SectionDB() {
		db = new DBConnection();
		personDB = new PersonDB();
	}
	
	/**
	 * adds section to the database
	 * 
	 * @param sectionN
	 * @param classroomId
	 * @return - true if section was added successfuly, false otherwise
	 */
	public boolean addSection(String classroomId) {
		int sectionN = getSections(classroomId).size();
		System.out.println("sectionN   " + sectionN);
		String query = String.format("insert into `sections` (`section_n`, `classroom_id`) values (%s, %s);",
				sectionN, classroomId);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	/**
	 * 
	 * @param sectionN
	 * @param classroomId
	 * @return sectionId based on classroomId and sectionN
	 */
	public String getSectionId(int sectionN, String classroomId) {
		String query = String.format(
				"select `section_id` from `sections` where `classroom_id` = %s and `section_n` = %s;", classroomId,
				sectionN);
		MyConnection myConnection = db.getMyConnection(query);
		ResultSet rs = myConnection.executeQuery();
		String sectionId = "";
		try {
			if (rs != null && rs.next())
				sectionId = rs.getString(1);
		} catch (SQLException e) {
		}
		return sectionId;
	}

	/**
	 * adds student to section in database
	 * 
	 * @param sectionN
	 * @param studentEmail
	 * @param classroomId
	 * @return true - if student was successfully added to the given section,
	 *         flase - otherwise
	 */
	public boolean addStudentToSection(int sectionN, String studentEmail, String classroomId) {
		String sectionId = getSectionId(sectionN, classroomId);
		String personId = personDB.getPersonId(studentEmail);
		if (sectionId.equals("")) {			
			return false;
		}
		if (personId.equals("")) {
			return false;
		}
		String query = String.format(
				"insert into `student-section` (`classroom_id`, `person_id`, `section_id`) values(%s, %s, %s);",
				classroomId, personId, sectionId);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	/**
	 * checks if section with given n(index) exists in given classroom
	 * 
	 * @param sectionN
	 * @param classroomId
	 * @return true- if exists, false - otherwise
	 */
	public boolean sectionExists(int sectionN, String classroomId) {
		String query = String.format("select * from `sections` where `section_n` = %s and `classroom_id` = %s;",
				sectionN, classroomId);
		MyConnection myConnection = db.getMyConnection(query);
		return !db.isResultEmpty(myConnection);
	}

	/**
	 * deletes section from database
	 * 
	 * @param sectionN
	 * @param classroomId
	 * @return true - if deletion was successful, false - otherwise
	 */
	public boolean deleteSection(String classroomId) {
		int sectionN  = getSections(classroomId).size() - 1;
		if (sectionN == -1) {
			return false;
		}
		String sectionID = getSectionId(sectionN, classroomId);

		String preQuery0 = String.format(
				"delete from `section-section_leader` where `section_id` = '%s' and `classroom_id` = %s;", sectionID,
				classroomId);

		String preQuery1 = String.format(
				"delete from `student-section` where `section_id` = '%s' and `classroom_id` = %s;", sectionID,
				classroomId);

		String query = String.format("delete from `sections` where `section_n` = %s and `classroom_id` = %s;",
				sectionN, classroomId);

		MyConnection myConnection = db.getMyConnection(preQuery0);
		db.executeUpdate(myConnection);

		myConnection = db.getMyConnection(preQuery1);
		db.executeUpdate(myConnection);

		myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	/**
	 * adds section leader to given section
	 * 
	 * @param sectionN
	 * @param email
	 * @param classroomId
	 * @return true - if section leader was added successfully, false -
	 *         otherwise
	 */
	public boolean addSectionLeaderToSection(int sectionN, String email, String classroomId) {
		String sectionId = getSectionId(sectionN, classroomId);
		String personId = personDB.getPersonId(email);
		if (sectionId.equals("") || personId.equals("")){
			return false;
		}
		String query = String.format(
				"insert into `section-section_leader` (`classroom_id`, `person_id`, `section_id`) values(%s, %s, %s);",
				classroomId, personId, sectionId);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}
	

	/**
	 * 
	 * @param classroomId
	 *            -Id of sections classroom
	 * @return returns ArrayList of sections associated with given classroom
	 */
	public ArrayList<Section> getSections(String classroomId) {
		String query = "select * from sections where `classroom_id`=" + classroomId + ";";
		ArrayList<Section> sections = new ArrayList<Section>();
		ResultSet rs = db.getResultSet(query);

		try {
			while (rs.next()) {
				sections.add(new Section( rs.getInt("section_n"), classroomId));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return sections;
	}
	
	
	/**
	 * returns section that contains smallest number of students
	 * @param classroomID - ID of classroom
	 * @return smallest Section
	 */
	public Section getSmallestSection(String classroomID) {
		ArrayList<Section> sections = getSections(classroomID);
		if (sections.isEmpty()) return null;
		
		Section section = null;
		int curMin = Integer.MAX_VALUE;
		
		for (Section sec : sections){
			int curSize = sec.getSectionStudents().size();
			
			if (curSize <= curMin){
				curMin = curSize;
				section = sec;
			}
		}
		return section;
	}
}
