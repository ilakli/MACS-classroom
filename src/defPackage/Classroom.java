package defPackage;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
	
	public static final String ID_ATTRIBUTE_NAME = "classroomID";
	
	private ArrayList <Person> sectionLeaders;
	private ArrayList <Person> seminarists;
	private ArrayList <Person> students;
	private ArrayList <Person> lecturers;
	private String classroomName;
	private String classroomID;
	
	//Constructor;
	public Classroom (String classroomName, String classroomID, List <Person> sectionLeaders, List <Person> seminarists,
			List <Person> students, List <Person> lecturers) {
		this.classroomName = classroomName;
		this.classroomID = classroomID;
		this.sectionLeaders = new ArrayList<Person>(sectionLeaders);
		this.seminarists = new ArrayList <Person> (seminarists);
		this.students = new ArrayList <Person> (students);
		this.lecturers = new ArrayList <Person> (lecturers);
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
	 * @return - section leaders list
	 */
	public List <Person> getClassroomSectionLeaders(){
		return this.sectionLeaders;
	}
	
	
	/**
	 * this method returns seminarists' list from a classroom;
	 * @return - list of a seminarists
	 */
	public List <Person> getClassroomSeminarists(){
		return this.seminarists;
	}
	
	/**
	 * This method returns students' list from a classroom;
	 * @return - list of a students
	 */
	public List <Person> getClassroomStudents(){
		return this.students;		
	}
	
	/**
	 * This method returns lecturers' list from a classroom;
	 * @return - list of a lecturers
	 */
	public List <Person> getClassroomLecturers(){
		return this.lecturers;
	}
	
}
