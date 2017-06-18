package defPackage;

import java.util.ArrayList;
import java.util.List;

import database.AllConnections;
import database.ClassroomDB;
import database.DBConnection;
import database.LecturerDB;
import database.MaterialDB;
import database.SectionDB;
import database.SeminarDB;
import database.StudentDB;

public class Classroom {

	public static final String ID_ATTRIBUTE_NAME = "classroomID";
	public static final String EMAIL_ATTRIBUTE_NAME = "email";

	private String classroomName;
	private String classroomID;
	protected DBConnection classroomConnection;
	private AllConnections db;
	private boolean autoSeminarDistribution;
	private boolean autoSectionDistribution;
	private int numberOfSeminars;
	private int numberOfSections;
	private int numberOfReschedulings;
	private int reschedulingLength;
	
	
	

	// Constructor;
	public Classroom(String classroomName, String classroomID) {
		this.classroomName = classroomName;
		this.classroomID = classroomID;
		this.autoSeminarDistribution = false;
		this.autoSectionDistribution = false;
		this.numberOfSections = 0;
		this.numberOfSeminars = 0;
		this.numberOfReschedulings = 0;
		this.reschedulingLength = 0;
		
		
		classroomConnection = new DBConnection();
		db = new AllConnections();
	}

	/**
	 * This method returns name of a classroom.
	 * 
	 * @return - name of a classroom;
	 */
	public String getClassroomName() {
		return this.classroomName;
	}

	/**
	 * This method returns ID of a classroom.
	 * 
	 * @return - ID of a classroom
	 */
	public String getClassroomID() {
		return this.classroomID;
	}

	/**
	 * This method returns section leaders' list from a classroom. This method
	 * returns only copy of real data, so it can't be changed from outside; We
	 * use DBConnection to return information from database.
	 * 
	 * @return - section leaders list
	 */
	public List<Person> getClassroomSectionLeaders() {
		return db.sectionLeaderDB.getSectionLeaders(this.classroomID);
	}

	/**
	 * This method returns seminarists' list from a classroom; This method
	 * returns only copy of real data, so it can't be changed from outside; We
	 * use DBConnection to return information from database.
	 * 
	 * @return - list of a seminarists
	 */
	public List<Person> getClassroomSeminarists() {
		return db.seminaristDB.getSeminarists(this.classroomID);
	}

	/**
	 * This method returns students' list from a classroom; This method returns
	 * only copy of real data, so it can't be changed from outside; We use
	 * DBConnection to return information from database.
	 * 
	 * @return - list of students
	 */
	public List<Person> getClassroomStudents() {
		return db.studentDB.getStudents(this.classroomID);
	}

	/**
	 * This method returns lecturers' list from a classroom; This method returns
	 * only copy of real data, so it can't be changed from outside; We use
	 * DBConnection to return information from database.
	 * 
	 * @return - list of lecturers
	 */
	public List<Person> getClassroomLecturers() {
		return (db.lecturerDB.getLecturers(this.classroomID));
	}

	/**
	 * This method returns seminars' list from a classroom; This method returns
	 * only copy of real data, so it can't be changed from outside; We use
	 * DBConnection to return information.
	 * 
	 * @return - list of seminars
	 */
	public List<Seminar> getClassroomSeminars() {
		return db.seminarDB.getSeminars(this.classroomID);
	}

	/**
	 * This method returns sections' list from a classroom; This method returns
	 * only copy of real data, so it can't be changed from outside; We use
	 * DBConnection to return information.
	 * 
	 * @return - list of sections
	 */
	public List<Section> getClassroomSections() {
		return db.sectionDB.getSections(this.classroomID);
	}

	/**
	 * This method tells us if the person is a lecturer in the classroom;
	 * 
	 * @param email
	 *            - person's email'
	 * @return - true if a person is a lecturer, false otherwise;
	 */
	public boolean classroomLecturerExists(String email) {
		return db.lecturerDB.lecturerExists(email, this.classroomID);
	}

	/**
	 * This method tells us if the person is a seminarist in the classroom
	 * 
	 * @param email
	 *            - person's email;
	 * @return - true if a person is a seminarist, false otherwise;
	 */
	public boolean classroomSeminaristExists(String email) {
		return db.seminaristDB.seminaristExists(email, this.classroomID);
	}

	/**
	 * This method tells us if the person is a section leader in the classroom;
	 * 
	 * @param email
	 *            - person's email
	 * @return - true if a person is a section leader, false otherwise;
	 */
	public boolean classroomSectionLeaderExists(String email) {
		return db.sectionLeaderDB.sectionLeaderExists(email, this.classroomID);
	}

	/**
	 * This method tells us if the person is a student in the classroom;
	 * 
	 * @param email
	 *            - person's email
	 * @return - true if a person is a student, false otherwise
	 */
	public boolean classroomStudentExists(String email) {
		return db.studentDB.studentExists(email, this.classroomID);
	}

	/**
	 * This method tells us if a person is a member of the classroom
	 * 
	 * @param email
	 *            - person's email;
	 * @return - true if a person is a member, false otherwise;
	 */
	public boolean classroomPersonExists(String email) {
		return db.personDB.personExists(email, this.classroomID);
	}

	/**
	 * This method adds person in the classroom as a lecturer;
	 * 
	 * @param email
	 *            - person's email;
	 * @return - true if a person has added successfully, false otherwise (if it
	 *         was a lecturer before or some error occurred) ======= This method
	 *         tells us if a section exists in the class;
	 * @param sectionN
	 *            - name of the section;
	 * @return - true if a section exists, false otherwise;
	 */
	public boolean classroomSectionExists(int sectionN) {
		return db.sectionDB.sectionExists(sectionN, this.classroomID);
	}

	/**
	 * This method tells us if a seminar exists in the class;
	 * 
	 * @param seminarN
	 *            - name of the section;
	 * @return - true is a seminar exists, false otherwise;
	 */
	public boolean classroomSeminarExists(int seminarN) {
		return db.seminarDB.seminarExists(seminarN, this.classroomID);
	}

	/**
	 * This method adds person in the classroom as a lecturer;
	 * 
	 * @param email
	 *            - person's email;
	 * @return - true if a person has added successfully, false otherwise (if it
	 *         was a lecturer before or some error occurred) >>>>>>>
	 *         e479ff59e7889fe12cf0fbdcc9d38be99cc3c3bd
	 */
	public boolean classroomAddLecturer(String email) {
		if (classroomLecturerExists(email)) {
			return false;
		} else {
			return db.lecturerDB.addLecturer(email, this.classroomID);
		}

	}

	/**
	 * This method adds person in the classroom as a student;
	 * 
	 * @param email
	 *            - person's email;
	 * @return - true if a person has added successfully, false otherwise (if it
	 *         was a student before or some error occurred)
	 */
	public boolean classroomAddStudent(String email) {
		if (classroomStudentExists(email)) {
			return false;
		} else {
			return db.studentDB.addStudent(email, this.classroomID);
		}
	}

	/**
	 * This method adds person in the classroom as a section leader;
	 * 
	 * @param email
	 *            - person's email;
	 * @return - true if a person has added successfully, false otherwise (if it
	 *         was a section leader before or some error occurred)
	 */
	public boolean classroomAddSectionLeader(String email) {
		if (classroomSectionLeaderExists(email)) {
			return false;
		} else {
			return db.sectionLeaderDB.addSectionLeader(email, this.classroomID);
		}
	}

	/**
	 * This method adds seminar in the classroom;
	 * @return - true if a seminar has added successfully, false otherwise (if
	 *         seminar with same name already existed in this classroom or some
	 *         error occurred)
	 */
	public boolean classroomAddSeminar() {
		return db.seminarDB.addSeminar(this.classroomID);
	}

	/**
	 * This method adds section in the classroom;
	 * @return - true if a section has added successfully, false otherwise (if
	 *         section with same name already existed in this classroom or some
	 *         error occurred)
	 */
	public boolean classroomAddSection() {
		return db.sectionDB.addSection(this.classroomID);
	}

	/**
	 * This method adds person in the classroom as a seminarist;
	 * 
	 * @param email
	 *            - person's email;
	 * @return - true if a person has added successfully, false otherwise (if it
	 *         was seminarist before or some error occurred)
	 */
	public boolean classroomAddSeminarist(String email) {
		if (classroomSeminaristExists(email)) {
			return false;
		} else {
			return db.seminaristDB.addSeminarist(email, this.classroomID);
		}
	}

	/**
	 * This method deletes a lecturer from the classroom;
	 * 
	 * @param email
	 *            - person's email;
	 * @return - true if a person has deleted successfully, false otherwise (if
	 *         it was not lecturer before or some error occurred)
	 */
	public boolean classroomDeleteLecturer(String email) {
		if (!classroomLecturerExists(email)) {
			return false;
		} else {
			return db.lecturerDB.deleteLecturer(email, this.classroomID);
		}
	}

	/**
	 * This method deletes a student from the classroom;
	 * 
	 * @param email
	 *            - person's email;
	 * @return - true if a person has deleted successfully, false otherwise (if
	 *         it was not a student before or some error occurred)
	 */
	public boolean classroomDeleteStudent(String email) {
		if (!classroomStudentExists(email)) {
			return false;
		} else {
			return db.studentDB.deleteStudent(email, this.classroomID);
		}
	}

	/**
	 * This method deletes a section leader from the classroom;
	 * 
	 * @param email
	 *            - person's email;
	 * @return - true if a person has deleted successfully, false otherwise (if
	 *         it was not a section leader before or some error occurred)
	 */
	public boolean classroomDeleteSectionLeader(String email) {
		if (!classroomSectionLeaderExists(email)) {
			return false;
		} else {
			return db.sectionLeaderDB.deleteSectionLeader(email, this.classroomID);
		}
	}

	/**
	 * This method deletes a seminarist from the classroom;
	 * 
	 * @param email
	 *            - person's email;
	 * @return - true if a person has deleted successfully, false otherwise (if
	 *         it was not seminarist before or some error occurred)
	 */
	public boolean classroomDeleteSeminarist(String email) {
		if (!classroomSeminaristExists(email)) {
			return false;
		} else {
			return db.seminaristDB.deleteSeminarist(email, this.classroomID);
		}
	}

	// not sure, header can be changed
	/**
	 * This method deletes the last seminar from the classroom;
	 * @return - true if a seminar has deleted successfully, false otherwise (if
	 *         seminar with same name didn't exist in this classroom or some
	 *         error occurred)
	 */
	public boolean classroomDeleteSeminar() {
		return db.seminarDB.deleteSeminar(this.classroomID);
	}

	// not sure, header can be changed
	/**
	 * This method deletes the last section in the classroom;
	 * @return - true if a section has deleted successfully, false otherwise (if
	 *         section with same name didn't exist in this classroom or some
	 *         error occurred)
	 */
	public boolean classroomDeleteSection() {
		return db.sectionDB.deleteSection(this.classroomID);
	}



	/**
	 * Adds specified material to this classroom.
	 * 
	 * @param materialName
	 *            name of the given material
	 * @return booelan (whether the material added to classroom successfully or
	 *         not).
	 */
	public boolean classroomAddMaterial(String materialName) {
		return db.materialDB.addMaterial(this.classroomID, materialName);
	}

	/**
	 * Gets all the materials associated with this classroom.
	 * 
	 * @return returns List of materials associated with this classroom.
	 */
	public ArrayList <Material> getMaterials() {
		return db.materialDB.getMaterials(this.classroomID);
	}
	
	/**
	 * fills seminars with free students,
	 * prioritizes seminars with small number of students
	 */
	public void fillSeminarsWithFreeStudents(){
		
		List<Person> students = db.studentDB.getStudentsWithoutSeminar(this.classroomID);
		List<Seminar> seminars = db.seminarDB.getSeminars(this.classroomID);
		
		if (seminars.isEmpty()) {
			System.out.println("TRIED TO FILL SEMINARS WITH FREE STUDENTS, BUT THERE ARE NO SEMINARS!!!!");
			return;
		}
		
		for (Person p : students){
			this.addStudentToSmallestSeminar(p);
		}
	
	}

	/**
	 * fills sections with free students,
	 * prioritizes sections with small number of students
	 */
	public void fillSectionsWithFreeStudents(){
		
		List<Person> students = db.studentDB.getStudentsWithoutSection(this.classroomID);
		List<Section> sections = db.sectionDB.getSections(this.classroomID);
		
		if (sections.isEmpty()){
			System.out.println("TRIED TO FILL SECTIONS WITH FREE STUDENTS, BUT THERE ARE NO SECTIONS!!!!");
			return;
		}
		
		for (Person p : students){
			this.addStudentToSmallestSection(p);
		}
	}
	
	/**
	 * changes class's section auto distribution to res
	 * @param res - variable for autoSectionDistribution to be set to
	 */
	public void setSectionDistribution(boolean newValue) {
		this.autoSectionDistribution = newValue;
	}
	
	/**
	 * @return - if sections are auto distributed in this class 
	 */
	public boolean areSectionsAudoDistributed() {
		return this.autoSectionDistribution;
	}
	
	/**
	 * changes class's seminar auto distribution to res
	 * @param res - variable for autoSeminarDistribution to be set to
	 */
	public void setSeminarDistribution(boolean newValue) {
		this.autoSeminarDistribution = newValue;
	}
	
	/**
	 * @return - if sections are auto distributed in this class 
	 */
	public boolean areSeminarsAudoDistributed() {
		return this.autoSeminarDistribution;
	}
	
	/**
	 * sets number of section in class to newValue
	 * @param newValue - new value
	 */
	public void setNumberOfSections(int newValue){
		if (newValue > this.numberOfSections){
			for (int i = 0; i < (newValue - this.numberOfSections); i++ ){
				classroomAddSection();
			}
		} else if (newValue < this.numberOfSections){
			for (int i = 0; i < (this.numberOfSections - newValue); i++ ){
				classroomDeleteSeminar();
			}
		}
		this.numberOfSections = newValue;
	}
	
	/**
	 * @return - number of sections in this class
	 */
	public int getNumberOfSections() {
		return numberOfSections;
	}
	
	/**
	 * sets number of seminars in class to newValue
	 * @param newValue - new value
	 */
	public void setNumberOfSeminars(int newValue){
		if (newValue > this.numberOfSeminars){
			for (int i = 0; i < (newValue - this.numberOfSeminars); i++ ){
				classroomAddSeminar();
			}
		} else if (newValue < this.numberOfSeminars){
			for (int i = 0; i < (this.numberOfSeminars - newValue); i++ ){
				classroomDeleteSeminar();
			}
		}
		this.numberOfSeminars= newValue;
	}
	
	/**
	 * @return - number of seminars in this class
	 */
	public int getNumberOfSeminars() {
		return numberOfSeminars;
	}
	
	/**
	 * @param newValue - new value of rescheduling allowed
	 */
	public void setNumberOfReschedulings(int newValue) {
		numberOfReschedulings = newValue;
	}
	
	/**
	 * @return - number of reschedulings allowed
	 */
	public int getNumberOfReschedulings() {
		return numberOfReschedulings;
	}
	
	/**
	 * @param newValue - new value of rescheduling length(in days)
	 */
	public void setReschedulingLength(int newValue) {
		reschedulingLength = newValue;
	}
	
	/**
	 * @return - length of rescheduling(in days)
	 */
	public int getReschedulingLength() {
		return reschedulingLength;
	}
	
	/**
	 * adds student to the smallest section of this class
	 * @param p - the student
	 */
	public void addStudentToSmallestSection(Person p) {
		Section section = db.sectionDB.getSmallestSection(this.classroomID);
		if (section == null) {
			System.out.println("THERE IS NO SECTION!!!");
			return;
		}
		section.addStudentToSection(p);
	}
	
	/**
	 * adds student to the smallest seminar of this class
	 * @param p - the student
	 */
	public void addStudentToSmallestSeminar(Person p) {
		Seminar seminar = db.seminarDB.getSmallestSeminar(this.classroomID);
		if (seminar == null) {
			System.out.println("THERE IS NO SEMINAR!!!");
			return;
		}
		seminar.addStudentToSeminar(p);
	}
	
	
}