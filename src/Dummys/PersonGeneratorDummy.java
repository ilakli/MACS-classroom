package Dummys;

import database.PersonDB;
import defPackage.Person;

public class PersonGeneratorDummy {
	
	public static Person createPersonByEmail(String email){
		/*
		PersonDB personDB = new PersonDB();
		Person person = personDB.getPersonByEmail(email);
		
		if (person == null) {
			int len = email.length();
			String name = email.substring(0, len/2);
			String surname = email.substring(len/2);
			
			if (email.indexOf('@') != -1){
				int en = email.indexOf('@');
				name = email.substring(0,en);
				surname = email.substring(en);
			}
			
			personDB.addPerson(name, surname, email);
			person = personDB.getPersonByEmail(email);
		}
		
		return person;
		*/
		return null;
	}
	
}
