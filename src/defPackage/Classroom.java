package defPackage;

import java.util.ArrayList;
import java.util.List;

public class Classroom {

	public static final String ID_ATTRIBUTE_NAME = "classroomID";
	public static final String EMAIL_ATTRIBUTE_NAME = "email";

	private String classroomName;
	private String classroomID;
	protected DBConnection classroomConnection;

	// Constructor;
	public Classroom(String classroomName, String classroomID) {
		this.classroomName = classroomName;
		this.classroomID = classroomID;
		classroomConnection = new DBConnection();
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
		return this.classroomConnection.getSectionLeaders(this.classroomID);
	}

	/**
	 * This method returns seminarists' list from a classroom; This method
	 * returns only copy of real data, so it can't be changed from outside; We
	 * use DBConnection to return information from database.
	 * 
	 * @return - list of a seminarists
	 */
	public List<Person> getClassroomSeminarists() {
		return this.classroomConnection.getSeminarists(this.classroomID);
	}

	/**
	 * This method returns students' list from a classroom; This method returns
	 * only copy of real data, so it can't be changed from outside; We use
	 * DBConnection to return information from database.
	 * 
	 * @return - list of students
	 */
	public List<Person> getClassroomStudents() {
		return this.classroomConnection.getStudents(this.classroomID);
	}

	/**
	 * This method returns lecturers' list from a classroom; This method returns
	 * only copy of real data, so it can't be changed from outside; We use
	 * DBConnection to return information from database.
	 * 
	 * @return - list of lecturers
	 */
	public List<Person> getClassroomLecturers() {
		return (this.classroomConnection.getLecturers(this.classroomID));
	}

	/**
	 * This method returns seminars' list from a classroom; This method returns
	 * only copy of real data, so it can't be changed from outside; We use
	 * DBConnection to return information.
	 * 
	 * @return - list of seminars
	 */
	public List<Seminar> getClassroomSeminars() {
		return this.classroomConnection.getSeminars(this.classroomID);
	}

	/**
	 * This method returns sections' list from a classroom; This method returns
	 * only copy of real data, so it can't be changed from outside; We use
	 * DBConnection to return information.
	 * 
	 * @return - list of sections
	 */
	public List<Section> getClassroomSections() {
		return this.classroomConnection.getSections(this.classroomID);
	}


	/**
	 * This method tells us if the person is a lecturer in the classroom;
	 * 
	 * @param email
	 *            - person's email'
	 * @return - true if a person is a lecturer, false otherwise;
	 */
	public boolean classroomLecturerExists(String email) {
		return this.classroomConnection.lecturerExists(email, this.classroomID);
	}

	/**
	 * This method tells us if the person is a seminarist in the classroom
	 * 
	 * @param email
	 *            - person's email;
	 * @return - true if a person is a seminarist, false otherwise;
	 */
	public boolean classroomSeminaristExists(String email) {
		return this.classroomConnection.seminaristExists(email, this.classroomID);
	}

	/**
	 * This method tells us if the person is a section leader in the classroom;
	 * 
	 * @param email
	 *            - person's email
	 * @return - true if a person is a section leader, false otherwise;
	 */
	public boolean classroomSectionLeaderExists(String email) {
		return this.classroomConnection.sectionLeaderExists(email, this.classroomID);
	}

	/**
	 * This method tells us if the person is a student in the classroom;
	 * 
	 * @param email
	 *            - person's email
	 * @return - true if a person is a student, false otherwise
	 */
	public boolean classroomStudentExists(String email) {
		return this.classroomConnection.studentExists(email, this.classroomID);
	}

	/**
	 * This method tells us if a person is a member of the classroom
	 * 
	 * @param email
	 *            - person's email;
	 * @return - true if a person is a member, false otherwise;
	 */
	public boolean classroomPersonExists(String email) {
		return this.classroomConnection.personExists(email, this.classroomID);
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
		return this.classroomConnection.sectionExists(sectionN, this.classroomID);
	}

	/**
	 * This method tells us if a seminar exists in the class;
	 * 
	 * @param seminarN
	 *            - name of the section;
	 * @return - true is a seminar exists, false otherwise;
	 */
	public boolean classroomSeminarExists(int seminarN) {
		return this.classroomConnection.seminarExists(seminarN, this.classroomID);
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
			return this.classroomConnection.addLecturer(email, this.classroomID);
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
			return this.classroomConnection.addStudent(email, this.classroomID);
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
			return this.classroomConnection.addSectionLeader(email, this.classroomID);
		}
	}

	/**
	 * This method adds seminar in the classroom;
	 * @return - true if a seminar has added successfully, false otherwise (if
	 *         seminar with same name already existed in this classroom or some
	 *         error occurred)
	 */
	public boolean classroomAddSeminar() {
		return this.classroomConnection.addSeminar(this.classroomID);
	}

	/**
	 * This method adds section in the classroom;
	 * @return - true if a section has added successfully, false otherwise (if
	 *         section with same name already existed in this classroom or some
	 *         error occurred)
	 */
	public boolean classroomAddSection() {
		return this.classroomConnection.addSection(this.classroomID);
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
			return this.classroomConnection.addSeminarist(email, this.classroomID);
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
			return this.classroomConnection.deleteLecturer(email, this.classroomID);
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
			return this.classroomConnection.deleteStudent(email, this.classroomID);
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
			return this.classroomConnection.deleteSectionLeader(email, this.classroomID);
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
			return this.classroomConnection.deleteSeminarist(email, this.classroomID);
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
		return this.classroomConnection.deleteSeminar(this.classroomID);
	}

	// not sure, header can be changed
	/**
	 * This method deletes the last section in the classroom;
	 * @return - true if a section has deleted successfully, false otherwise (if
	 *         section with same name didn't exist in this classroom or some
	 *         error occurred)
	 */
	public boolean classroomDeleteSection() {
		return this.classroomConnection.deleteSection(this.classroomID);
	}



	/**
	 * This method sets section's student;
	 * 
	 * @param sectionN
	 *            - seminar's name;
	 * @param sectionLeaderEmail
	 *            - student who should become section member;
	 * @return - true if added, false otherwise;
	 */
	public boolean classroomAddStudentToSection(int sectionN, String studentEmail) {
		return this.classroomConnection.addStudentToSection(sectionN, studentEmail, this.classroomID);
	}

	/**
	 * This method sets seminar's student;
	 * 
	 * @param sectionN
	 *            - seminar's name;
	 * @param sectionLeaderEmail
	 *            - student who should become seminar member;
	 * @return - true if added, false otherwise;
	 */
	public boolean classroomAddStudentToSeminar(int seminarN, String studentEmail) {
		return this.classroomConnection.addStudentToSeminar(seminarN, studentEmail, this.classroomID);
	}

	/**
	 * This method sets seminar's seminarist;
	 * 
	 * @param sectionN
	 *            - seminar's name;
	 * @param sectionLeaderEmail
	 *            - person who should become seminarist;
	 * @return - true if added leader, false otherwise;
	 */
	public boolean classroomAddSeminaristToSeminar(int seminarN, String seminaristEmail) {
		return this.classroomConnection.addSeminaristToSeminar(seminarN, seminaristEmail, this.classroomID);
	}

	/**
	 * This method sets section's leader
	 * 
	 * @param sectionN
	 *            - section's name;
	 * @param sectionLeaderEmail
	 *            - person who should become leader;
	 * @return - true if added leader, false otherwise;
	 */
	public boolean classroomAddSectionLeaderToSection(int sectionN, String sectionLeaderEmail) {
		return this.classroomConnection.addSectionLeaderToSection(sectionN, sectionLeaderEmail, this.classroomID);

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
		return this.classroomConnection.addMaterial(this.classroomID, materialName);
	}

	/**
	 * Gets all the materials associated with this classroom.
	 * 
	 * @return returns List of materials associated with this classroom.
	 */
	public ArrayList <Material> getMaterials() {
		return this.classroomConnection.getMaterials(this.classroomID);
	}
	
	/**
	 * fills seminars with free students,
	 * prioritizes seminars with small number of students
	 */
	public void fillSeminarsWithFreeStudents(){
		
		List<Person> students = classroomConnection.getStudentsWithoutSeminar(this.classroomID);
		List<Seminar> seminars = classroomConnection.getSeminars(this.classroomID);
		
		if (seminars.isEmpty()) {
			System.out.println("TRIED TO FILL SEMINARS WITH FREE STUDENTS, BUT THERE ARE NO SEMINARS!!!!");
			return;
		}
		
		for (Person p : students){
			Seminar seminar = classroomConnection.getSmallestSeminar(this.classroomID);
			seminar.addStudentToSeminar(p);
		}
	
	}
	
	public void fillSectionsWithFreeStudents(){
		
		List<Person> students = classroomConnection.getStudentsWithoutSection(this.classroomID);
		List<Section> sections = classroomConnection.getSections(this.classroomID);
		
		if (sections.isEmpty()){
			System.out.println("TRIED TO FILL SECTIONS WITH FREE STUDENTS, BUT THERE ARE NO SECTION!!!!");
			return;
		}
		
		for (Person p : students){
			Section section = classroomConnection.getSmallestSection(this.classroomID);
		}
	}
	
}