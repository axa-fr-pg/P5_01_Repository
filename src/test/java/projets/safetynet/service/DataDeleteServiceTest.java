package projets.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.core.Person;

@SpringBootTest
public class DataDeleteServiceTest {

	@Autowired
	private DataDeleteService service;
	
	@MockBean
	private PersonDao personDao;

	private Person p1 = new Person("f1", "l1", "a1", "c1", 11111L, "t1", "e1");

	@BeforeEach
	private void initTestData()
	{
		try {
			when(personDao.delete(p1)).thenReturn(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void givenExistingP1_deletePersonRequest_updatesP1()
	{
		// GIVEN
		// Test data prepared in initTestData
		// WHEN
		boolean response = service.deletePersonRequest(p1);
		// THEN
		assertEquals(true, response);
	}

}
