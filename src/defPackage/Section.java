package defPackage;

import java.util.Comparator;
import java.util.List;

import database.AllConnections;
import database.DBConnection;
import database.SectionDB;
import database.SectionLeaderDB;
import database.StudentDB;

public class Section {
	
	private String id;
	private int sectionN;
	private String classroomId;
	protected DBConnection sectionConnection;
	private SectionDB sectionDB;
	private SectionLeaderDB sectionLeaderDB;
	private StudentDB studentDB;
	private int sectionSize;
	
	public Section(int sectionN, String classroomId, int sectionSize,AllConnections allConnections){
		
		this.sectionN = sectionN;
		this.classroomId = classroomId;
		sectionConnection = allConnections.db;
		sectionDB = allConnections.sectionDB;
		sectionLeaderDB = allConnections.sectionLeaderDB;
		studentDB = allConnections.studentDB;
		this.sectionSize = sectionSize;
	}
	
	public Section (int sectionN, String classroomId, AllConnections allConnections) {
		this(sectionN, classroomId, 0, allConnections);
	}
	
	/**
	 * Returns name of this current section.
	 * 
	 * @return
	 */
	public int getSectionN(){
		return this.sectionN;
	}
	
	/**
	 * 
	 * @return amount of students in given section
	 */
	
	public int getSectionSize() {
		return this.sectionSize;
	}
	
	/**
	 * Returns id of the classroom associated with this current section.
	 * 
	 * @return
	 */
	public String getClassroomId(){
		return this.classroomId;
	}
	/**
	 * 
	 * @return sectionId of current section
	 */
	public String getSectionId() {
		return sectionDB.getSectionId(sectionN, classroomId);
	}
	
	/**
	 * Returns person object of the leader of current section, null if section leader is not set
	 * 
	 * @return
	 */
	public Person getSectionLeader(){
		String sectionId = getSectionId();
		return sectionLeaderDB.getSectionLeader(sectionId);
	}
	
	
	/**
	 * Returns list of person objects of students of current section
	 * 
	 * @return
	 */
	public List<Person> getSectionStudents(){
		return studentDB.getSectionStudents(getSectionId());
	}

	/**
	 * updates size of section by n
	 * @param n
	 */
	public void updateSectionSize(int n) {
		sectionSize += n;
	}
	
	/**
	 * Returns true if section leader was removed from the section, false otherwise 
	 * 
	 * @return
	 */
	public boolean removeSectionLeader(){
		String sectionId = getSectionId();
		if(getSectionLeader() == null){
			return false;
		}			
		return sectionLeaderDB.deleteSectionLeaderFromSection(getSectionLeader().getEmail(), 
				classroomId, sectionId);
	}
	
	/**
	 * Returns true if section leader was added to the section, false otherwise
	 * @param leader
	 * @return
	 */
	public boolean setSectionLeader(String leaderEmail){
		return sectionDB.addSectionLeaderToSection(sectionN, leaderEmail, classroomId);
		
	}

	/**
	 * Returns true if student was removed from the section false otherwise
	 * @param student
	 * @return
	 */
	public boolean removeStudentFromSection(String studentEmail){
		return sectionDB.removeStudent(classroomId, studentEmail);
	}
	
	/**
	 * Returns true if student was added to the section false otherwise
	 * @param student
	 * @return
	 */
	public boolean addStudentToSection(String studentEmail){
		return sectionDB.addStudentToSection(sectionN, studentEmail, classroomId);		
	}
	
	
	/**
	 * Returns true if student is in current section, false otherwise
	 * @param student
	 * @return
	 */
	public boolean sectionContainsStudent(String studentId){
		return sectionDB.containsStudent(classroomId, studentId);
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
		Section other = (Section) obj;
		if (classroomId == null) {
			if (other.classroomId != null)
				return false;
		} else if (!classroomId.equals(other.classroomId))
			return false;
		 if (sectionN !=other.sectionN)
			return false;
		return true;
	}
}