package defPackage;

public class Material {
	private String materialName;
	private String classroomId;
	public Material(String classroomId, String materialName){
		this.materialName = materialName;
		this.classroomId = classroomId;
	}
	
	public String getMaterialName(){
		return this.materialName;
	}
	
	public String getClassroomId(){
		return this.classroomId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classroomId == null) ? 0 : classroomId.hashCode());
		result = prime * result + ((materialName == null) ? 0 : materialName.hashCode());
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
		Material other = (Material) obj;
		if (classroomId == null) {
			if (other.classroomId != null)
				return false;
		} else if (!classroomId.equals(other.classroomId))
			return false;
		if (materialName == null) {
			if (other.materialName != null)
				return false;
		} else if (!materialName.equals(other.materialName))
			return false;
		return true;
	}
	
}
