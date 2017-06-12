package defPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 
 * In order to make this class work as Java Application, one should comment
 * DBConnection constructor
 *
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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

		ArrayList<Person> realStudents = new ArrayList<Person>();
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = String.valueOf(ch);
			String surname = String.valueOf(ch);
			String email = String.valueOf(ch) + "@freeuni.edu.ge";

			assertTrue(db.addPerson(name, surname, email));
			assertTrue(db.addStudent(email, currentClassroom));

			realStudents.add(new Person(name, surname, email, "3"));
		}
		ArrayList<Person> students = db.getStudents(currentClassroom);
		assertEquals(students, realStudents);

		ArrayList<Person> realLecturers = new ArrayList<Person>();
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = "a" + String.valueOf(ch);
			String surname = "a" + String.valueOf(ch);
			String email = "a" + String.valueOf(ch) + "@freeuni.edu.ge";

			assertTrue(db.addPerson(name, surname, email));
			assertTrue(db.addLecturer(email, currentClassroom));

			realLecturers.add(new Person(name, surname, email, "2"));
		}
		ArrayList<Person> lecturers = db.getLecturers(currentClassroom);
		assertEquals(lecturers, realLecturers);

		ArrayList<Person> realSeminarists = new ArrayList<Person>();
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = "b" + String.valueOf(ch);
			String surname = "b" + String.valueOf(ch);
			String email = "b" + String.valueOf(ch) + "@freeuni.edu.ge";

			assertTrue(db.addPerson(name, surname, email));
			assertTrue(db.addSeminarist(email, currentClassroom));

			realSeminarists.add(new Person(name, surname, email, "2"));
		}
		ArrayList<Person> seminarists = db.getSeminarists(currentClassroom);
		assertEquals(seminarists, realSeminarists);

		ArrayList<Person> realSectionLeaders = new ArrayList<Person>();
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = "c" + String.valueOf(ch);
			String surname = "c" + String.valueOf(ch);
			String email = "c" + String.valueOf(ch) + "@freeuni.edu.ge";

			assertTrue(db.addPerson(name, surname, email));
			assertTrue(db.addSectionLeader(email, currentClassroom));

			realSectionLeaders.add(new Person(name, surname, email, "2"));
		}
		ArrayList<Person> sectionLeaders = db.getSectionLeaders(currentClassroom);
		assertEquals(sectionLeaders, realSectionLeaders);
	}

	@Test
	public void test6AddSeminar() {
		String classroomId = db.addClassroom("test6");

		assertTrue(db.addSeminar("test6seminar", classroomId));
		assertFalse(db.addSeminar("fakeSeminar", "fakeClassroomId"));
		assertFalse(db.addSeminar("test6seminar", classroomId));
		assertTrue(db.getSeminarId("test6seminar", classroomId).equals("1"));

		assertTrue(db.seminarExists("test6seminar", classroomId));
		assertFalse(db.seminarExists("fakeSeminar", classroomId));
	}

	@Test
	public void test7AddPersonsToSeminar() {
		String classroomId = db.addClassroom("test7");

		db.addSeminar("test7seminar", classroomId);
		db.addPerson("seminaristi1", "seminaristi", "seminaristi1@gmail.com");
		db.addPerson("seminaristi2", "seminaristi", "seminaristi2@gmail.com");
		db.addPerson("vigac", "ucnobi", "vigac@gmail.com");
		db.addPerson("vigacseminarist", "seminaristi", "vigacseminaristi@gmail.com");

		db.addSeminarist("seminaristi1@gmail.com", classroomId);
		db.addSeminarist("seminaristi2@gmail.com", classroomId);
		assertFalse(db.addSeminarist("vigacseminaristi@gmail.com", "222"));

		assertTrue(db.addSeminaristToSeminar("test7seminar", "seminaristi1@gmail.com", classroomId));
		assertFalse(db.addSeminaristToSeminar("test7seminar", "seminaristi1@gmail.com", classroomId));
		assertFalse(db.addSeminaristToSeminar("test7seminar", "vigac@gmail.com", classroomId));
		assertFalse(db.addSeminaristToSeminar("test7seminar", "vigacseminaristi@gmail.com", classroomId));
		assertFalse(db.addSeminaristToSeminar("fakeSeminar", "fakeemail@fake.com", classroomId));
	}

	@Test
	public void test8AddSection() {
		String classroomId = db.addClassroom("test8");

		assertTrue(db.addSection("test8section1", classroomId));
		assertFalse(db.addSection("test8section1", classroomId));
		assertFalse(db.addSection("test8section2", "fakeclassroom"));

		assertTrue(db.addSection("test8section2", classroomId));
		assertTrue(db.getSectionId("test8section1", classroomId).equals("1"));

		assertTrue(db.addSection("test8sec1", classroomId));
		assertTrue(db.addSection("test8sec2", classroomId));
		assertTrue(db.addSection("test8sec3", classroomId));

		assertTrue(db.sectionExists("test8sec1", classroomId));
		assertFalse(db.sectionExists("fakeSection", classroomId));
		assertFalse(db.sectionExists("fakeSection", "fakeClassroom"));
		assertFalse(db.addSection("test8sec2", classroomId));
	}

	@Test
	public void test9AddPersonsToSection() {
		String classroomId = db.addClassroom("test9");

		db.addPerson("test9per1", "test", "test9per1@gmail.com");
		db.addPerson("test9per2", "test", "test9per2@gmail.com");
		db.addPerson("test9per3", "test", "test9per3@gmail.com");

		assertTrue(db.addSectionLeader("test9per1@gmail.com", classroomId));
		assertFalse(db.addSectionLeader("test9per1@gmail.com", classroomId));
		assertFalse(db.addSectionLeader("fakeperson@gmail.com", classroomId));
		assertTrue(db.addSectionLeader("test9per2@gmail.com", classroomId));
		assertTrue(db.addSectionLeader("test9per3@gmail.com", classroomId));

		assertTrue(db.addSection("test9sec1", classroomId));
		assertTrue(db.addSection("test9sec2", classroomId));
		assertTrue(db.addSection("test9sec3", classroomId));

		assertTrue(db.addSectionLeaderToSection("test9sec1", "test9per1@gmail.com", classroomId));
		assertFalse(db.addSectionLeaderToSection("test9sec1", "test9per1@gmail.com", classroomId));
		assertTrue(db.addSectionLeaderToSection("test9sec2", "test9per2@gmail.com", classroomId));
		assertFalse(db.addSectionLeaderToSection("fakeSectionName", "test9per3@gmail.com", classroomId));
		assertTrue(db.addSectionLeaderToSection("test9sec3", "test9per3@gmail.com", classroomId));
	}

	@Test
	public void test10DeleteSeminar() {
		String classroomId = db.addClassroom("test9");

		assertTrue(db.addSeminar("test9seminar", classroomId));
		assertTrue(db.seminarExists("test9seminar", classroomId));
		assertFalse(db.addSeminar("test9seminar", classroomId));
		assertTrue(db.deleteSeminar("test9seminar", classroomId));
		assertFalse(db.deleteSeminar("fakeSeminar", classroomId));
		assertFalse(db.seminarExists("test9seminar", classroomId));
	}

	@Test
	public void test11DeleteSection() {
		String classroomId = db.addClassroom("test10");

		assertTrue(db.addSection("test10sec1", classroomId));
		assertFalse(db.addSection("test10sec1", classroomId));
		assertTrue(db.sectionExists("test10sec1", classroomId));
		assertTrue(db.deleteSection("test10sec1", classroomId));
		assertFalse(db.deleteSection("test10sec1", classroomId));
		assertFalse(db.deleteSection("fakeSection", classroomId));
		assertTrue(db.addSection("test10sec2", classroomId));
		assertFalse(db.deleteSection("test10sec2", "fakeClassroom"));
	}

	@Test
	public void test12AddMaterial() {
		String classroomId = db.addClassroom("test11");

		assertTrue(db.addMaterial(classroomId, "test1material"));
		assertTrue(db.addMaterial(classroomId, "test2material"));
		assertTrue(db.addMaterial(classroomId, "test3material"));
		assertTrue(db.addMaterial(classroomId, "test4material"));
		assertTrue(db.addMaterial(classroomId, "test5material"));
		assertTrue(db.addMaterial(classroomId, "test6material"));
		assertTrue(db.addMaterial(classroomId, "test7material"));

	}

	@Test
	public void test13GetMaterials() {
		String classroomId = db.addClassroom("randomTest");
	
		ArrayList<Material> realMaterials = new ArrayList<Material>();
		
		realMaterials.add(new Material(classroomId,"test1material"));
		assertTrue(db.addMaterial(classroomId, "test1material"));
		
		realMaterials.add(new Material(classroomId,"test2material"));
		assertTrue(db.addMaterial(classroomId, "test2material"));
		
		realMaterials.add(new Material(classroomId,"test3material"));
		assertTrue(db.addMaterial(classroomId, "test3material"));
		
		realMaterials.add(new Material(classroomId,"test4material"));
		assertTrue(db.addMaterial(classroomId, "test4material"));
		
		realMaterials.add(new Material(classroomId,"test5material"));
		assertTrue(db.addMaterial(classroomId, "test5material"));
		
		realMaterials.add(new Material(classroomId,"test6material"));
		assertTrue(db.addMaterial(classroomId, "test6material"));
		
		realMaterials.add(new Material(classroomId,"test7material"));
		assertTrue(db.addMaterial(classroomId, "test7material"));
		
		
	
		
		ArrayList<Material> materials = db.getMaterials(classroomId);
		
		for(int i=0;i<materials.size();i++){
			System.out.println(materials.get(i).getMaterialName());
		}
		
		assertEquals(materials,realMaterials);
	}

}
