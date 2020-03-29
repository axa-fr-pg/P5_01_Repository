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
import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.core.Data;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.FireStationPersonResponse;
import projets.safetynet.model.url.FireStationResponse;

@SpringBootTest
public class DataReadServiceTest {

	private static Data data;
	
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

	@BeforeEach
	private void initTestData()
	{
		data = new Data();
		Person p1 = new Person("f1", "l1", "a1", "c1", 11111L, "t1", "e1");
		Person p2 = new Person("adultfirstname", "adultlastname", "adultaddress", "c2", 22222L, "adultphone", "e2");
		Person p3 = new Person("chilffirstname", "childlastname", "childaddress", "c3", 33333L, "childphone", "e3");
		Person p4 = new Person("adultfirstname", "l4", "a4", "c4", 44444L, "t4", "e4");
		Person p5 = new Person("f5", "childlastname", "a5", "c5", 55555L, "t5", "e5");
		data.getPersons().add(p1);
		data.getPersons().add(p2);
		data.getPersons().add(p3);
		data.getPersons().add(p4);
		data.getPersons().add(p5);

		FireStation f1 = new FireStation("adultaddress", 999);
		FireStation f2 = new FireStation("a2", 2);
		FireStation f3 = new FireStation("a3", 3);
		FireStation f4 = new FireStation("childaddress", 999);
		FireStation f5 = new FireStation("a5", 5);
		data.getFirestations().add(f1);
		data.getFirestations().add(f2);
		data.getFirestations().add(f3);
		data.getFirestations().add(f4);
		data.getFirestations().add(f5);
		
		MedicalRecord m1 = new MedicalRecord("f1", "l1", new Date(), new String[]{}, new String[]{});
		when(adultRecord.getFirstName()).thenReturn("adultfirstname");
		when(adultRecord.getLastName()).thenReturn("adultlastname");
		when(adultRecord.getAge()).thenReturn(20L);
		when(childRecord.getFirstName()).thenReturn("chilffirstname");
		when(childRecord.getLastName()).thenReturn("childlastname");
		when(childRecord.getAge()).thenReturn(17L);
		MedicalRecord m4 = new MedicalRecord("f4", "l4", new Date(), new String[]{}, new String[]{});
		MedicalRecord m5 = new MedicalRecord("f5", "f5", new Date(), new String[]{}, new String[]{});
		data.getMedicalrecords().add(m1);
		data.getMedicalrecords().add(adultRecord);
		data.getMedicalrecords().add(childRecord);
		data.getMedicalrecords().add(m4);
		data.getMedicalrecords().add(m5);
		
		when(personDao.getAll()).thenReturn(data.getPersons());
		when(stationDao.getAll()).thenReturn(data.getFirestations());
		when(recordDao.getAll()).thenReturn(data.getMedicalrecords());
	}

	@Test
	void givenTestJson_getUrlFirestation1_returnsCorrectValues()
	{
		// GIVEN
		// WHEN
		FireStationResponse r = service.getFireStationResponse(999);
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
