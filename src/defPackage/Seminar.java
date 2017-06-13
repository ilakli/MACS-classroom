package defPackage;

public class Seminar {
	
	private String id;
	private String name;
	private String classroomId;
	
	public Seminar(String id, String name, String classroomId){
		
		this.id = id;
		this.name = name;
		this.classroomId = classroomId;
	}
	
	/**
	 * Returns id associated with this seminar.
	 * 
	 * @return
	 */
	public String getSeminarId(){
		return this.id;
	}
	
	/**
	 * Returns name of this current seminar.
	 * 
	 * @return
	 */
	public String getSeminarName(){
		return this.name;
	}
	
	/**
	 * Returns id of the classroom associated with this current seminar.
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
		Seminar other = (Seminar) obj;
		if (classroomId == null) {
			if (other.classroomId != null)
				return false;
		} else if (!classroomId.equals(other.classroomId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
}
