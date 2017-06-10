package defPackage;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
	
	public static final String ID_ATTRIBUTE_NAME = "classroomID";
	
	
	private String classroomName;
	private String classroomID;
	
	//Constructor;
	public Classroom (String classroomName, String classroomID) {
		this.classroomName = classroomName;
		this.classroomID = classroomID;
		
	}
	
	/**
	 * This method returns name of a classroom.
	 * @return - name of a classroom;
	 */
	public String getClassroomName(){
		return this.classroomName;
	}
	
	
	/**
	 * This method returns ID of a classroom.
	 * @return - ID of a classroom 
	 */
	public String getClassroomID(){
		return this.classroomID;
	}
	
	
	/**
	 * This method returns section leaders' list from a classroom.
	 * This method returns only copy of real data, so it can't be changed from outside;
	 * We use DBConnection to return information from database. 
	 * @return - section leaders list
	 */
	public List <Person> getClassroomSectionLeaders(){
		DBConnection db = new DBConnection();
		return db.getSectionLeaders(this.classroomID);
	}
	
	
	/**
	 * This method returns seminarists' list from a classroom;
	 * This method returns only copy of real data, so it can't be changed from outside;
	 * We use DBConnection to return information from database. 
	 * @return - list of a seminarists
	 */
	public List <Person> getClassroomSeminarists(){
		DBConnection db = new DBConnection();
		return db.getSeminarists(this.classroomID);
	}
	
	/**
	 * This method returns students' list from a classroom;
	 * This method returns only copy of real data, so it can't be changed from outside;
	 * We use DBConnection to return information from database. 
	 * @return - list of a students
	 */
	public List <Person> getClassroomStudents(){
		DBConnection db = new DBConnection();
		return db.getStudents(this.classroomID);
	}
	
	/**
	 * This method returns lecturers' list from a classroom;
	 * This method returns only copy of real data, so it can't be changed from outside;
	 * We use DBConnection to return information from database. 
	 * @return - list of a lecturers
	 */
	public List <Person> getClassroomLecturers(){
		DBConnection db = new DBConnection();
		return db.getLecturers(this.classroomID);
	}
	
	
	
	
}
