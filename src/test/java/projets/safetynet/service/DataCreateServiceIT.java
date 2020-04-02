package projets.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.core.Person;

@SpringBootTest
public class DataCreateServiceIT {

	private Person p1 = new Person("f1", "l1", "a1", "c1", 11111L, "t1", "e1");

	@Autowired
	private DataCreateService service;
	
	@Autowired
	private PersonDao personDao;

	@Test
	void givenNewP1_postPersonRequest_savesP1()
	{
		// GIVEN
		// Test data provided by test.json
		// WHEN
		service.postPersonRequest(p1);
		ArrayList<Person> allPersons = personDao.getAll();
		// THEN
		assertEquals(24, allPersons.size());
		assertEquals("f1", allPersons.get(23).getFirstName());
		assertEquals("l1", allPersons.get(23).getLastName());
	}

}
