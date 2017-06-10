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
	public static final String DATABASE_ERROR = "DATABASE ERROR";
	
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
	public Connection getConnection() {
		
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
	 * @param query
	 * 			-SQL query
	 * @return returns ResultSet object of this query
	 */
	private ResultSet getResultSet(String query) {

		Connection con = getConnection();
		ResultSet rs = null;

		try {
			PreparedStatement stmnt = con.prepareStatement(query);
			rs = stmnt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}
	
	/**
	 * 
	 * @param query that PreparedStatement needs to execute
	 * @return MyConnection object which includes Connection and PreparedStatement
	 */
	private MyConnection getMyConnection (String query) {
		Connection con = getConnection();
		PreparedStatement stmnt = null;
		try {
			stmnt = con.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new MyConnection(con, stmnt);
	}
	
	/**
	 * 
	 * @param personID - ID of person whose information we'd like to get
	 * @return Person object
	 */
	public Person getPerson (String personId) {
		Person currentPerson = null;
		String query = String.format("select * from `persons` where `person_id`=%s;", personId);
		MyConnection myConnection = null;
		try {
			myConnection = getMyConnection(query);
			ResultSet rs = myConnection.executeQuery();
			while (rs.next()){
				currentPerson = new Person(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(1));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) myConnection.closeConnection();
		}
		
		return currentPerson;
	}
	
	/**
	 * 
	 * @param query - MySQL query that lists persons
	 * @return person ArrayList
	 */
	private ArrayList <Person> getPersons (String query) {

		ArrayList <Person> persons = new ArrayList <Person> ();
		MyConnection myConnection = null;
		try {
			myConnection = getMyConnection(query);
			ResultSet rs = myConnection.executeQuery();
			while (rs.next()) {
				persons.add(getPerson(rs.getString(2)));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) myConnection.closeConnection();
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
		MyConnection stmnt = getMyConnection(classroomsQuery);
		
		try {
			ResultSet classroomsTable = stmnt.executeQuery();
			while (classroomsTable.next()) {
				String classroomID = classroomsTable.getString(1);
				String classroomName = classroomsTable.getString(2);
				
				classrooms.add(new Classroom(classroomName, classroomID));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return classrooms;
	}

	/**
	 * returns classroom object based on classroomId
	 * @param classroomId
	 * @return classroom object
	 */
	public Classroom getClassroom(String classroomId){
		String query = String.format("select * from `classrooms` where `classroom_id` = %s", classroomId);
		Classroom classroom = null;
		MyConnection myConnection = null;
		try {
			myConnection = getMyConnection(query);
			ResultSet rs = myConnection.executeQuery();
			if (rs.next()) {
				String classroomName = rs.getString(2);
				classroom = new Classroom(classroomName, classroomId);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) myConnection.closeConnection();
		}
		return classroom;
	}
	
	/**
	 * adds classroom with given name to the database
	 * @param classroomName
	 * @return classromId of newly added classroom
	 */
	public String addClassroom (String classroomName) {
		String classroomId = DATABASE_ERROR;
		Connection con = getConnection();
		try {
			con.setAutoCommit(false);

			PreparedStatement insertClassroom = con.prepareStatement(String.format(
					"insert into `classrooms` (`classroom_name`) values ('%s');", classroomName));
			insertClassroom.executeUpdate();

			PreparedStatement selectLastIndex = con.prepareStatement("select last_insert_id();");
			ResultSet rs = selectLastIndex.executeQuery();

			if (rs.next()) classroomId = rs.getString(1);
			
			con.commit();
			con.close();
		} catch(SQLException e) {
		}
		
		return classroomId;
	}
	
	/**
	 * 
	 * @param email
	 * @return personId of person with given Email
	 */
	public String getPersonId (String email) {
		String query = String.format("select `person_id` from `persons` where `person_email`='%s';", email);
		MyConnection myConnection = getMyConnection(query);
		String personId = "";
		try {
			ResultSet rs = myConnection.executeQuery();
			if (rs.next()) personId = rs.getString(1);
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return personId;
	}
	
	/**
	 * checks if given MyConnection returns empty result after executing
	 * @param myConnection - object that saves PreparedStatement
	 * @return whether result set is empty or not
	 */
	private boolean isResultEmpty(MyConnection myConnection) {		
		ResultSet rs = null;
		boolean isEmpty;
		try {
			rs = myConnection.executeQuery();
			isEmpty = !rs.next();
		} catch (SQLException | NullPointerException e) {
			isEmpty = true;
		} finally {
			if (myConnection != null) myConnection.closeConnection();
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
		MyConnection myConnection = getMyConnection(query);
		return !isResultEmpty(myConnection);
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
		MyConnection myConnection = getMyConnection(query);
		return !isResultEmpty(myConnection);
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
		MyConnection myConnection = getMyConnection(query);
		return !isResultEmpty(myConnection);
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
		MyConnection myConnection = getMyConnection(query);
		return !isResultEmpty(myConnection);
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
	 * executes given myConnection
	 * @param myConnection
	 * @return true - if execution was successful, false - otherwise
	 */
	private boolean executeUpdate (MyConnection myConnection) {
		try {
			myConnection.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * adds new person with given name, surname and email address to the persons table
	 * @param name
	 * @param surname
	 * @param email
	 * @return	true- if update executed successfully, false - otherwise
	 */
	public boolean addPerson(String name, String surname, String email ){
		String query = String.format("insert into `persons` (`person_name`, `person_surname`, `person_email`)"
				+ " values ('%s', '%s', '%s');",
				name, surname, email);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		String query = String.format("delete from `classroom_students` where `classroom_id` = %s and `person_id` = %s;", 
				classroomId, personId);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		String query = String.format("delete from `classroom_section_leaders` where `classroom_id` = %s and `person_id` =%s;", 
				classroomId, personId);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
	}

	/**
	 * 
	 * @param classroomId
	 *            -Id of seminars classroom
	 * @return returns ArrayList of seminars associated with given classroom
	 */
	public ArrayList<Seminar> getSeminars(String classroomId) {
		String query = "select * from seminars where `classroom_id`=" + classroomId + ";";
		ArrayList<Seminar> seminars = new ArrayList<Seminar>();
		ResultSet rs = getResultSet(query);

		try {
			while (rs.next()) {
				seminars.add(new Seminar(rs.getString(1), rs.getString(3), classroomId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return seminars;
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
		ResultSet rs = getResultSet(query);

		try {
			while (rs.next()) {
				sections.add(new Section(rs.getString(1), rs.getString(3), classroomId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sections;
	}

	/**
	 * 
	 * @param classroomId
	 *            -Id of active seminars classroom
	 * @return returns ArrayList of active seminars associated with given classroom
	 */
	public ArrayList<ActiveSeminar> getActiveSeminars(String classroomId) {
		String query = "select * from seminars_timetable where `seminar_id` "
				+ "in (select seminar_id from seminars where `classroom_id` = " + classroomId + ");";
		ArrayList<ActiveSeminar> activeSeminars = new ArrayList<ActiveSeminar>();
		ResultSet rs = getResultSet(query);
		
		try {
			while (rs.next()) {
				activeSeminars.add(new ActiveSeminar(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return activeSeminars;
	}
	/**
	 * 
	 * this is class which saves connection and given prepared statement
	 *
	 */
	private class MyConnection {
		public Connection con;
		public PreparedStatement stmnt;
		public MyConnection(Connection con, PreparedStatement stmnt) {
			this.con = con;
			this.stmnt = stmnt;
		}
		
		public ResultSet executeQuery() {
			try {
				return stmnt.executeQuery();
			} catch (SQLException e) {
				return null;
			}
		}
		
		public void closeConnection() {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		
		public void executeUpdate() throws SQLException {
			stmnt.executeUpdate();
		}
	}
}
