package defPackage;

import java.util.List;

import database.DBConnection;
import database.SectionDB;

public class Section {
	
	private String id;
	private int sectionN;
	private String classroomId;
	protected DBConnection sectionConnection;
	private SectionDB sectionDB;
	
	public Section(int sectionN, String classroomId){
		
		this.sectionN = sectionN;
		this.classroomId = classroomId;
		sectionConnection = new DBConnection();
		sectionDB = new SectionDB();
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
	 * Returns id of the classroom associated with this current section.
	 * 
	 * @return
	 */
	public String getClassroomId(){
		return this.classroomId;
	}
	
	
	/**
	 * Returns person object of the leader of current section, null if section leader is not set
	 * 
	 * @return
	 */
	public Person getSectionLeader(){
		//TODO
		return null;	
	}
	
	
	/**
	 * Returns list of person objects of students of current section
	 * 
	 * @return
	 */
	public List<Person> getSectionStudents(){
		//TODO
		return null;	
	}
	
	/**
	 * Returns true if section leader was removed from the section, false otherwise 
	 * 
	 * @return
	 */
	public boolean removeSectionLeader(){
		//TODO
		return false;
		
	}
	
	/**
	 * Returns true if section leader was added to the section, false otherwise
	 * @param leader
	 * @return
	 */
	public boolean setSectionLeader(Person leader){
		return sectionDB.addSectionLeaderToSection(sectionN, leader.getEmail(), classroomId);
		
	}

	/**
	 * Returns true if student was removed from the section false otherwise
	 * @param student
	 * @return
	 */
	public boolean removeStudentFromSection(Person student){
		//TODO
		return false;
	}
	
	/**
	 * Returns true if student was added to the section false otherwise
	 * @param student
	 * @return
	 */
	public boolean addStudentToSection(Person student){
		return sectionDB.addStudentToSection(sectionN, student.getEmail(), classroomId);		
	}
	
	
	/**
	 * Returns true if student is in current section, false otherwise
	 * @param student
	 * @return
	 */
	public boolean sectionContainsStudent(Person student){
		//TODO
		return false;	
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