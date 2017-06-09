package defPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnection {

	/**
	 * 
	 * @return new Connection
	 */
	private Connection getConnection() {
		Connection currentConnection = null;
		try {
			Class.forName(DBinfo.JDBC_DRIVER);
			currentConnection = DriverManager.getConnection(DBinfo.MYSQL_DATABASE_SERVER, DBinfo.MYSQL_USERNAME, 
					DBinfo.MYSQL_PASSWORD);
		} catch (Exception e) {
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
	 * @param courseID - ID of students course
	 * @return students of current classroom
	 */
	public ArrayList <Person> getStudents (int courseID) {
		String query = "select * from classroom_students where `classroom_id`=" + courseID + ";";
		ArrayList <Person> students = getPersons (query);
		return students;
	}
	
	/**
	 * 
	 * @param courseID - ID of Section Leaders course
	 * @return section leaders of current course
	 */
	public ArrayList <Person> getSectionLeaders (int courseID) {
		String query = "select * from classroom_section_leaders where `classroom_id`=" + courseID + ";";
		ArrayList <Person> sectionLeaders = getPersons (query);
		return sectionLeaders;
	}
	
	/**
	 * 
	 * @param courseID - ID of seminarists course
	 * @return seminarists of current course
	 */
	public ArrayList <Person> getSeminarists (int courseID) {
		String query = "select * from classroom_seminarists where `classroom_id`=" + courseID + ";";
		ArrayList <Person> seminarists = getPersons(query);
		return seminarists;
	}

	/**
	 * 
	 * @param courseID - ID of lecturers course
	 * @return lecturers of current course
	 */
	public ArrayList <Person> getLecturers (int courseID) {
		String query = "select * from classroom_lecturers where `classroom_id`=" + courseID + ";";
		ArrayList <Person> lecturers = getPersons(query);
		return lecturers;
	}

	/**
	 * 
	 * @return returns ArrayList of current courses
	 */
	public ArrayList <Course> getCourses() {
		ArrayList <Course> courses = new ArrayList <Course>();
		String coursesQuery = "select * from classrooms;";
		PreparedStatement stmnt = getPreparedStatement(coursesQuery);
		
		try {
			ResultSet coursesTable = stmnt.executeQuery();
			while (coursesTable.next()) {
				int courseID = coursesTable.getInt(1);
				String courseName = coursesTable.getString(2);
				
				ArrayList <Person> students = getStudents(courseID);
				ArrayList <Person> sectionLeaders = getSectionLeaders(courseID);
				ArrayList <Person> seminarists = getSeminarists(courseID);
				ArrayList <Person> lecturers = getLecturers(courseID);
				
				courses.add(new Course(courseName, courseID, sectionLeaders, seminarists, students, lecturers));
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return courses;
	}
	
	public Course getCourse(String courseId){
		return null;
	}
	
}
