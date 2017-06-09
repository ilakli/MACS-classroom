package defPackage;

import java.util.ArrayList;
import java.util.List;

public class Course {
	
	public static final String ID_ATTRIBUTE_NAME = "courseID";
	
	private ArrayList <Person> sectionLeaders;
	private ArrayList <Person> seminarists;
	private ArrayList <Person> students;
	private ArrayList <Person> lecturers;
	private String courseName;
	private int courseID;
	
	//Constructor;
	public Course (String courseName, int courseID, List <Person> sectionLeaders, List <Person> seminarists,
			List <Person> students, List <Person> lecturers) {
		this.courseName = courseName;
		this.courseID = courseID;
		this.sectionLeaders = new ArrayList<Person>(sectionLeaders);
		this.seminarists = new ArrayList <Person> (seminarists);
		this.students = new ArrayList <Person> (students);
		this.lecturers = new ArrayList <Person> (lecturers);
	}
	
	/**
	 * This method returns name of a course.
	 * @return - name of a course;
	 */
	public String getCourseName(){
		return this.courseName;
		
	}
	
	
	/**
	 * This method returns ID of a course.
	 * @return - ID of a course 
	 */
	public int getCourseID(){
		return this.courseID;
	}
	
	
	/**
	 * This method returns section leaders' list from a course.
	 * @return - section leaders list
	 */
	public List <Person> getCourseSectionLeaders(){
		return this.sectionLeaders;
	}
	
	
	/**
	 * this method returns seminarists' list from a course;
	 * @return - list of a seminarists
	 */
	public List <Person> getCourseSeminarists(){
		return this.seminarists;
	}
	
	/**
	 * This method returns students' list from a course;
	 * @return - list of a students
	 */
	public List <Person> getCourseStudents(){
		return this.students;		
	}
	
	/**
	 * This method returns lecturers' list from a course;
	 * @return - list of a lecturers
	 */
	public List <Person> getCourseLecturers(){
		return this.lecturers;
	}
	
}
