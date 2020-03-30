package projets.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.MedicalRecordNotFoundException;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.PersonNotFoundException;
import projets.safetynet.model.core.Data;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.FireStationPersonResponse;
import projets.safetynet.model.url.FireStationResponse;

@SpringBootTest
public class DataReadServiceTest {

	private Person p1 = new Person("f1", "l1", "a1", "c1", 11111L, "t1", "e1");
	private Person p2 = new Person("adultfirstname", "adultlastname", "adultaddress", "c2", 22222L, "adultphone", "e2");
	private Person p3 = new Person("childfirstname", "childlastname", "childaddress", "c3", 33333L, "childphone", "e3");
	private Person p4 = new Person("adultfirstname", "l4", "a4", "c4", 44444L, "t4", "e4");
	private Person p5 = new Person("f5", "childlastname", "a5", "c5", 55555L, "t5", "e5");
	private FireStation f1 = new FireStation("adultaddress", 12345);
	private FireStation f2 = new FireStation("a2", 2);
	private FireStation f3 = new FireStation("a3", 3);
	private FireStation f4 = new FireStation("childaddress", 12345);
	private FireStation f5 = new FireStation("a5", 5);
	private MedicalRecord m1 = new MedicalRecord("f1", "l1", new Date(), new String[]{}, new String[]{});
	private MedicalRecord m2 = new MedicalRecord("adultfirstname", "adultlastname", new Date(), new String[]{}, new String[]{});
	private MedicalRecord m3 = new MedicalRecord("childfirstname", "childlastname", new Date(), new String[]{}, new String[]{});
	private MedicalRecord m4 = new MedicalRecord("adultfirstname", "l4", new Date(), new String[]{}, new String[]{});
	private MedicalRecord m5 = new MedicalRecord("f5", "childlastname", new Date(), new String[]{}, new String[]{});

	
	@InjectMocks
	private DataReadService service;
	
	@Mock
	private PersonDao personDao;
	
	@Mock
	private FireStationDao stationDao;
	
	@Mock
	private MedicalRecordDao recordDao;
	
	@Mock
	private MedicalRecord adultRecord;
	
	@Mock
	private MedicalRecord childRecord;

	@BeforeTestClass
	private void initMocks()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void givenTestData_getUrlFirestation1_returnsCorrectValues()
	{
		// GIVEN
		when(stationDao.getByStation(12345)).thenReturn(new ArrayList<FireStation> (Arrays.asList(f1, f4)));
		when(personDao.getByAddress("adultaddress")).thenReturn(new ArrayList<Person> (Arrays.asList(p2)));
		when(personDao.getByAddress("childaddress")).thenReturn(new ArrayList<Person> (Arrays.asList(p3)));
		when(adultRecord.getAge()).thenReturn(19L);
		when(childRecord.getAge()).thenReturn(18L);
		try {
			when(recordDao.get("adultfirstname", "adultlastname")).thenReturn(adultRecord);
			when(recordDao.get("childfirstname", "childlastname")).thenReturn(childRecord);
		} catch (MedicalRecordNotFoundException e) {
			e.printStackTrace();
		}

		// WHEN
		FireStationResponse r = service.getFireStationResponse(12345);
		ArrayList<FireStationPersonResponse> p = r.getPersons();
		// THEN
		assertNotNull(r);
		assertEquals(2, p.size());
		assertEquals("adultfirstname", p.get(0).getFirstName());
		assertEquals("adultlastname", p.get(0).getLastName());
		assertEquals("childaddress", p.get(1).getAddress());
		assertEquals("childphone", p.get(1).getPhone());
		assertEquals(1, r.getNumberAdults());
		assertEquals(1, r.getNumberChildren());
	}

}
