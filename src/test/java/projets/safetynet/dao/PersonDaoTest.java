package projets.safetynet.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projets.safetynet.model.core.Person;

public class PersonDaoTest {
	
	private Person p1;
	private Person p2;
	private Person p3;
	
	@BeforeEach
	private void prepareTests()
	{
		p1 = new Person ("firstName1","lastName1","address1","city1",11111,"phone1","email1");
		p2 = new Person ("firstName2","lastName2","address2","city2",22222,"phone2","email2");
		p3 = new Person ("firstName3","lastName3","address3","city3",33333,"phone3","email3");
	}
	
	@Test
	void getAll_returnsCompleteList()
	{
		// GIVEN
		ArrayList<Person> listGiven = new ArrayList<Person>(Arrays.asList(p1, p2, p3));
		PersonDao dao = new PersonDao(listGiven);
		// WHEN
		ArrayList<Person> listResult = dao.getAll();
		// THEN
		assertEquals(3, listResult.size());
	}
	
	@Test
	void givenExistingP2_getP2_returnsP2()
	{
		// GIVEN
		ArrayList<Person> listGiven = new ArrayList<Person>(Arrays.asList(p1, p2, p3));
		PersonDao dao = new PersonDao(listGiven);
		// WHEN
		Person p = null;
		try {
			p = dao.get(p2.getFirstName(), p2.getLastName());
		} catch (PersonNotFoundException e) {
			e.printStackTrace();
		}
		// THEN
		assertEquals(p2.getFirstName(), p.getFirstName());
		assertEquals(p2.getLastName(), p.getLastName());
		assertEquals(p2.getAddress(), p.getAddress());
		assertEquals(p2.getCity(), p.getCity());
		assertEquals(p2.getZip(), p.getZip());
		assertEquals(p2.getPhone(), p.getPhone());
		assertEquals(p2.getEmail(), p.getEmail());
	}

	@Test
	void givenMissingP2_getP2_throwsPersonNotFoundException()
	{
		// GIVEN
		ArrayList<Person> listGiven = new ArrayList<Person>(Arrays.asList(p1, p3));
		PersonDao dao = new PersonDao(listGiven);
		// WHEN & THEN
		assertThrows(PersonNotFoundException.class, () -> {
			dao.get(p2.getFirstName(), p2.getLastName());
		});
	}

@Test
	void saveP3_addsP3()
	{
		// GIVEN
		ArrayList<Person> listGiven = new ArrayList<Person>(Arrays.asList(p1, p2));
		PersonDao dao = new PersonDao(listGiven);
		// WHEN
		dao.save(p3);
		ArrayList<Person> listResult = dao.getAll();
		// THEN
		assertEquals(3, listResult.size());
	}
	
	@Test
	void updateP2_changesP2()
	{
		// GIVEN
		ArrayList<Person> listGiven = new ArrayList<Person>(Arrays.asList(p1, p2, p3));
		PersonDao dao = new PersonDao(listGiven);
		// WHEN
		Person p4 = new Person (p2.getFirstName(),p2.getLastName(),"address-test-value",
				"city-test-value", 44444, "phone-test-value", "email-test-value");
		try {
			dao.update(p4);
		} catch (PersonNotFoundException e1) {
			e1.printStackTrace();
		}
		Person p = null;
		try {
			p = dao.get(p2.getFirstName(), p2.getLastName());
		} catch (PersonNotFoundException e) {
			e.printStackTrace();
		}
		// THEN
		assertEquals(p4.getAddress(), p.getAddress());
		assertEquals(p4.getCity(), p.getCity());
		assertEquals(p4.getZip(), p.getZip());
		assertEquals(p4.getPhone(), p.getPhone());
		assertEquals(p4.getEmail(), p.getEmail());
	}

	@Test
	void givenMissingP2_updateP2_throwsPersonNotFoundException()
	{
		// GIVEN
		ArrayList<Person> listGiven = new ArrayList<Person>(Arrays.asList(p1, p3));
		PersonDao dao = new PersonDao(listGiven);
		// WHEN & THEN
		assertThrows(PersonNotFoundException.class, () -> {
			dao.update(p2);
		});
	}

	@Test
	void givenP2_deleteP2_suppressesP2()
	{
		// GIVEN
		ArrayList<Person> listGiven = new ArrayList<Person>(Arrays.asList(p1, p2, p3));
		PersonDao dao = new PersonDao(listGiven);
		// WHEN
		dao.delete(p2);
		ArrayList<Person> listResult = dao.getAll();
		// THEN
		assertEquals(2, listResult.size());
	}
}
