package defPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
			// TODO Auto-generated catch block
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
	public Person getPerson (String personID) {
		Person currentPerson = null;
		String query = "select * from persons where `person_id`=" + personID + ";";
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
		Connection con = getConnection();
		ArrayList <Person> persons = new ArrayList <Person> ();
		
		try {
			PreparedStatement stmnt = con.prepareStatement(query);
			rs = stmnt.executeQuery();
			while (rs.next()) {
				persons.add(getPerson(rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return persons;
	}

	/**
	 * 
	 * @param classroomID - ID of students classroom
	 * @return students of current classroom
	 */
	public ArrayList <Person> getStudents (String classroomID) {
		String query = "select * from classroom_students where `classroom_id`=" + classroomID + ";";
		ArrayList <Person> students = getPersons (query);
		return students;
	}
	
	/**
	 * 
	 * @param classroomID - ID of Section Leaders classroom
	 * @return section leaders of current classroom
	 */
	public ArrayList <Person> getSectionLeaders (String classroomID) {
		String query = "select * from classroom_section_leaders where `classroom_id`=" + classroomID + ";";
		ArrayList <Person> sectionLeaders = getPersons (query);
		return sectionLeaders;
	}
	
	/**
	 * 
	 * @param classroomID - ID of seminarists classroom
	 * @return seminarists of current classroom
	 */
	public ArrayList <Person> getSeminarists (String classroomID) {
		String query = "select * from classroom_seminarists where `classroom_id`=" + classroomID + ";";
		ArrayList <Person> seminarists = getPersons(query);
		return seminarists;
	}

	/**
	 * 
	 * @param classroomID - ID of lecturers classroom
	 * @return lecturers of current classroom
	 */
	public ArrayList <Person> getLecturers (String classroomID) {
		String query = "select * from classroom_lecturers where `classroom_id`=" + classroomID + ";";
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
	
	public Classroom getClassroom(String classroomId){
		return null;
	}
	
	//cero
	public ArrayList <Seminar> getSeminars(String classroomId){
		return null;
	}
	
	//cero
	public ArrayList <Section> getSections(String classroomId){
		return null;
	}
	
	//cero
	public ArrayList <ActiveSeminar> getActiveSeminars(String classroomId){
		return null;
	}
	
	
	
	/**
	 * 
	 * @param Email
	 * @return personID of person with given Email
	 */
	public String getPersonId (String Email) {
		String query = "select `person_id` from `persons` where `person_email`='" + Email + "'";
		PreparedStatement stmnt = getPreparedStatement(query);
		String personId = null;
		try {
			ResultSet rs = stmnt.executeQuery();
			if (rs.next()) personId = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personId;
	}
	
	public boolean lecturerExists (String Email, String classroomId) {
		return false;
	}

	public boolean seminaristExists(String mail, String classroomId) {
		return false;
	}

	public boolean sectionLeaderExists(String mail, String classroomId) {
		return false;
	}

	public boolean studentExists(String mail, String classroomId) {
		return false;
	}

	public boolean personExists(String mail, String classroomId) {
		return false;
	}

	public boolean addLecturer(String mail, String classroomId) {
		return false;
	}

	public boolean addStudent(String mail, String classroomId) {
		return false;
	}

	public boolean addSectionLeader(String mail, String classroomId) {
		return false;
	}

	public boolean addSeminarist(String mail, String classroomId) {
		return false;
	}

	public boolean deleteSeminarist(String mail, String classroomId) {
		return false;
	}

	public boolean deleteStudent(String mail, String classroomId) {
		return false;
	}

	public boolean deleteSectionLeader(String mail, String classroomId) {
		return false;
	}

	public boolean deleteLecturer(String mail, String classroomId) {
		return false;
	}
	
	
}
