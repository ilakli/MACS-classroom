package defPackage;

public class Category {
	private String categoryName;
	private String classroomId;
	private String categoryId;
	
	public Category(String classroomId, String categoryName, String categoryId){
		this.categoryName = categoryName;
		this.classroomId = classroomId;
		this.categoryId = categoryId;
	}
	
	/**
	 * 
	 * @return - name of the category
	 */
	public String getCategoryName(){
		return this.categoryName;
	}
	
	/**
	 * 
	 * @return - id of the classroom
	 */
	public String getClassroomId(){
		return this.classroomId;
	}
	
	/**
	 * 
	 * @return ID of the category
	 */
	public String getCategoryId(){
		return this.categoryId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
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
		Category other = (Category) obj;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		if (classroomId == null) {
			if (other.classroomId != null)
				return false;
		} else if (!classroomId.equals(other.classroomId))
			return false;
		return true;
	}
	
	
	
}
