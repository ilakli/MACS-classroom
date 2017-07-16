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
	
	public PersonDB(AllConnections allConnections) {
		db = allConnections.db;
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
				currentPerson = new Person(rs.getString("person_name"), rs.getString("person_surname"), 
						rs.getString("person_email"), rs.getString("person_id"), rs.getString("image_url"));
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
				currentPerson = new Person(rs.getString("person_name"), rs.getString("person_surname"), 
						rs.getString("person_email"), rs.getString("person_id"), rs.getString("image_url"));
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
				persons.add(getPerson(rs.getString("person_id")));
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
	public boolean addPerson(String name, String surname, String email, String imgUrl) {
		String query = String.format("insert into `persons` (`person_name`, `person_surname`, `person_email`,`image_url`)"
				+ " values ('%s', '%s', '%s', '%s');", name, surname, email, imgUrl);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	/**
	 * adds new person with given email adress to the persons table
	 * 
	 * @param email
	 * @return true- if update executed successfully, false - otherwise
	 */
	public boolean addPersonByEmail(String email) {
		String query = String.format("insert into `persons` (`person_email`)"
				+ " values ('%s');", email);
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
				personId = rs.getString("person_id");
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally { 
			if (myConnection != null) {
				myConnection.closeConnection();
			}
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
	public boolean personExistsInClassroom(String email, String classroomId) {
		return lecturerDB.lecturerExists(email, classroomId) || seminaristDB.seminaristExists(email, classroomId)
				|| sectionLeaderDB.sectionLeaderExists(email, classroomId) || studentDB.studentExists(email, classroomId);
	}

	/**
	 * sets name and surname of the person with given email
	 * 
	 * @param email
	 * @param name
	 * @param surname
	 * @return true- if update executed successfully, false - otherwise
	 */
	public boolean setNameAndSurname(String email, String name, String surname) {
		String query = String.format("update `persons` set `person_name` = '%s', `person_surname` = '%s' "
				+ "where `person_email` = '%s'; ", name, surname, email);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
		
	}
	
	/**
	 * Sets image url of the sepcified person
	 * 
	 * @param email email of the give person
	 * @param imgUrl image url of a given person
	 * @return true- if update executed successfully, false - otherwise
	 */
	public boolean setImageUrl(String email, String imgUrl){
		String query = String.format("update `persons` set `image_url` = '%s'"
				+ "where `person_email` = '%s'; ", imgUrl, email);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}
	
	/**
	 * @param p - person
	 * @return - true if p is admin, false otherwise
	 */
	public boolean isAdmin(Person p) {
		boolean ret = false;
		String query = String.format("select * from `admins` where `person_id`=\"%s\"", p.getPersonID());
		MyConnection myConnection = db.getMyConnection(query);
		
		try {
			ResultSet rs = myConnection.executeQuery();
			if (rs.next()) ret = true;				
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally { 
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		
		return ret;
	}
	
	
	
}
