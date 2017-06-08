package defPackage;

import java.util.ArrayList;

public class Course {
	private ArrayList <Person> sectionLeaders;
	private ArrayList <Person> seminarists;
	private ArrayList <Person> students;
	private ArrayList <Person> lecturers;
	private String courseName;
	private String courseID;
	
	public Course (String courseName, String courseID) {
		this.courseName = courseName;
		this.courseID = courseID;
	}
	
}
