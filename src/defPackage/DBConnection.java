package defPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnection {

	// returns new connection
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
	
	// returns new Prepared Statement based on passed query string
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
	
	// returns single person based on personID
	public Person getPerson (String personID) {
		Person currentPerson = null;
		String query = "select * from persons where `person_id`=" + personID + ";";
		PreparedStatement stmnt = getPreparedStatement(query);
		ResultSet rs;
		try {
			rs = stmnt.executeQuery();
			while (rs.next()){
				currentPerson = new Person(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return currentPerson;
	}
	
	// returns persons ArrayList, it can be students, or seminarists, or section leaders
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
	
	// returns students ArrayList
	public ArrayList <Person> getStudents (String courseID) {
		String query = "select * from classroom_students where `classroom_id`=" + courseID + ";";
		ArrayList <Person> students = getPersons (query);
		return students;
	}
	
	// returns section leaders ArrayList
	public ArrayList <Person> getSectionLeaders (String courseID) {
		String query = "select * from classroom_section_leaders where `classroom_id`=" + courseID + ";";
		ArrayList <Person> sectionLeaders = getPersons (query);
		return sectionLeaders;
	}
	
	// returns seminarists ArrayList
	public ArrayList <Person> getSeminarists (String courseID) {
		String query = "select * from classroom_seminarists where `classroom_id`=" + courseID + ";";
		ArrayList <Person> seminarists = getPersons(query);
		return seminarists;
	}
	
	// returns lecturers ArrayList
	public ArrayList <Person> getLecturers (String courseID) {
		String query = "select * from classroom_lecturers where `classroom_id`=" + courseID + ";";
		ArrayList <Person> lecturers = getPersons(query);
		return lecturers;
	}
	
	// returns courses ArrayList
	public ArrayList <Course> getCourses() {
		ArrayList <Course> courses = new ArrayList <Course>();
		String coursesQuery = "select * from classrooms;";
		PreparedStatement stmnt = getPreparedStatement(coursesQuery);
		
		try {
			ResultSet coursesTable = stmnt.executeQuery();
			while (coursesTable.next()) {
				String courseID = coursesTable.getString(1);
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
}
