package defPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import database.AllConnections;
import database.ClassroomDB;
import database.DBConnection;
import database.LecturerDB;
import database.MaterialDB;
import database.PersonDB;
import database.SectionDB;
import database.SectionLeaderDB;
import database.SeminarDB;
import database.SeminaristDB;
import database.StudentDB;

/**
 * 
 * In order to make this class work as Java Application, one should comment
 * DBConnection constructor
 *
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDBConnection {

	private MockDBConnection db;
	private PersonDB personDB;
	private ClassroomDB cl;
	private PersonDB pr;
	private StudentDB st;
	private LecturerDB lr;
	private SeminaristDB sm;
	private SectionLeaderDB sl;
	private SeminarDB smn;
	private SectionDB sc;
	private SectionLeaderDB scl;
	private MaterialDB ma;
	private AllConnections allConnections;
	@Before
	public void initialize() {
		allConnections = new AllConnections();
		db = new MockDBConnection();
		personDB = allConnections.personDB;
		cl = allConnections.classroomDB;
		pr = allConnections.personDB;
		st = allConnections.studentDB;
		lr = allConnections.lecturerDB;
		sm = allConnections.seminaristDB;
		sl = allConnections.sectionLeaderDB;
		smn = allConnections.seminarDB;
		scl = allConnections.sectionLeaderDB;
		sc = allConnections.sectionDB;
		ma = allConnections.materialDB;
	}

	@Test
	public void test1AddingPerson() {
		assertTrue(personDB.addPerson("irakli", "popkhadze", "ipopk15@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
		assertTrue(personDB.addPerson("giorgi", "khosroshvili", "gkhos15@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
		assertTrue(personDB.addPerson("shota", "gvinepadze", "s.gvinepadze@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
		assertTrue(personDB.addPerson("nika", "begiashvili", "n.begiashvili@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
		assertTrue(personDB.addPerson("giorgi", "cercvadze", "gitser15@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
		assertTrue(personDB.addPerson("aleko", "cxovrebovi", "acxcx15@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
		assertTrue(personDB.addPerson("mari", "berishvili", "mberi15@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));

		assertFalse(personDB.addPerson("shota", "gvinepadze", "s.gvinepadze@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
		assertFalse(personDB.addPerson("vigaca", "vigaca", "ipopk15@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
	}

	@Test
	public void test2AddingClassroom() {
		ClassroomDB classroomDB = allConnections.classroomDB;
		assertEquals(classroomDB.addClassroom("OOP", "1"), "4");
		assertEquals(classroomDB.addClassroom("Paradigms", "1"), "5");
		assertEquals(classroomDB.addClassroom("Methodologies", "1"), "6");
		assertEquals(classroomDB.addClassroom("iraklis'", "1"), DBConnection.DATABASE_ERROR);
	}

	@Test
	public void test3AddingToClassrom() {
		LecturerDB lecturerDB = allConnections.lecturerDB;
		assertTrue(lecturerDB.addLecturer("s.gvinepadze@freeuni.edu.ge", "1"));
		assertTrue(lecturerDB.addLecturer("s.gvinepadze@freeuni.edu.ge", "2"));
		assertTrue(lecturerDB.addLecturer("s.gvinepadze@freeuni.edu.ge", "3"));

		SeminaristDB seminaristDB = allConnections.seminaristDB;
		assertTrue(seminaristDB.addSeminarist("n.begiashvili@freeuni.edu.ge", "1"));
		assertTrue(seminaristDB.addSeminarist("n.begiashvili@freeuni.edu.ge", "2"));
		
		SectionLeaderDB sectionLeaderDB = allConnections.sectionLeaderDB;
		assertTrue(sectionLeaderDB.addSectionLeader("ipopk15@freeuni.edu.ge", "3"));
		assertTrue(sectionLeaderDB.addSectionLeader("gkhos15@freeuni.edu.ge", "3"));
		assertTrue(sectionLeaderDB.addSectionLeader("acxcx15@freeuni.edu.ge", "3"));

		assertFalse(sectionLeaderDB.addSectionLeader("ipopk15@freeuni.edu.ge", "3"));
		assertFalse(sectionLeaderDB.addSectionLeader("gkhos15@freeuni.edu.ge", "3"));

		assertFalse(lecturerDB.addLecturer("baqara@freeuni.edu.ge", "2"));
		assertFalse(seminaristDB.addSeminarist("baqara@freeuni.edu.ge", "2"));
		assertFalse(sectionLeaderDB.addSectionLeader("baquna@freeuni.edu.ge", "1"));
		
		StudentDB studentDB = allConnections.studentDB;
		assertFalse(studentDB.addStudent("xosrika@freeuni.edu.ge", "3"));
	}

	@Test
	public void test4PersonsDelete() {
		SeminaristDB seminaristDB = allConnections.seminaristDB;
		LecturerDB lecturerDB = allConnections.lecturerDB;
		assertTrue(seminaristDB.seminaristExists("n.begiashvili@freeuni.edu.ge", "1"));
		assertFalse(seminaristDB.deleteSeminarist("ipopk15@freeuni.edu.ge", "1"));
		assertTrue(seminaristDB.deleteSeminarist("n.begiashvili@freeuni.edu.ge", "1"));
		assertFalse(seminaristDB.seminaristExists("n.begiashvili@freeuni.edu.ge", "1"));

		assertTrue(lecturerDB.lecturerExists("s.gvinepadze@freeuni.edu.ge", "1"));
		assertFalse(lecturerDB.deleteLecturer("n.begiashvili@freeuni.edu.ge", "1"));
		assertTrue(lecturerDB.deleteLecturer("s.gvinepadze@freeuni.edu.ge", "1"));
		assertFalse(lecturerDB.lecturerExists("s.gvinepadze@freeuni.edu.ge", "1"));
		assertTrue(lecturerDB.lecturerExists("s.gvinepadze@freeuni.edu.ge", "2"));

		SectionLeaderDB sectionLeaderDB = allConnections.sectionLeaderDB;
		assertTrue(sectionLeaderDB.sectionLeaderExists("ipopk15@freeuni.edu.ge", "3"));
		assertTrue(sectionLeaderDB.deleteSectionLeader("ipopk15@freeuni.edu.ge", "3"));
		assertTrue(sectionLeaderDB.deleteSectionLeader("gkhos15@freeuni.edu.ge", "3"));
		assertFalse(sectionLeaderDB.sectionLeaderExists("gkhos15@freeuni.edu.ge", "3"));
		assertFalse(sectionLeaderDB.sectionLeaderExists("ipopk15@freeuni.edu.ge", "3"));
		assertFalse(sectionLeaderDB.deleteSectionLeader("vigaca@freeuni.edu.ge", "2"));

		PersonDB personDB = allConnections.personDB;
		assertTrue(personDB.addPerson("tpp", "tpp", "tpp@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
		assertTrue(personDB.addPerson("kpp", "kpp", "kpp@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
		assertTrue(personDB.addPerson("unnamed", "unnamed", "unnamed@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
		
		StudentDB studentDB = allConnections.studentDB;
		assertTrue(studentDB.addStudent("tpp@freeuni.edu.ge", "1"));
		assertTrue(studentDB.addStudent("kpp@freeuni.edu.ge", "1"));
		assertTrue(studentDB.addStudent("unnamed@freeuni.edu.ge", "1"));

		assertTrue(studentDB.studentExists("tpp@freeuni.edu.ge", "1"));
		assertTrue(studentDB.studentExists("kpp@freeuni.edu.ge", "1"));
		assertTrue(studentDB.studentExists("unnamed@freeuni.edu.ge", "1"));
		assertTrue(studentDB.deleteStudent("tpp@freeuni.edu.ge", "1"));
		assertTrue(studentDB.deleteStudent("kpp@freeuni.edu.ge", "1"));
		assertTrue(studentDB.deleteStudent("unnamed@freeuni.edu.ge", "1"));
		assertFalse(studentDB.studentExists("tpp@freeuni.edu.ge", "1"));
		assertFalse(studentDB.studentExists("kpp@freeuni.edu.ge", "1"));
		assertFalse(studentDB.studentExists("unnamed@freeuni.edu.ge", "1"));
	}

	@Test
	public void test5ArrayListGetters() {
		
		String currentClassroom = cl.addClassroom("just to test", "1");

		ArrayList<Person> realStudents = new ArrayList<Person>();
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = String.valueOf(ch);
			String surname = String.valueOf(ch);
			String email = String.valueOf(ch) + "@freeuni.edu.ge";

			assertTrue(pr.addPerson(name, surname, email,"http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
			assertTrue(st.addStudent(email, currentClassroom));

			realStudents.add(new Person(name, surname, email, "3"));
		}
		ArrayList<Person> students = st.getStudents(currentClassroom);
		assertEquals(students, realStudents);

		ArrayList<Person> realLecturers = new ArrayList<Person>();
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = "a" + String.valueOf(ch);
			String surname = "a" + String.valueOf(ch);
			String email = "a" + String.valueOf(ch) + "@freeuni.edu.ge";

			assertTrue(pr.addPerson(name, surname, email,"http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
			assertTrue(lr.addLecturer(email, currentClassroom));

			realLecturers.add(new Person(name, surname, email, "2"));
		}
		ArrayList<Person> lecturers = lr.getLecturers(currentClassroom);
		assertEquals(lecturers, realLecturers);

		ArrayList<Person> realSeminarists = new ArrayList<Person>();
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = "b" + String.valueOf(ch);
			String surname = "b" + String.valueOf(ch);
			String email = "b" + String.valueOf(ch) + "@freeuni.edu.ge";

			assertTrue(pr.addPerson(name, surname, email,"http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
			assertTrue(sm.addSeminarist(email, currentClassroom));

			realSeminarists.add(new Person(name, surname, email, "2"));
		}
		ArrayList<Person> seminarists = sm.getSeminarists(currentClassroom);
		assertEquals(seminarists, realSeminarists);

		ArrayList<Person> realSectionLeaders = new ArrayList<Person>();
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = "c" + String.valueOf(ch);
			String surname = "c" + String.valueOf(ch);
			String email = "c" + String.valueOf(ch) + "@freeuni.edu.ge";

			assertTrue(pr.addPerson(name, surname, email,"http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042"));
			assertTrue(sl.addSectionLeader(email, currentClassroom));

			realSectionLeaders.add(new Person(name, surname, email, "2"));
		}
		ArrayList<Person> sectionLeaders = sl.getSectionLeaders(currentClassroom);
		assertEquals(sectionLeaders, realSectionLeaders);
	}

	@Test
	public void test6AddSeminar() {
		
		String classroomId = cl.addClassroom("test6", "1");

		assertTrue(smn.addSeminar(classroomId));
		assertFalse(smn.addSeminar("fakeClassroomId"));
		assertTrue(smn.addSeminar(classroomId));

		assertTrue(smn.seminarExists(1, classroomId));
		assertFalse(smn.seminarExists(123, classroomId));
	}

	@Test
	public void test7AddPersonsToSeminar() {
		String classroomId = cl.addClassroom("test7", "1");

		smn.addSeminar(classroomId);
		pr.addPerson("seminaristi1", "seminaristi", "seminaristi1@gmail.com","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");
		pr.addPerson("seminaristi2", "seminaristi", "seminaristi2@gmail.com","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");
		pr.addPerson("vigac", "ucnobi", "vigac@gmail.com","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");
		pr.addPerson("vigacseminarist", "seminaristi", "vigacseminaristi@gmail.com","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");

		sm.addSeminarist("seminaristi1@gmail.com", classroomId);
		sm.addSeminarist("seminaristi2@gmail.com", classroomId);
		assertFalse(sm.addSeminarist("vigacseminaristi@gmail.com", "222"));
		assertTrue(smn.addSeminaristToSeminar(0, "seminaristi1@gmail.com", classroomId));
		assertFalse(smn.addSeminaristToSeminar(0, "seminaristi1@gmail.com", classroomId));
		assertFalse(smn.addSeminaristToSeminar(0, "vigac@gmail.com", classroomId));
		assertFalse(smn.addSeminaristToSeminar(0, "vigacseminaristi@gmail.com", classroomId));
		assertFalse(smn.addSeminaristToSeminar(123, "fakeemail@fake.com", classroomId));
	}

	@Test
	public void test8AddSection() {
		String classroomId = cl.addClassroom("test8","1");

		assertTrue(sc.addSection(classroomId));
		assertTrue(sc.addSection(classroomId));
		assertFalse(sc.addSection("fakeclassroom"));

		assertTrue(sc.addSection(classroomId));
		assertTrue(sc.addSection(classroomId));
		assertTrue(sc.addSection(classroomId));
		assertTrue(sc.addSection(classroomId));

		assertTrue(sc.sectionExists(1, classroomId));
		assertFalse(sc.sectionExists(123, classroomId));
		assertFalse(sc.sectionExists(123, "fakeClassroom"));
		assertTrue(sc.addSection(classroomId));
	}

	@Test
	public void test9AddPersonsToSection() {
		String classroomId = cl.addClassroom("test9", "1");

		pr.addPerson("test9per1", "test", "test9per1@gmail.com","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");
		pr.addPerson("test9per2", "test", "test9per2@gmail.com","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");
		pr.addPerson("test9per3", "test", "test9per3@gmail.com","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");

		assertTrue(scl.addSectionLeader("test9per1@gmail.com", classroomId));
		assertFalse(scl.addSectionLeader("test9per1@gmail.com", classroomId));
		assertFalse(scl.addSectionLeader("fakeperson@gmail.com", classroomId));
		assertTrue(scl.addSectionLeader("test9per2@gmail.com", classroomId));
		assertTrue(scl.addSectionLeader("test9per3@gmail.com", classroomId));

		assertTrue(sc.addSection(classroomId));
		assertTrue(sc.addSection(classroomId));
		assertTrue(sc.addSection(classroomId));

		assertTrue(sc.addSectionLeaderToSection(1, "test9per1@gmail.com", classroomId));
		assertFalse(sc.addSectionLeaderToSection(1, "test9per1@gmail.com", classroomId));
		assertTrue(sc.addSectionLeaderToSection(2, "test9per2@gmail.com", classroomId));
		assertFalse(sc.addSectionLeaderToSection(123, "test9per3@gmail.com", classroomId));
		assertFalse(sc.addSectionLeaderToSection(3, "test9per3@gmail.com", classroomId));
		assertFalse(sc.addSectionLeaderToSection(2, "test9per3@gmail.com", classroomId));
	}

	@Test
	public void test10DeleteSeminar() {
		String classroomId = cl.addClassroom("test9", "1");

		assertTrue(smn.addSeminar(classroomId));
		assertTrue(smn.seminarExists(0, classroomId));
		assertTrue(smn.addSeminar(classroomId));
		assertTrue(smn.deleteSeminar(classroomId));
		assertFalse(smn.seminarExists(1, classroomId));
		assertTrue(smn.seminarExists(0, classroomId));
		assertTrue(smn.deleteSeminar(classroomId));
		assertFalse(smn.deleteSeminar(classroomId));
	}

	@Test
	public void test11DeleteSection() {
		String classroomId = cl.addClassroom("test10", "1");

		assertTrue(sc.addSection(classroomId));
		assertTrue(sc.addSection(classroomId));
		assertTrue(sc.sectionExists(0, classroomId));
		assertTrue(sc.deleteSection(classroomId));
		assertTrue(sc.deleteSection(classroomId));
		assertFalse(sc.deleteSection(classroomId));
		assertTrue(sc.addSection(classroomId));
		assertFalse(sc.deleteSection("fakeClassroom"));
		assertTrue(sc.deleteSection(classroomId));
		assertFalse(sc.deleteSection(classroomId));
	}

	@Test
	public void test12AddMaterial() {
		String classroomId = cl.addClassroom("test11", "1");
		
		/*
		assertTrue(ma.addMaterial(classroomId, "test1material"));
		assertTrue(ma.addMaterial(classroomId, "test2material"));
		assertTrue(ma.addMaterial(classroomId, "test3material"));
		assertTrue(ma.addMaterial(classroomId, "test4material"));
		assertTrue(ma.addMaterial(classroomId, "test5material"));
		assertTrue(ma.addMaterial(classroomId, "test6material"));
		assertTrue(ma.addMaterial(classroomId, "test7material"));
		
		*/

	}

	@Test
	public void test13GetMaterials() {
		
		ArrayList<Material> realMaterials = new ArrayList<Material>();
		/*
		realMaterials.add(new Material(classroomId,"test1material"));
		assertTrue(ma.addMaterial(classroomId, "test1material"));
		
		realMaterials.add(new Material(classroomId,"test2material"));
		assertTrue(ma.addMaterial(classroomId, "test2material"));
		
		realMaterials.add(new Material(classroomId,"test3material"));
		assertTrue(ma.addMaterial(classroomId, "test3material"));
		
		realMaterials.add(new Material(classroomId,"test4material"));
		assertTrue(ma.addMaterial(classroomId, "test4material"));
		
		realMaterials.add(new Material(classroomId,"test5material"));
		assertTrue(ma.addMaterial(classroomId, "test5material"));
		
		realMaterials.add(new Material(classroomId,"test6material"));
		assertTrue(ma.addMaterial(classroomId, "test6material"));
		
		realMaterials.add(new Material(classroomId,"test7material"));
		assertTrue(ma.addMaterial(classroomId, "test7material"));
		
		ArrayList<Material> materials = ma.getMaterials(classroomId);
		
		assertEquals(materials,realMaterials);
		
		*/
	}

}
