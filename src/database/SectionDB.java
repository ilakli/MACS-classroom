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
	private AllConnections allConnections;
	public SectionDB(AllConnections allConnections) {
		this.allConnections = allConnections;
		db = allConnections.db;
		personDB = allConnections.personDB;
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
	 * @param classroomId
	 * @param personId
	 * @return
	 */
	public boolean containsStudent (String classroomId, String personId) {
		String query = String.format("select * from `student-section` where `person_id` = %s and `classroom_id` = %s;", 
				personId, classroomId);
		MyConnection myConnection = db.getMyConnection(query);
		return !db.isResultEmpty(myConnection);
	}
	
	/**
	 * removes given person from section of given classroom
	 * @param classroomId
	 * @param personId
	 * @return
	 */
	public boolean removeStudent(String classroomId, String studentEmail) {
		String personId = personDB.getPersonId(studentEmail);
		String query = String.format("delete from `student-section` where `classroom_id` = %s and `person_id` = %s;", 
				classroomId, personId);
		
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}
	/**
	 * Returns section according to classroom id and student Email.
	 * @param classroomId id of classroom
	 * @param studentEmail email of student
	 * @return Section
	 */
	public Section getSection(String classroomId, String studentEmail){
		String personId = personDB.getPersonId(studentEmail);
		String query = String.format("select * from sections where `section_id` =( select `section_id` from `student-section` where `classroom_id` = %s and `person_id` = %s);", 
				classroomId, personId);
		MyConnection myConnection = db.getMyConnection(query);
		Section result = null;
		try {
			ResultSet rs = myConnection.executeQuery();
			if (rs != null && rs.next())
				result = new Section(Integer.parseInt(rs.getString(3)), rs.getString(2), allConnections);
		} catch (SQLException e) {
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		return result;
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
		String sectionId = "";
		try {
			ResultSet rs = myConnection.executeQuery();
			if (rs != null && rs.next())
				sectionId = rs.getString(1);
		} catch (SQLException e) {
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
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
	 * adds given students to given section
	 * @param sectionN
	 * @param students
	 * @param classroomId
	 * @return
	 */
	
	public boolean addStudentsToSection(int sectionN, ArrayList<Person> students, String classroomId) {
		String sectionId = getSectionId(sectionN, classroomId);
		if (sectionId.equals("")) {
			return false;
		}
		if (students.isEmpty()) {
			return true;
		}
		
		String query = "insert into `student-section` (`classroom_id`, `person_id`, `section_id`) values\n";
		
		for (int i = 0; i < students.size(); i++){
			Person p = students.get(i);
			query += "(" + classroomId + ", " + p.getPersonID() + ", " + sectionId + ")";
			if (i + 1 < students.size()) query += ",\n";
		}
		
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
		MyConnection myConnection = db.getMyConnection(query);
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs.next()) {
				sections.add(new Section(rs.getInt("section_n"), classroomId, rs.getInt("section_size"),allConnections));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
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
	
	/**
	 * updates section_size in database
	 * @param sectionN
	 * @param classroomId
	 * @param sectionSize
	 */
	public void updateSectionSize(int sectionN, String classroomId) {
		String sectionId = getSectionId(sectionN, classroomId);
		String query = String.format(
				"update `sections` set `section_size` = (select count(*) from `student-section` where section_id = %s) where `section_id` = %s and `classroom_id` = %s",
				sectionId,sectionId, classroomId);
		MyConnection myConnection = db.getMyConnection(query);
		db.executeUpdate(myConnection);
	}
	
	
	/**
	 * finds section by its leader
	 * @param leader - leader
	 * @param classroomID - id of section's classroom
	 * @return - found section
	 */
	public Section getSectionByLeader(Person leader, String classroomID) {
		
		String query = String.format("select s.section_n, s.section_size "
				+ "from `classrooms` c,`persons` p,`classroom_section_leaders` csl,`sections` s" 
				+ " where c.classroom_id = %s and c.classroom_id = csl.classroom_id"
				+ " and p.person_id = %s and s.classroom_id = c.classroom_id;", classroomID, leader.getPersonID());
				
				
		Section section = null;
		
		MyConnection myConnection = db.getMyConnection(query);
		try {
			ResultSet rs = myConnection.executeQuery();
			if (rs.next()) {
				section = new Section(rs.getInt("section_n"), classroomID, rs.getInt("section_size"),allConnections);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}

		return section;
		
	}

}
