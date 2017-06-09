package defPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DBConnection {
	
	private DataSource dataSource;
	
	public DBConnection(){
		PoolProperties p = new PoolProperties();
        p.setUrl(DBinfo.MYSQL_DATABASE_SERVER);
        p.setDriverClassName(DBinfo.JDBC_DRIVER);
        p.setUsername(DBinfo.MYSQL_USERNAME);
        p.setPassword(DBinfo.MYSQL_PASSWORD);
        
        dataSource = new DataSource();
        dataSource.setPoolProperties(p);
	}
	
	/**
	 * 
	 * @return new Connection
	 */
	private Connection getConnection() {
		
		Connection currentConnection = null;
		
		try {
			currentConnection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return currentConnection;
	}
	
	/**
	 * 
	 * @param query that PreparedStatement needs to execute
	 * @return PreparedStatement based on query
	 */
	private PreparedStatement getPreparedStatement (String query) {
		Connection con = getConnection();
		PreparedStatement stmnt = null;
		try {
			stmnt = con.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmnt;
	}
	
	/**
	 * 
	 * @param personID - ID of person whose information we'd like to get
	 * @return Person object
	 */
	public Person getPerson (String personId) {
		Person currentPerson = null;
		String query = String.format("select * from `persons` where `person_id`=%s;", personId);
		ResultSet rs;
		try {
			PreparedStatement stmnt = getPreparedStatement(query);
			rs = stmnt.executeQuery();
			while (rs.next()){
				currentPerson = new Person(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(1));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return currentPerson;
	}
	
	/**
	 * 
	 * @param query - MySQL query that lists persons
	 * @return person ArrayList
	 */
	private ArrayList <Person> getPersons (String query) {
		ResultSet rs = null;
		ArrayList <Person> persons = new ArrayList <Person> ();
		
		try {
			PreparedStatement stmnt = getPreparedStatement(query);
			rs = stmnt.executeQuery();
			while (rs.next()) {
				persons.add(getPerson(rs.getString(2)));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return persons;
	}

	/**
	 * 
	 * @param classroomID - ID of students classroom
	 * @return students of current classroom
	 */
	public ArrayList <Person> getStudents (String classroomId) {
		String query = String.format("select * from `classroom_students` where `classroom_id`=%s;", classroomId);
		ArrayList <Person> students = getPersons (query);
		return students;
	}
	
	/**
	 * 
	 * @param classroomID - ID of Section Leaders classroom
	 * @return section leaders of current classroom
	 */
	public ArrayList <Person> getSectionLeaders (String classroomId) {
		String query = String.format("select * from `classroom_section_leaders` where `classroom_id`=%s;", classroomId);
		ArrayList <Person> sectionLeaders = getPersons (query);
		return sectionLeaders;
	}
	
	/**
	 * 
	 * @param classroomID - ID of seminarists classroom
	 * @return seminarists of current classroom
	 */
	public ArrayList <Person> getSeminarists (String classroomId) {
		String query = String.format("select * from `classroom_seminarists` where `classroom_id`=%s;", classroomId);
		ArrayList <Person> seminarists = getPersons(query);
		return seminarists;
	}

	/**
	 * 
	 * @param classroomID - ID of lecturers classroom
	 * @return lecturers of current classroom
	 */
	public ArrayList <Person> getLecturers (String classroomId) {
		String query = String.format("select * from `classroom_lecturers` where `classroom_id`=%s;", classroomId);
		ArrayList <Person> lecturers = getPersons(query);
		return lecturers;
	}

	/**
	 * 
	 * @return returns ArrayList of current classrooms
	 */
	public ArrayList <Classroom> getClassrooms() {
		ArrayList <Classroom> classrooms = new ArrayList <Classroom>();
		String classroomsQuery = "select * from classrooms;";
		PreparedStatement stmnt = getPreparedStatement(classroomsQuery);
		
		try {
			ResultSet classroomsTable = stmnt.executeQuery();
			while (classroomsTable.next()) {
				String classroomID = classroomsTable.getString(1);
				String classroomName = classroomsTable.getString(2);
				
				ArrayList <Person> students = getStudents(classroomID);
				ArrayList <Person> sectionLeaders = getSectionLeaders(classroomID);
				ArrayList <Person> seminarists = getSeminarists(classroomID);
				ArrayList <Person> lecturers = getLecturers(classroomID);
				
				classrooms.add(new Classroom(classroomName, classroomID, sectionLeaders, seminarists, students, lecturers));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return classrooms;
	}
	
<<<<<<< HEAD
	public Classroom getclassroom(String classroomId){
		String query = String.format("select * from `classrooms` where `classroom_id` = %s", classroomId);
		Classroom classroom = null;
		try {
			PreparedStatement stmnt = getPreparedStatement(query);
			ResultSet rs = stmnt.executeQuery();
			if (rs.next()) {
				String classroomName = rs.getString(2);
				classroom = new Classroom(classroomName, classroomId, getSectionLeaders(classroomId), getSeminarists(classroomId),
						getStudents(classroomId), getLecturers(classroomId));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return classroom;
=======
	public Classroom getClassroom(String classroomId){
		return null;
>>>>>>> f1d7d7dec4a59ee740c398175dd3a32451343186
	}
	
	//cero
	public ArrayList <Seminar> getSeminars(String classroomId){
		return new ArrayList <Seminar>();
	}
	
	//cero
	public ArrayList <Section> getSections(String classroomId){
		return new ArrayList <Section>();
	}
	
	//cero
	public ArrayList <ActiveSeminar> getActiveSeminars(String classroomId){
		return new ArrayList <ActiveSeminar>();
	}
	
	
	
	/**
	 * 
	 * @param email
	 * @return personId of person with given Email
	 */
	public String getPersonId (String email) {
		String query = String.format("select `person_id` from `persons` where `person_email`='%s';", email);
		PreparedStatement stmnt = getPreparedStatement(query);
		String personId = "";
		try {
			ResultSet rs = stmnt.executeQuery();
			if (rs.next()) personId = rs.getString(1);
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return personId;
	}
	/**
	 * checks if given PreparedStatement returns empty result after executing
	 * @param stmnt - statement that has to be executed
	 * @return whether result set is empty or not
	 */
	private boolean isResultEmpty(PreparedStatement stmnt) {		
		ResultSet rs = null;
		boolean isEmpty;
		try {
			rs = stmnt.executeQuery();
			isEmpty = !rs.next();
		} catch (SQLException | NullPointerException e) {
			isEmpty = true;
		}
		
		return isEmpty;
	}

	/**
	 * checks if lecturer with given email exists in given classroom
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean lecturerExists (String email, String classroomId) {
		String personId = getPersonId(email);
		String query = String.format("select * from `classroom_lecturers` where `classroom_id`=%s and `person_id`=%s;",
				classroomId, personId);
		PreparedStatement stmnt = getPreparedStatement(query);
		return !isResultEmpty(stmnt);
	}

	/**
	 * checks if seminarist with given email exists in given classroom
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean seminaristExists(String email, String classroomId) {
		String personId = getPersonId(email);
		String query = String.format("select * from `classroom_seminarists` where `classroom_id`=%s and `person_id`=%s;",
				classroomId, personId);
		PreparedStatement stmnt = getPreparedStatement(query);
		return !isResultEmpty(stmnt);
	}

	/**
	 * checks if section leader with given email exists in given classroom
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean sectionLeaderExists(String email, String classroomId) {
		String personId = getPersonId(email);
		String query = String.format("select * from `classroom_section_leaders` where `classroom_id`=%s and `person_id`=%s;",
				classroomId, personId);
		PreparedStatement stmnt = getPreparedStatement(query);
		return !isResultEmpty(stmnt);
	}

	/**
	 * checks if student with given email exists in given classroom
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean studentExists(String email, String classroomId) {
		String personId = getPersonId(email);
		String query = String.format("select * from `classroom_students` where `classroom_id`=%s and `person_id`=%s;",
				classroomId, personId);
		PreparedStatement stmnt = getPreparedStatement(query);
		return !isResultEmpty(stmnt);
	}
	
	/**
	 * checks if person with given email exists in given classroom
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean personExists(String email, String classroomId) {
		return lecturerExists(email, classroomId) || seminaristExists(email, classroomId)
				|| sectionLeaderExists(email, classroomId) || studentExists (email, classroomId);
	}
	
	/**
	 * executes given PreaparedStatement
	 * @param stmnt
	 * @return true - if execution was successful, false - otherwise
	 */
	private boolean executeUpdate (PreparedStatement stmnt) {
		try {
			stmnt.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * adds lecturer with given email to the given class
	 * @param email
	 * @param classroomId
	 * @return true - if addition was successful, false - if lecturer already exists or there is no such person
	 */
	public boolean addLecturer(String email, String classroomId) {
		String personId = getPersonId(email);
		if (personId.equals("")) return false;
		if (lecturerExists(email, classroomId)) return false;
		String query = String.format("insert into `classroom_lecturers` (`classroom_id`, `person_id`) values (%s, %s);",
				classroomId, personId);
		PreparedStatement stmnt = getPreparedStatement(query);
		return executeUpdate(stmnt);
	}

	/**
	 * adds student with given email to the given classroom
	 * @param email
	 * @param classroomId
	 * @return true - if addition was successful, false - if student already exists or there is no such person
	 */
	public boolean addStudent(String email, String classroomId) {
		String personId = getPersonId(email);
		if (personId.equals("")) return false;
		if (studentExists(email, classroomId)) return false;
		String query = String.format("insert into `classroom_students` (`classroom_id`, `person_id`) values (%s, %s);",
				classroomId, personId);
		PreparedStatement stmnt = getPreparedStatement(query);
		return executeUpdate(stmnt);
	}

	/**
	 * adds section leader with given email to the given classroom
	 * @param email
	 * @param classroomId
	 * @return true - if addition was successful, false - if section leaders already exists or there is no such person
	 */
	public boolean addSectionLeader(String email, String classroomId) {
		String personId = getPersonId(email);
		if (personId.equals("")) return false;
		if (sectionLeaderExists(email, classroomId)) return false;
		String query = String.format("insert into `classroom_section_leaders` (`classroom_id`, `person_id`) values (%s, %s);",
				classroomId, personId);
		PreparedStatement stmnt = getPreparedStatement(query);
		return executeUpdate(stmnt);
	}

	/**
	 * adds seminarist with given email to the given classroom
	 * @param email
	 * @param classroomId
	 * @return true - if addition was successful, false - if such seminarist already exists or there is no such person
	 */
	public boolean addSeminarist(String email, String classroomId) {
		String personId = getPersonId(email);
		if (personId.equals("")) return false;
		if (seminaristExists(email, classroomId)) return false;
		String query = String.format("insert into `classroom_seminarists` (`classroom_id`, `person_id`) values (%s, %s);",
				classroomId, personId);
		PreparedStatement stmnt = getPreparedStatement(query);
		return executeUpdate(stmnt);
	}

	/**
	 * deletes seminarist with given email from given classroom
	 * @param email
	 * @param classroomId
	 * @return true - if deletion was successful, false - if there was no such seminarist or database crashed
	 */
	public boolean deleteSeminarist(String email, String classroomId) {
		if (!seminaristExists(email, classroomId)) return false;
		String personId = getPersonId(email);
		String query = String.format("delete from `classroom_seminarists` where `classroom_id` = %s and `person_id` = %s;", 
				classroomId, personId);
		PreparedStatement stmnt = getPreparedStatement(query);
		return executeUpdate(stmnt);
	}

	/**
	 * deletes student with given email from given classroom
	 * @param email
	 * @param classroomId
	 * @return true - if deletion was successful, false - if there was no such student or database crashed
	 */
	public boolean deleteStudent(String email, String classroomId) {
		if (!studentExists(email, classroomId)) return false;
		String personId = getPersonId(email);
		String query = String.format("delete from `classrom_students` where `classroom_id` = %s and `person_id` = %s;", 
				classroomId, personId);
		PreparedStatement stmnt = getPreparedStatement(query);
		return executeUpdate(stmnt);
	}

	/**
	 * deletes section leader with given email from given classroom
	 * @param email
	 * @param classroomId
	 * @return true - if deletion was successful, false - if there was no such section leader or database crashed
	 */
	public boolean deleteSectionLeader(String email, String classroomId) {
		if (!sectionLeaderExists(email, classroomId)) return false;
		String personId = getPersonId(email);
		String query = String.format("delete from `classroom_students` where `classroom_id` = %s and `person_id` =%s;", 
				classroomId, personId);
		PreparedStatement stmnt = getPreparedStatement(query);
		return executeUpdate(stmnt);
	}
	
	/**
	 * deletes lecturer with given email from given classroom
	 * @param email
	 * @param classroomId
	 * @return true - if deletion was successful, false - if there was no such lecturer or database crashed
	 */
	public boolean deleteLecturer(String email, String classroomId) {
		if (!lecturerExists(email, classroomId)) return false;
		String personId = getPersonId(email);
		String query = String.format("delete from `classroom_lecturers` where `classroom_id` = %s and `person_id` = %s;", 
				classroomId, personId);
		PreparedStatement stmnt = getPreparedStatement(query);
		return executeUpdate(stmnt);
	}
	
	
}
