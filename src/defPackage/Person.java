package defPackage;

import java.util.ArrayList;

import database.AllConnections;
import database.ClassroomDB;

public class Person {
	
	public static final String ID_ATTRIBUTE_NAME = "personID";
	public static final String DEFAULT_IMG_URL = "https://s-media-cache-ak0.pinimg.com/736x/6b/70/2c/6b702c0de73e2ce734e98333566e8679--happy-faces-smiley-faces.jpg";
	private String name;
	private String surname;
	private String Email;
	private String personID;
	String imgUrl;
	
	private AllConnections db;
	
	public Person (String name, String surname, String Email, String personID) {
		this.name = name;
		this.surname = surname;
		this.Email = Email;
		this.personID = personID;
		this.imgUrl = "http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042";
		db = new AllConnections();
	}
	public Person (String name, String surname, String Email, String personID, String imgUrl) {
		this.name = name;
		this.surname = surname;
		this.Email = Email;
		this.personID = personID;
		this.imgUrl = imgUrl;
		if (this.imgUrl == null) this.imgUrl = DEFAULT_IMG_URL;
		db = new AllConnections();
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
	public String getPersonImgUrl(){
		return imgUrl;
	}
	public boolean setNameAndSurname(String firstName, String lastName) {
		return db.personDB.setNameAndSurname(Email, firstName, lastName);
		
	}
	public boolean setImageUrl(String imgUrl){
		return db.personDB.setImageUrl(Email, imgUrl);
	}
	public ArrayList <Classroom> getClassrooms() {
		return db.classroomDB.getClassroomsByPerson(Email);
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
