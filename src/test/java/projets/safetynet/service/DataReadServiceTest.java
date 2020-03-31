package projets.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.FireStationNotFoundException;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.MedicalRecordNotFoundException;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.ChildAlertResponse;
import projets.safetynet.model.url.FirePersonResponse;
import projets.safetynet.model.url.FireResponse;
import projets.safetynet.model.url.FireStationPersonResponse;
import projets.safetynet.model.url.FireStationResponse;

@SpringBootTest
public class DataReadServiceTest {

	private Person p1 = new Person("f1", "l1", "a1", "c1", 11111L, "t1", "e1");
	private Person p2 = new Person("f2", "l2", "a2", "c2", 22222L, "t2", "e2");
	private Person p3 = new Person("f3", "l3", "a3", "c3", 33333L, "t3", "e3");
	private Person p4 = new Person("f4", "l4", "a4", "c4", 44444L, "t4", "e4");
	private Person p5 = new Person("f5", "l5", "a5", "c5", 55555L, "t5", "e5");
	private Person p6 = new Person("f6", "l6", "a6", "c6", 66666L, "t6", "e6");
	private Person p7 = new Person("f7", "l7", "a7", "c7", 77777L, "t7", "e7");
	private Person p8 = new Person("f8", "l8", "a8", "c8", 88888L, "t8", "e8");
	private Person p9 = new Person("f9", "l9", "a9", "c9", 99999L, "t9", "e9");
	private FireStation f1 = new FireStation("a1", 1);
	private FireStation f2 = new FireStation("a2", 2);
	private FireStation f3 = new FireStation("a3", 3);
	private FireStation f4 = new FireStation("a4", 4);
	private FireStation f5 = new FireStation("a5", 5);
	private String[] m1 = new String[] {"m1a"};
	private String[] m2 = new String[] {"m2a", "m2b"};
	private String[] m3 = new String[] {"m3a", "m3b", "m3c"};
	private String[] m4 = new String[] {"m4a", "m4b", "m4c", "m4d"};
	private String[] a1 = new String[] {"a1a", "a1b"};
	private String[] a2 = new String[] {"a2a", "a2b", "a2c"};
	private String[] a3 = new String[] {"a3a", "a3b", "a3c", "a3d"};
	private String[] a4 = new String[] {"a4a", "a4b", "a4c", "a4d", "a4d"};
	
	@Mock
	private MedicalRecord adultRecord1;

	@Mock
	private MedicalRecord adultRecord2;

	@Mock
	private MedicalRecord childRecord1;

	@Mock
	private MedicalRecord childRecord2;
	
	@Autowired
	private DataReadService service;
	
	@MockBean
	private PersonDao personDao;
	
	@MockBean
	private FireStationDao stationDao;

	@MockBean
	private MedicalRecordDao recordDao;

	@BeforeEach
	private void initTestData()
	{
		when(personDao.getByAddress("familyaddress")).thenReturn(new ArrayList<Person> (Arrays.asList(p2, p4, p6, p8)));
		when(personDao.getByAddress("a1")).thenReturn(new ArrayList<Person> (Arrays.asList(p2, p4)));
		when(personDao.getByAddress("a4")).thenReturn(new ArrayList<Person> (Arrays.asList(p6, p8)));

		when(stationDao.getByStation(12345)).thenReturn(new ArrayList<FireStation> (Arrays.asList(f1, f4)));
		try {
			when(stationDao.getByAddress("familyaddress")).thenReturn(f3);
		} catch (FireStationNotFoundException e1) {
			e1.printStackTrace();
		}
		
		when(adultRecord1.getAge()).thenReturn(55L);
		when(childRecord1.getAge()).thenReturn(18L);
		when(adultRecord2.getAge()).thenReturn(60L);
		when(childRecord2.getAge()).thenReturn(15L);
		when(adultRecord1.getMedications()).thenReturn(m1);
		when(childRecord1.getMedications()).thenReturn(m2);
		when(adultRecord2.getMedications()).thenReturn(m3);
		when(childRecord2.getMedications()).thenReturn(m4);
		when(adultRecord1.getAllergies()).thenReturn(a1);
		when(childRecord1.getAllergies()).thenReturn(a2);
		when(adultRecord2.getAllergies()).thenReturn(a3);
		when(childRecord2.getAllergies()).thenReturn(a4);
		
		try {
			when(recordDao.get("f2", "l2")).thenReturn(adultRecord1);
			when(recordDao.get("f4", "l4")).thenReturn(childRecord1);
			when(recordDao.get("f6", "l6")).thenReturn(adultRecord2);
			when(recordDao.get("f8", "l8")).thenReturn(childRecord2);
		} catch (MedicalRecordNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void givenTestData_getUrlFirestation1_returnsCorrectValues()
	{
		// GIVEN
		// Test data prepared in initTestData
		// WHEN
		FireStationResponse r = service.getFireStationResponse(12345);
		ArrayList<FireStationPersonResponse> p = r.getPersons();
		// THEN
		assertNotNull(r);
		assertEquals(4, p.size());
		assertEquals("f2", p.get(0).getFirstName());
		assertEquals("l2", p.get(0).getLastName());
		assertEquals("a2", p.get(0).getAddress());
		assertEquals("t2", p.get(0).getPhone());
		assertEquals("f4", p.get(1).getFirstName());
		assertEquals("l4", p.get(1).getLastName());
		assertEquals("a4", p.get(1).getAddress());
		assertEquals("t4", p.get(1).getPhone());
		assertEquals(2, r.getNumberAdults());
		assertEquals(2, r.getNumberChildren());
	}

	@Test
	void givenTestData_getChildAlertResponse_returnsCorrectValues()
	{
		// GIVEN
		// Test data prepared in initTestData
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		// WHEN
		ArrayList<ChildAlertResponse> r = service.getChildAlertResponse("familyaddress");
		// THEN
		assertNotNull(r);
		assertEquals(2, r.size());
		assertEquals("f4", r.get(0).getFirstName());
		assertEquals("l4", r.get(0).getLastName());
		assertEquals(18, r.get(0).getAge());
		assertEquals(3, r.get(0).getHousehold().size());
		sb1.append(r.get(0).getHousehold());
		assertEquals("[f2 l2, f6 l6, f8 l8]", sb1.toString());
		assertEquals("f8", r.get(1).getFirstName());
		assertEquals("l8", r.get(1).getLastName());
		assertEquals(15, r.get(1).getAge());
		assertEquals(3, r.get(1).getHousehold().size());
		sb2.append(r.get(1).getHousehold());
		assertEquals("[f2 l2, f4 l4, f6 l6]", sb2.toString());
	}

	@Test
	void givenTestData_getPhoneAlertResponse_returnsCorrectValues()
	{
		// GIVEN
		// Test data prepared in initTestData
		// WHEN
		ArrayList<String> r = service.getPhoneAlertResponse(12345);
		// THEN
		assertNotNull(r);
		assertEquals(4, r.size());
		assertEquals("t2", r.get(0));
		assertEquals("t4", r.get(1));
		assertEquals("t6", r.get(2));
		assertEquals("t8", r.get(3));
	}

	@Test
	void givenTestData_getFireResponse_returnsCorrectValues()
	{
		// GIVEN
		// Test data prepared in initTestData
		// WHEN
		FireResponse r = service.getFireResponse("familyaddress");
		// THEN
		assertNotNull(r);
		assertEquals(3, r.getStation());
		ArrayList<FirePersonResponse> persons = r.getInhabitants();
		assertEquals(4, persons.size());
		FirePersonResponse i = persons.get(0);
		assertEquals("f2 l2", i.getName());
		assertEquals("t2", i.getPhone());
		assertEquals(55, i.getAge());
		assertEquals(1, i.getMedications().length);
		assertEquals("m1a", i.getMedications()[0]);
		assertEquals(2, i.getAllergies().length);
		assertEquals("a1a", i.getAllergies()[0]);
		assertEquals("a1b", i.getAllergies()[1]);
		FirePersonResponse k = persons.get(1);
		assertEquals("f4 l4", k.getName());
		assertEquals("t4", k.getPhone());
		assertEquals(18, k.getAge());
		assertEquals(2, k.getMedications().length);
		assertEquals("m2a", k.getMedications()[0]);
		assertEquals("m2b", k.getMedications()[1]);
		assertEquals(3, k.getAllergies().length);
		assertEquals("a2a", k.getAllergies()[0]);
		assertEquals("a2b", k.getAllergies()[1]);
		assertEquals("a2c", k.getAllergies()[2]);
	}

}
