package defPackage;

public class Section {
	
	private String id;
	private String name;
	private String classroomId;
	
	public Section(String id, String name, String classroomId){
		
		this.id = id;
		this.name = name;
		this.classroomId = classroomId;
	}
	
	/**
	 * Returns id of this current section.
	 * 
	 * @return
	 */
	public String getSectionId(){
		return this.id;
	}
	
	
	/**
	 * Returns name of this current section.
	 * 
	 * @return
	 */
	public String getSectionName(){
		return this.name;
	}
	
	
	/**
	 * Returns id of the classroom associated with this current section.
	 * 
	 * @return
	 */
	public String getClassroomId(){
		return this.classroomId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classroomId == null) ? 0 : classroomId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (classroomId == null && name == null && 
				other.classroomId == null && other.name == null) {
			return true;
		} 
		
		if (classroomId == null || name == null || 
				other.classroomId == null || other.name == null) {
			return false;
		} 
		
		if (classroomId.equals(other.classroomId) && name.equals(other.name) ) {
			return true;
		} 
		
		return false;
	}
}
