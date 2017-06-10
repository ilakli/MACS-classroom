package defPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 
 * In order to make this class work as Java Application, one should comment DBConnection constructor
 *
 */

@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class TestDBConnection {
	
	private MockDBConnection db;
	
	@Before
	public void initialize() {
		db = new MockDBConnection();
	}
	
	@Test
	public void test1AddingPerson() {
		assertTrue(db.addPerson("irakli", "popkhadze", "ipopk15@freeuni.edu.ge"));
		assertTrue(db.addPerson("giorgi", "khosroshvili", "gkhos15@freeuni.edu.ge"));
		assertTrue(db.addPerson("shota", "gvinepadze", "s.gvinepadze@freeuni.edu.ge"));
		assertTrue(db.addPerson("nika", "begiashvili", "n.begiashvili@freeuni.edu.ge"));
		assertTrue(db.addPerson("giorgi", "cercvadze", "gitser15@freeuni.edu.ge"));
		assertTrue(db.addPerson("aleko", "cxovrebovi", "acxcx15@freeuni.edu.ge"));
		assertTrue(db.addPerson("mari", "berishvili", "mberi15@freeuni.edu.ge"));

		assertFalse(db.addPerson("shota", "gvinepadze", "s.gvinepadze@freeuni.edu.ge"));
		assertFalse(db.addPerson("vigaca", "vigaca", "ipopk15@freeuni.edu.ge"));
	}

	@Test
	public void test2AddingClassroom() {
		assertEquals(db.addClassroom("OOP"), "1");
		assertEquals(db.addClassroom("Paradigms"), "2");
		assertEquals(db.addClassroom("Methodologies"), "3");
		assertEquals(db.addClassroom("iraklis'"), DBConnection.DATABASE_ERROR);
	}
	
	@Test
	public void test3AddingToClassrom() {
		assertTrue(db.addLecturer("s.gvinepadze@freeuni.edu.ge", "1"));
		assertTrue(db.addLecturer("s.gvinepadze@freeuni.edu.ge", "2"));
		assertTrue(db.addLecturer("s.gvinepadze@freeuni.edu.ge", "3"));
		
		assertTrue(db.addSeminarist("n.begiashvili@freeuni.edu.ge", "1"));
		assertTrue(db.addSeminarist("n.begiashvili@freeuni.edu.ge", "2"));
		
		assertTrue(db.addSectionLeader("ipopk15@freeuni.edu.ge", "3"));
		assertTrue(db.addSectionLeader("gkhos15@freeuni.edu.ge", "3"));
		assertTrue(db.addSectionLeader("acxcx15@freeuni.edu.ge", "3"));
		
		assertFalse(db.addSectionLeader("ipopk15@freeuni.edu.ge", "3"));
		assertFalse(db.addSectionLeader("gkhos15@freeuni.edu.ge", "3"));
		
		assertFalse(db.addLecturer("baqara@freeuni.edu.ge", "2"));
		assertFalse(db.addSeminarist("baqara@freeuni.edu.ge", "2"));
		assertFalse(db.addSectionLeader("baquna@freeuni.edu.ge", "1"));
		assertFalse(db.addStudent("xosrika@freeuni.edu.ge", "3"));
	}
	
	@Test
	public void test4PersonsDelete() {
		assertTrue(db.seminaristExists("n.begiashvili@freeuni.edu.ge", "1"));
		assertFalse(db.deleteSeminarist("ipopk15@freeuni.edu.ge", "1"));
		assertTrue(db.deleteSeminarist("n.begiashvili@freeuni.edu.ge", "1"));
		assertFalse(db.seminaristExists("n.begiashvili@freeuni.edu.ge", "1"));
		
		assertTrue(db.lecturerExists("s.gvinepadze@freeuni.edu.ge", "1"));
		assertFalse(db.deleteLecturer("n.begiashvili@freeuni.edu.ge", "1"));
		assertTrue(db.deleteLecturer("s.gvinepadze@freeuni.edu.ge", "1"));
		assertFalse(db.lecturerExists("s.gvinepadze@freeuni.edu.ge", "1"));
		assertTrue(db.lecturerExists("s.gvinepadze@freeuni.edu.ge", "2"));
		
		assertTrue(db.sectionLeaderExists("ipopk15@freeuni.edu.ge", "3"));
		assertTrue(db.deleteSectionLeader("ipopk15@freeuni.edu.ge", "3"));
		assertTrue(db.deleteSectionLeader("gkhos15@freeuni.edu.ge", "3"));
		assertFalse(db.sectionLeaderExists("gkhos15@freeuni.edu.ge", "3"));
		assertFalse(db.sectionLeaderExists("ipopk15@freeuni.edu.ge", "3"));
		
		assertFalse(db.deleteSectionLeader("vigaca@freeuni.edu.ge", "2"));

		assertTrue(db.addPerson("tpp", "tpp", "tpp@freeuni.edu.ge"));
		assertTrue(db.addPerson("kpp", "kpp", "kpp@freeuni.edu.ge"));
		assertTrue(db.addPerson("unnamed", "unnamed", "unnamed@freeuni.edu.ge"));
		
		assertTrue(db.addStudent("tpp@freeuni.edu.ge", "1"));
		assertTrue(db.addStudent("kpp@freeuni.edu.ge", "1"));
		assertTrue(db.addStudent("unnamed@freeuni.edu.ge", "1"));
		
		assertTrue(db.studentExists("tpp@freeuni.edu.ge", "1"));
		assertTrue(db.studentExists("kpp@freeuni.edu.ge", "1"));
		assertTrue(db.studentExists("unnamed@freeuni.edu.ge", "1"));
		assertTrue(db.deleteStudent("tpp@freeuni.edu.ge", "1"));
		assertTrue(db.deleteStudent("kpp@freeuni.edu.ge", "1"));
		assertTrue(db.deleteStudent("unnamed@freeuni.edu.ge", "1"));
		assertFalse(db.studentExists("tpp@freeuni.edu.ge", "1"));
		assertFalse(db.studentExists("kpp@freeuni.edu.ge", "1"));
		assertFalse(db.studentExists("unnamed@freeuni.edu.ge", "1"));
	}
	
	@Test
	public void test5ArrayListGetters() {
		String currentClassroom = db.addClassroom("just to test");

		ArrayList <Person> realStudents = new ArrayList <Person>();
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = String.valueOf(ch);
			String surname = String.valueOf(ch);
			String email = String.valueOf(ch) + "@freeuni.edu.ge";

			assertTrue(db.addPerson(name, surname, email));
			assertTrue(db.addStudent(email, currentClassroom));
			
			realStudents.add(new Person(name, surname, email, "3"));
		}
		ArrayList <Person> students = db.getStudents(currentClassroom);
		assertEquals(students, realStudents);
		
		ArrayList <Person> realLecturers = new ArrayList <Person>(); 
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = "a" + String.valueOf(ch);
			String surname = "a" + String.valueOf(ch);
			String email = "a" + String.valueOf(ch) + "@freeuni.edu.ge";

			assertTrue(db.addPerson(name, surname, email));
			assertTrue(db.addLecturer(email, currentClassroom));
			
			realLecturers.add(new Person(name, surname, email, "2"));
		}
		ArrayList <Person> lecturers = db.getLecturers(currentClassroom);
		assertEquals(lecturers, realLecturers);

		ArrayList <Person> realSeminarists = new ArrayList <Person>(); 
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = "b" + String.valueOf(ch);
			String surname = "b" + String.valueOf(ch);
			String email = "b" + String.valueOf(ch) + "@freeuni.edu.ge";

			assertTrue(db.addPerson(name, surname, email));
			assertTrue(db.addSeminarist(email, currentClassroom));
			
			realSeminarists.add(new Person(name, surname, email, "2"));
		}
		ArrayList <Person> seminarists = db.getSeminarists(currentClassroom);
		assertEquals(seminarists, realSeminarists);

		ArrayList <Person> realSectionLeaders = new ArrayList <Person>(); 
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = "c" + String.valueOf(ch);
			String surname = "c" + String.valueOf(ch);
			String email = "c" + String.valueOf(ch) + "@freeuni.edu.ge";

			assertTrue(db.addPerson(name, surname, email));
			assertTrue(db.addSectionLeader(email, currentClassroom));
			
			realSectionLeaders.add(new Person(name, surname, email, "2"));
		}
		ArrayList <Person> sectionLeaders = db.getSectionLeaders(currentClassroom);
		assertEquals(sectionLeaders, realSectionLeaders);				
	}
}
