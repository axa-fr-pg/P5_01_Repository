package projets.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

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
	
	@BeforeTestClass
	private void initMocks()
	{
		MockitoAnnotations.initMocks(this);
	}
	
	@BeforeAll
	private static void loadTestFile()
	{
		data = FileService.getDataFromFile("src/test/resources/test.json");
	}

	@BeforeEach
	private void loadDataIntoDao()
	{
		when(personDao.getAll()).thenReturn(data.getPersons());
		when(stationDao.getAll()).thenReturn(data.getFirestations());
		when(recordDao.getAll()).thenReturn(data.getMedicalrecords());
	}

	@Test
	void givenTestJson_getUrlFirestation1_returnsCorrectValues()
	{
		// GIVEN station 1 in charge of Peter, Reginold, Jamie, Brian, Shawna, Kendrik
		// WHEN
		FireStationResponse r = service.getFireStationResponse(1);
		ArrayList<FireStationPersonResponse> p = r.getPersons();
		// THEN
		assertNotNull(r);
		assertEquals(6, p.size());
		assertEquals("Peter", p.get(0).getFirstName());
		assertEquals("WWWWWWW", p.get(1).getLastName());
		assertEquals("444 Gershwin Cir", p.get(0).getAddress());
		assertEquals("241-234-3442", p.get(2).getPhone());
		assertEquals(5, r.getNumberAdults());
		assertEquals(1, r.getNumberChildren());
	}

}
