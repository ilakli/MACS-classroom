package defPackage;

import java.util.ArrayList;

import database.ClassroomDB;

public class Person {
	
	public static final String ID_ATTRIBUTE_NAME = "personID";

	private String name;
	private String surname;
	private String Email;
	private String personID;
	private ClassroomDB classroomDB;
	
	public Person (String name, String surname, String Email, String personID) {
		this.name = name;
		this.surname = surname;
		this.Email = Email;
		this.personID = personID;
		classroomDB = new ClassroomDB();
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getEmail() {
		return Email;
	}
	
	public String getPersonID() {
		return personID;
	}
	
	public ArrayList <Classroom> getClassrooms() {
		return classroomDB.getClassroomsByPerson(Email);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		if (!(obj instanceof Person)) return false;
		
		Person p = (Person) obj;
		
		return p.Email.equals(this.Email);
	}
}
