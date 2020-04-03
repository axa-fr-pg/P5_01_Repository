package projets.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.Person;

@SpringBootTest
public class DataUpdateServiceTest {

	private Person p1 = new Person("f1", "l1", "a1", "c1", 11111L, "t1", "e1");
	private FireStation s1 = new FireStation( "a1", 1 );

	@Autowired
	private DataUpdateService service;
	
	@MockBean
	private PersonDao personDao;

	@MockBean
	private FireStationDao stationDao;

	@BeforeEach
	private void initTestData()
	{
		try {
			when(personDao.update(p1)).thenReturn(p1);
			when(stationDao.updateByAddress(s1)).thenReturn(s1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void givenExistingP1_putPersonRequest_updatesP1()
	{
		// GIVEN
		// Test data prepared in initTestData
		// WHEN
		Person response = service.putPersonRequest(p1);
		// THEN
		assertEquals("f1", response.getFirstName());
		assertEquals("l1", response.getLastName());
	}

	@Test
	void givenExistingS1_putFireStationRequest_updatesS1()
	{
		// GIVEN
		// Test data prepared in initTestData
		// WHEN
		FireStation response = service.putFireStationRequest(s1);
		// THEN
		assertEquals("a1", response.getAddress());
		assertEquals(1, response.getStation());
	}

}
