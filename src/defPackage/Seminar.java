package defPackage;

import java.util.List;

import database.AllConnections;
import database.DBConnection;
import database.SeminarDB;
import database.SeminaristDB;
import database.StudentDB;

public class Seminar {
	
	private int seminarN;
	private String classroomId;
	protected DBConnection seminarConnection;
	private SeminaristDB seminaristDB;
	private SeminarDB seminarDB;
	private StudentDB studentDB;
	private int seminarSize;

	public Seminar(int seminarN, String classroomId, int seminarSize, AllConnections allConnections){

		this.seminarN = seminarN;
		this.classroomId = classroomId;
		seminarConnection = allConnections.db;
		seminaristDB = allConnections.seminaristDB;
		seminarDB = allConnections.seminarDB;
		studentDB = allConnections.studentDB;
		this.seminarSize = seminarSize;
	}
	
	public Seminar(int seminarN, String classroomId, AllConnections allConnections) {
		this(seminarN, classroomId, 0, allConnections);
	}

	/**
	 * Returns name of this current seminar.
	 * 
	 * @return
	 */
	public int getSeminarN(){
		return this.seminarN;
	}
	
	/**
	 * Returns id of the classroom associated with this current seminar.
	 * 
	 * @return
	 */
	public String getClassroomId(){
		return this.classroomId;
	}
	
	/**
	 * 
	 * @return id of the seminar
	 */
	public String getSeminarId() {
		return seminarDB.getSeminarId(seminarN, classroomId);
	}
	
	
	/**
	 * Returns person object of the seminarist of current seminar, null if seminarist is not set
	 * 
	 * @return
	 */
	public Person getSeminarist(){
		String seminarId = getSeminarId();
		return seminaristDB.getSeminarist(seminarId);
	}
	
	
	/**
	 * Returns list of person objects of students of current seminar
	 * 
	 * @return
	 */
	public List<Person> getSeminarStudents(){
		String seminarId = getSeminarId();
		return studentDB.getSeminarStudents(seminarId);
	}
	
	/**
	 * 
	 * @return current size of seminar
	 */

	public int getSeminarSize() {
		return seminarSize;
	}

	/**
	 * updates seminarSize by n
	 * @param n
	 */
	public void updateSeminarSize(int n) {
		seminarSize += n;
	}

	
	/**
	 * Returns true if seminarist was removed from the seminar, false otherwise 
	 * 
	 * @return
	 */
	public boolean removeSeminarist(){
		Person seminarist = getSeminarist();

		if(seminarist != null){
			String seminarId = getSeminarId();
			return seminaristDB.deleteSeminaristFromSeminar(seminarist.getEmail(), classroomId, seminarId);
		} else{
			return false;
		}

	}
	
	/**
	 * Returns true if seminarist was added to the section, false otherwise
	 * @param seminarist
	 * @return
	 */
	public boolean setSeminarist(String seminaristEmail){
		return seminarDB.addSeminaristToSeminar(seminarN, seminaristEmail, classroomId);		
	}

	/**
	 * Returns true if student was removed from the seminar false otherwise
	 * @param student
	 * @return
	 */
	public boolean removeStudentFromSeminar(String studentEmail){
		return seminarDB.deleteStudentFromSeminar(seminarN, studentEmail, classroomId);
	}
	
	/**
	 * Returns true if student was added to the seminar false otherwise
	 * @param student
	 * @return
	 */
	public boolean addStudentToSeminar(String studentEmail){
		return seminarDB.addStudentToSeminar(seminarN, studentEmail, classroomId);	
	}
	
	/**
	 * Returns true if student is in current seminar, false otherwise
	 * @param student
	 * @return
	 */
	public boolean seminarContainsStudent(String studentId){
		return seminarDB.studentExists(studentId, getSeminarId());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classroomId == null) ? 0 : classroomId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seminar other = (Seminar) obj;
		if (classroomId == null) {
			if (other.classroomId != null)
				return false;
		} else if (!classroomId.equals(other.classroomId))
			return false;
		if (seminarN != other.seminarN)
			return false;
		return true;
	}
}
