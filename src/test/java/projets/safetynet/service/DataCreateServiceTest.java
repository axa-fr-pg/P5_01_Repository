package projets.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.dao.MultiplePersonWithSameNameException;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.PersonNotFoundException;
import projets.safetynet.model.core.Person;

@SpringBootTest
public class DataCreateServiceTest {

	private Person p1 = new Person("f1", "l1", "a1", "c1", 11111L, "t1", "e1");

	@Autowired
	private DataCreateService service;
	
	@MockBean
	private PersonDao personDao;

	@BeforeEach
	private void initTestData()
	{
		try {
			when(personDao.get("f1", "l1")).thenReturn(p1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void givenNewP1_postPersonRequest_savesP1()
	{
		// GIVEN
		// Test data provided by test.json
		// WHEN
		Person response = service.postPersonRequest(p1);
		// THEN
		assertEquals("f1", response.getFirstName());
		assertEquals("l1", response.getLastName());
	}

}
