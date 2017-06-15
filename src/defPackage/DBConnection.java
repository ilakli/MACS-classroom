package defPackage;

import java.awt.List;
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

	// creates constructor
	protected void createConstructor() {
		PoolProperties p = new PoolProperties();
		p.setUrl(DBinfo.MYSQL_DATABASE_SERVER);
		p.setDriverClassName(DBinfo.JDBC_DRIVER);
		p.setUsername(DBinfo.MYSQL_USERNAME);
		p.setPassword(DBinfo.MYSQL_PASSWORD);

		dataSource = new DataSource();
		dataSource.setPoolProperties(p);
	}

	public DBConnection() {
		// delete here to uses tests;
		createConstructor();
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
	 *            -SQL query
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
	 * @param query
	 *            that PreparedStatement needs to execute
	 * @return MyConnection object which includes Connection and
	 *         PreparedStatement
	 */
	private MyConnection getMyConnection(String query) {
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
	 * @param personID
	 *            - ID of person whose information we'd like to get
	 * @return Person object
	 */
	public Person getPerson(String personId) {
		Person currentPerson = null;
		String query = String.format("select * from `persons` where `person_id`=%s;", personId);
		MyConnection myConnection = null;
		try {
			myConnection = getMyConnection(query);
			ResultSet rs = myConnection.executeQuery();
			while (rs.next()) {
				currentPerson = new Person(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(1));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}

		return currentPerson;
	}

	/**
	 * returns Person by email
	 * 
	 * @param email
	 *            - email of needed person
	 * @return Person if there exists one with such email, null otherwise.
	 */
	public Person getPersonByEmail(String email) {
		Person currentPerson = null;

		String query = String.format("select * from `persons` where `person_email`=\"%s\"", email);
		MyConnection myConnection = null;
		try {
			myConnection = getMyConnection(query);
			ResultSet rs = myConnection.executeQuery();
			while (rs.next()) {
				currentPerson = new Person(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(1));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}

		return currentPerson;
	}

	/**
	 * 
	 * @param query
	 *            - MySQL query that lists persons
	 * @return person ArrayList
	 */
	private ArrayList<Person> getPersons(String query) {

		ArrayList<Person> persons = new ArrayList<Person>();
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
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}

		return persons;
	}

	/**
	 * 
	 * @param classroomID
	 *            - ID of students classroom
	 * @return students of current classroom
	 */
	public ArrayList<Person> getStudents(String classroomId) {
		String query = String.format("select * from `classroom_students` where `classroom_id`=%s;", classroomId);
		ArrayList<Person> students = getPersons(query);
		return students;
	}

	/**
	 * 
	 * @param classroomID
	 *            - ID of Section Leaders classroom
	 * @return section leaders of current classroom
	 */
	public ArrayList<Person> getSectionLeaders(String classroomId) {
		String query = String.format("select * from `classroom_section_leaders` where `classroom_id`=%s;", classroomId);
		ArrayList<Person> sectionLeaders = getPersons(query);
		return sectionLeaders;
	}

	/**
	 * 
	 * @param classroomID
	 *            - ID of seminarists classroom
	 * @return seminarists of current classroom
	 */
	public ArrayList<Person> getSeminarists(String classroomId) {
		String query = String.format("select * from `classroom_seminarists` where `classroom_id`=%s;", classroomId);
		ArrayList<Person> seminarists = getPersons(query);
		return seminarists;
	}

	/**
	 * 
	 * @param classroomID
	 *            - ID of lecturers classroom
	 * @return lecturers of current classroom
	 */
	public ArrayList<Person> getLecturers(String classroomId) {
		String query = String.format("select * from `classroom_lecturers` where `classroom_id`=%s;", classroomId);
		ArrayList<Person> lecturers = getPersons(query);
		return lecturers;
	}

	/**
	 * 
	 * @return returns ArrayList of current classrooms
	 */
	public ArrayList<Classroom> getClassrooms() {
		ArrayList<Classroom> classrooms = new ArrayList<Classroom>();
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
	 * 
	 * @param classroomId
	 * @return classroom object
	 */
	public Classroom getClassroom(String classroomId) {
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
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		return classroom;
	}

	/**
	 * adds classroom with given name to the database
	 * 
	 * @param classroomName
	 * @return classromId of newly added classroom
	 */
	public String addClassroom(String classroomName) {
		String classroomId = DATABASE_ERROR;
		Connection con = getConnection();
		try {
			con.setAutoCommit(false);

			PreparedStatement insertClassroom = con.prepareStatement(
					String.format("insert into `classrooms` (`classroom_name`) values ('%s');", classroomName));
			insertClassroom.executeUpdate();

			PreparedStatement selectLastIndex = con.prepareStatement("select last_insert_id();");
			ResultSet rs = selectLastIndex.executeQuery();

			if (rs.next())
				classroomId = rs.getString(1);

			con.commit();
			con.close();
		} catch (SQLException e) {
		}

		return classroomId;
	}

	/**
	 * 
	 * @param email
	 * @return personId of person with given Email
	 */
	public String getPersonId(String email) {
		String query = String.format("select `person_id` from `persons` where `person_email`='%s';", email);
		MyConnection myConnection = getMyConnection(query);
		String personId = "";
		try {
			ResultSet rs = myConnection.executeQuery();
			if (rs.next())
				personId = rs.getString(1);
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return personId;
	}

	/**
	 * checks if given MyConnection returns empty result after executing
	 * 
	 * @param myConnection
	 *            - object that saves PreparedStatement
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
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}

		return isEmpty;
	}

	/**
	 * checks if lecturer with given email exists in given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean lecturerExists(String email, String classroomId) {
		String personId = getPersonId(email);
		String query = String.format("select * from `classroom_lecturers` where `classroom_id`=%s and `person_id`=%s;",
				classroomId, personId);
		MyConnection myConnection = getMyConnection(query);
		return !isResultEmpty(myConnection);
	}

	/**
	 * checks if seminarist with given email exists in given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean seminaristExists(String email, String classroomId) {
		String personId = getPersonId(email);
		String query = String.format(
				"select * from `classroom_seminarists` where `classroom_id`=%s and `person_id`=%s;", classroomId,
				personId);
		MyConnection myConnection = getMyConnection(query);
		return !isResultEmpty(myConnection);
	}

	/**
	 * checks if section leader with given email exists in given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean sectionLeaderExists(String email, String classroomId) {
		String personId = getPersonId(email);
		String query = String.format(
				"select * from `classroom_section_leaders` where `classroom_id`=%s and `person_id`=%s;", classroomId,
				personId);
		MyConnection myConnection = getMyConnection(query);
		return !isResultEmpty(myConnection);
	}

	/**
	 * checks if student with given email exists in given classroom
	 * 
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
	 * 
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean personExists(String email, String classroomId) {
		return lecturerExists(email, classroomId) || seminaristExists(email, classroomId)
				|| sectionLeaderExists(email, classroomId) || studentExists(email, classroomId);
	}

	/**
	 * executes given myConnection
	 * 
	 * @param myConnection
	 * @return true - if execution was successful, false - otherwise
	 */
	private boolean executeUpdate(MyConnection myConnection) {
		boolean result;
		try {
			myConnection.executeUpdate();
			result = true;
		} catch (SQLException e) {
			result = false;
		} finally {
			myConnection.closeConnection();
		}
		return result;
	}

	/**
	 * adds new person with given name, surname and email address to the persons
	 * table
	 * 
	 * @param name
	 * @param surname
	 * @param email
	 * @return true- if update executed successfully, false - otherwise
	 */
	public boolean addPerson(String name, String surname, String email) {
		String query = String.format("insert into `persons` (`person_name`, `person_surname`, `person_email`)"
				+ " values ('%s', '%s', '%s');", name, surname, email);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		String personId = getPersonId(email);
		if (personId.equals(""))
			return false;
		if (lecturerExists(email, classroomId))
			return false;
		String query = String.format("insert into `classroom_lecturers` (`classroom_id`, `person_id`) values (%s, %s);",
				classroomId, personId);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		String personId = getPersonId(email);
		if (personId.equals(""))
			return false;
		if (studentExists(email, classroomId))
			return false;
		String query = String.format("insert into `classroom_students` (`classroom_id`, `person_id`) values (%s, %s);",
				classroomId, personId);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
	}

	/**
	 * adds section to the database
	 * 
	 * @param sectionName
	 * @param classroomId
	 * @return - true if section was added successfuly, false otherwise
	 */
	public boolean addSection(String sectionName, String classroomId) {
		String query = String.format("insert into `sections` (`section_name`, `classroom_id`) values ('%s', %s);",
				sectionName, classroomId);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
	}

	/**
	 * 
	 * @param sectionName
	 * @param classroomId
	 * @return sectionId based on classroomId and sectionName
	 */
	public String getSectionId(String sectionName, String classroomId) {
		String query = String.format(
				"select `section_id` from `sections` where `classroom_id` = %s and `section_name` = '%s';", classroomId,
				sectionName);
		MyConnection myConnection = getMyConnection(query);
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
	 * @param sectionName
	 * @param studentEmail
	 * @param classroomId
	 * @return true - if student was successfully added to the given section,
	 *         flase - otherwise
	 */
	public boolean addStudentToSection(String sectionName, String studentEmail, String classroomId) {
		String sectionId = getSectionId(sectionName, classroomId);
		String personId = getPersonId(studentEmail);
		if (sectionId.equals(""))
			return false;
		if (personId.equals(""))
			return false;
		String query = String.format(
				"insert into `student-section` (`classroom_id`, `person_id`, `section_id`) values(%s, %s, %s);",
				classroomId, personId, sectionId);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
	}

	/**
	 * checks if section with given name exists in given classroom
	 * 
	 * @param sectionName
	 * @param classroomId
	 * @return true- if exists, false - otherwise
	 */
	public boolean sectionExists(String sectionName, String classroomId) {
		String query = String.format("select * from `sections` where `section_name` = '%s' and `classroom_id` = %s;",
				sectionName, classroomId);
		MyConnection myConnection = getMyConnection(query);
		return !isResultEmpty(myConnection);
	}

	/**
	 * deletes section from database
	 * 
	 * @param sectionName
	 * @param classroomId
	 * @return true - if deletion was successful, false - otherwise
	 */
	public boolean deleteSection(String sectionName, String classroomId) {
		if (!sectionExists(sectionName, classroomId))
			return false;
		String sectionID = getSectionId(sectionName, classroomId);

		String preQuery0 = String.format(
				"delete from `section-section_leader` where `section_id` = '%s' and `classroom_id` = %s;", sectionID,
				classroomId);

		String preQuery1 = String.format(
				"delete from `student-section` where `section_id` = '%s' and `classroom_id` = %s;", sectionID,
				classroomId);

		String query = String.format("delete from `sections` where `section_name` = '%s' and `classroom_id` = %s;",
				sectionName, classroomId);

		MyConnection myConnection = getMyConnection(preQuery0);
		executeUpdate(myConnection);

		myConnection = getMyConnection(preQuery1);
		executeUpdate(myConnection);

		myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		String personId = getPersonId(email);
		if (personId.equals(""))
			return false;
		if (sectionLeaderExists(email, classroomId))
			return false;
		String query = String.format(
				"insert into `classroom_section_leaders` (`classroom_id`, `person_id`) values (%s, %s);", classroomId,
				personId);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
	}

	/**
	 * adds section leader to given section
	 * 
	 * @param sectionName
	 * @param email
	 * @param classroomId
	 * @return true - if section leader was added successfully, false -
	 *         otherwise
	 */
	public boolean addSectionLeaderToSection(String sectionName, String email, String classroomId) {
		String sectionId = getSectionId(sectionName, classroomId);
		String personId = getPersonId(email);
		if (sectionId.equals("") || personId.equals(""))
			return false;
		String query = String.format(
				"insert into `section-section_leader` (`classroom_id`, `person_id`, `section_id`) values(%s, %s, %s);",
				classroomId, personId, sectionId);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
	}

	/**
	 * adds seminar to the database
	 * 
	 * @param seminarName
	 * @param classroomId
	 * @return - true if seminar was added successfully, false otherwise
	 */
	public boolean addSeminar(String seminarName, String classroomId) {
		String query = String.format("insert into `seminars` (`classroom_id`, `seminar_name`) values (%s, '%s');",
				classroomId, seminarName);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
	}

	/**
	 * adds given student to given seminar
	 * 
	 * @param seminarName
	 * @param studentEmail
	 * @param classroomId
	 * @return true - if student was successfully added, false otherwise
	 */
	public boolean addStudentToSeminar(String seminarName, String studentEmail, String classroomId) {
		String seminarId = getSeminarId(seminarName, classroomId);
		String personId = getPersonId(studentEmail);
		if (seminarId.equals("") || personId.equals(""))
			return false;
		String query = String.format(
				"insert into `student-seminar` (`classroom_id`, `person_id`, `seminar_id`) values(%s, %s, %s);",
				classroomId, personId, seminarId);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
	}

	/**
	 * 
	 * @param seminarName
	 * @param classroomId
	 * @return seminarId based on classroomId and seminarName
	 */
	public String getSeminarId(String seminarName, String classroomId) {
		String query = String.format(
				"select `seminar_id` from `seminars` where `seminar_name` = '%s' and `classroom_id` = %s;", seminarName,
				classroomId);
		MyConnection myConnection = getMyConnection(query);
		ResultSet rs = myConnection.executeQuery();
		String seminarId = "";
		try {
			if (rs != null && rs.next())
				seminarId = rs.getString(1);
		} catch (SQLException e) {
		}
		myConnection.closeConnection();
		return seminarId;
	}

	/**
	 * adds row to seminars_timetable in database
	 * 
	 * @param activeSeminarName
	 * @param seminarName
	 * @param time
	 * @param location
	 * @param classroomId
	 * @return true if it was added successfuly, false otherwise
	 */
	public boolean addActiveSeminar(String activeSeminarName, String seminarName, String time, String location,
			String classroomId) {
		String seminarId = getSeminarId(seminarName, classroomId);
		if (seminarId.equals(""))
			return false;
		String query = String
				.format("insert into `seminars_timetable` (`seminar_id`, `seminar_name`, `seminar_location`, `seminar_time`) "
						+ "values (%s, '%s', '%s', '%s');", seminarId, seminarName, location, time);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
	}

	/**
	 * checks if seminar in given classroom with given id exists
	 * 
	 * @param seminarName
	 * @param classroomId
	 * @return true - if seminar exists, false - otherwise
	 */
	boolean seminarExists(String seminarName, String classroomId) {
		String query = String.format("select * from `seminars` where `seminar_name` = '%s' and `classroom_id` = %s;",
				seminarName, classroomId);
		MyConnection myConnection = getMyConnection(query);
		return !isResultEmpty(myConnection);
	}

	/**
	 * deletes seminar from database
	 * 
	 * @param seminarName
	 * @param classroomId
	 * @return true - if seminar was successfully deleted, false - otherwise
	 */
	public boolean deleteSeminar(String seminarName, String classroomId) {
		if (!seminarExists(seminarName, classroomId))
			return false;
		String seminarID = getSeminarId(seminarName, classroomId);

		String preQuery0 = String.format(
				"delete from `seminar-seminarists` where `seminar_id` = '%s' and `classroom_id` = %s;", seminarID,
				classroomId);
		String preQuery1 = String.format("delete from `seminars_timetable` where `seminar_id` = '%s';", seminarID);
		String preQuery2 = String.format(
				"delete from `student-seminar` where `seminar_id` = '%s' and `classroom_id` = %s;", seminarID,
				classroomId);
		String query = String.format("delete from `seminars` where `seminar_name` = '%s' and `classroom_id` = %s;",
				seminarName, classroomId);

		MyConnection myConnection = getMyConnection(preQuery0);
		executeUpdate(myConnection);

		myConnection = getMyConnection(preQuery1);
		executeUpdate(myConnection);

		myConnection = getMyConnection(preQuery2);
		executeUpdate(myConnection);

		myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		String personId = getPersonId(email);
		if (personId.equals(""))
			return false;
		if (seminaristExists(email, classroomId))
			return false;
		String query = String.format(
				"insert into `classroom_seminarists` (`classroom_id`, `person_id`) values (%s, %s);", classroomId,
				personId);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
	}

	/**
	 * adds seminarist to seminar in database
	 * 
	 * @param seminarName
	 * @param email
	 * @param classroomId
	 * @return true - if seminarists was successfully added, false otherwise
	 */
	public boolean addSeminaristToSeminar(String seminarName, String email, String classroomId) {
		String seminarId = getSeminarId(seminarName, classroomId);
		String personId = getPersonId(email);
		if (seminarId.equals("") || personId.equals(""))
			return false;
		String query = String.format(
				"insert into `seminar-seminarists` (`seminar_id`, `classroom_id`, `person_id`) values(%s, %s, %s);",
				seminarId, classroomId, personId);
		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		if (!seminaristExists(email, classroomId))
			return false;
		String personId = getPersonId(email);

		String preQuery = String.format(
				"delete from `seminar-seminarists` where `classroom_id` = %s and `person_id` = %s;", classroomId,
				personId);
		String query = String.format(
				"delete from `classroom_seminarists` where `classroom_id` = %s and `person_id` = %s;", classroomId,
				personId);

		MyConnection myConnection = getMyConnection(preQuery);
		executeUpdate(myConnection);

		myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		if (!studentExists(email, classroomId))
			return false;
		String personId = getPersonId(email);

		String preQuery0 = String.format(
				"delete from `student-seminar` where `classroom_id` = %s and `person_id` = %s;", classroomId, personId);
		String preQuery1 = String.format(
				"delete from `student-section` where `classroom_id` = %s and `person_id` = %s;", classroomId, personId);
		String query = String.format("delete from `classroom_students` where `classroom_id` = %s and `person_id` = %s;",
				classroomId, personId);

		MyConnection myConnection = getMyConnection(preQuery0);
		executeUpdate(myConnection);

		myConnection = getMyConnection(preQuery1);
		executeUpdate(myConnection);

		myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		if (!sectionLeaderExists(email, classroomId))
			return false;
		String personId = getPersonId(email);

		String preQuery = String.format(
				"delete from `section-section_leader` where `classroom_id` = %s and `person_id` =%s;", classroomId,
				personId);
		String query = String.format(
				"delete from `classroom_section_leaders` where `classroom_id` = %s and `person_id` =%s;", classroomId,
				personId);

		MyConnection myConnection = getMyConnection(preQuery);
		executeUpdate(myConnection);

		myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
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
		if (!lecturerExists(email, classroomId))
			return false;
		String personId = getPersonId(email);

		String preQuery = String.format("delete from `lectures` where `classroom_id` = %s and `person_id` = %s;",
				classroomId, personId);

		String query = String.format(
				"delete from `classroom_lecturers` where `classroom_id` = %s and `person_id` = %s;", classroomId,
				personId);

		MyConnection myConnection = getMyConnection(preQuery);
		executeUpdate(myConnection);

		myConnection = getMyConnection(query);
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
	 * @return returns ArrayList of active seminars associated with given
	 *         classroom
	 */
	public ArrayList<ActiveSeminar> getActiveSeminars(String classroomId) {
		String query = "select * from seminars_timetable where `seminar_id` "
				+ "in (select seminar_id from seminars where `classroom_id` = " + classroomId + ");";
		ArrayList<ActiveSeminar> activeSeminars = new ArrayList<ActiveSeminar>();
		ResultSet rs = getResultSet(query);

		try {
			while (rs.next()) {
				activeSeminars
						.add(new ActiveSeminar(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return activeSeminars;
	}

	/**
	 * Adds material to the specified classroom
	 * 
	 * @param classroomId
	 *            id of the classroom in which this material is added
	 * @param materialName
	 *            name of the material which is added in classroom
	 * @return returns booelan (whether it added succesfully or not).
	 */
	public boolean addMaterial(String classroomId, String materialName) {

		if (materialName.equals(""))
			return false;

		String query = String.format("insert into `classroom_materials` values (%s,'%s');", classroomId, materialName);

		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
	}

	/**
	 * Returns all the materials associated with given classroom.
	 * 
	 * @param classroomId
	 *            id of the classroom of which materials is needed
	 * @return returns the ArrayList of materials associated with given
	 *         classroom.
	 */
	public ArrayList<Material> getMaterials(String classroomId) {
		String query = String.format("select * from `classroom_materials` where `classroom_id` = %s;", classroomId);

		MyConnection myConnection = getMyConnection(query);
		ResultSet rs = myConnection.executeQuery();
		ArrayList<Material> materials = new ArrayList<Material>();
		try {
			while (rs != null && rs.next()) {
				materials.add(new Material(rs.getString(1), rs.getString(2)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return materials;
	}

	/**
	 * 
	 * @param classroomId
	 * @param personId
	 * @param postText
	 * @return
	 */
	public boolean addPost(String classroomId, String personId, String postText) {
		String query = String.format(
				"insert into `classroom_posts` (`classroom_id`, `person_id`, `post_text`) values(%s, %s, '%s');",
				classroomId, personId, postText);

		MyConnection myConnection = getMyConnection(query);
		return executeUpdate(myConnection);
	}

	public ArrayList<Post> getPosts(String classroomId) {
		String query = String.format("select * from `classroom_posts` where `classroom_id` = %s;", classroomId);

		MyConnection myConnection = getMyConnection(query);
		ResultSet rs = myConnection.executeQuery();
		ArrayList<Post> posts = new ArrayList<Post>();
		try {
			while (rs != null && rs.next()) {
				posts.add(new Post(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return posts;
	}

	/**
	 * adds comment to the post.
	 * @param postID - ID of the post
	 * @param personID - ID of person
	 * @param comment_text - comment's text
	 */
	public void addPostComment(String postID, String personID, String comment_text) {
		String query = String.format(
				"insert into `post_comments` (`post_id`, `person_id`, `comment_text`)"
				+ "values(%s, %s, `%s`)", postID, personID, comment_text);
		
		MyConnection myConnection = getMyConnection(query);
		executeUpdate(myConnection);
	}
	
	/**
	 * returns list of comments of post with given ID
	 * @param postID - ID of post
	 * @return list of comments
	 */
	public ArrayList<Comment> getPostComments(String postID){
		ArrayList<Comment> comments = new ArrayList<Comment>();
		String query = String.format("select * from `post_comments` where `post_id` = %s;", postID);
		
		MyConnection myConnection = getMyConnection(query);
		ResultSet rs = myConnection.executeQuery();
		
		try {
			while (rs.next()){
				Comment comment = new Comment(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4));
				comments.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return comments;
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
