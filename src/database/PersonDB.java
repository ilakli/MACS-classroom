package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Person;

public class PersonDB {
	
	private DBConnection db;
	private LecturerDB lecturerDB;
	private SeminaristDB seminaristDB;
	private SectionLeaderDB sectionLeaderDB;
	private StudentDB studentDB;
	
	public PersonDB() {
		db = new DBConnection();
		
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
			myConnection = db.getMyConnection(query);
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
			myConnection = db.getMyConnection(query);
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
	ArrayList<Person> getPersons(String query) {

		ArrayList<Person> persons = new ArrayList<Person>();
		MyConnection myConnection = null;
		try {
			myConnection = db.getMyConnection(query);
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
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}


	/**
	 * 
	 * @param email
	 * @return personId of person with given Email
	 */
	public String getPersonId(String email) {
		String query = String.format("select `person_id` from `persons` where `person_email`='%s';", email);
		MyConnection myConnection = db.getMyConnection(query);
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
	 * checks if person with given email exists in given classroom
	 * 
	 * @param email
	 * @param classroomId
	 * @return
	 */
	public boolean personExists(String email, String classroomId) {
		return lecturerDB.lecturerExists(email, classroomId) || seminaristDB.seminaristExists(email, classroomId)
				|| sectionLeaderDB.sectionLeaderExists(email, classroomId) || studentDB.studentExists(email, classroomId);
	}
}
