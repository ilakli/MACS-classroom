package defPackage;

import java.util.ArrayList;

public class Course {
	private ArrayList <Person> sectionLeaders;
	private ArrayList <Person> seminarists;
	private ArrayList <Person> students;
	private ArrayList <Person> lecturers;
	private String courseName;
	private String courseID;
	
	public Course (String courseName, String courseID, ArrayList <Person> sectionLeaders, ArrayList <Person> seminarists,
			ArrayList <Person> students, ArrayList <Person> lecturers) {
		this.courseName = courseName;
		this.courseID = courseID;
		this.sectionLeaders = new ArrayList<Person>(sectionLeaders);
		this.seminarists = new ArrayList <Person> (seminarists);
		this.students = new ArrayList <Person> (students);
		this.lecturers = new ArrayList <Person> (lecturers);
	}
	
}
