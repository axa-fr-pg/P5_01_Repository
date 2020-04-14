package projets.safetynet.service.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.url.PersonRequest;
import projets.safetynet.service.exception.InvalidDeleteFireStationRequestException;

@SpringBootTest
public class DataDeleteServiceTest {

	@Autowired
	private DataDeleteService service;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PersonDao personDao;

	@MockBean
	private FireStationDao stationDao;

	@MockBean
	private MedicalRecordDao recordDao;

	private FireStation s1 = new FireStation("a1", 1);
	String[] medications1 = new String[] {};
	String[] allergies1 = new String[] {"allergy 1 a", "allergy 1 b", "allergy 1 c", "allergy 1 d"};
	Date date1 = Date.valueOf("1001-01-01");

	@BeforeEach
	private void initTestData() throws Exception
	{
		when(personDao.delete("f1", "l1")).thenReturn(true);
		when(stationDao.delete("a1", 1)).thenReturn(true);
		when(stationDao.deleteByAddress("a1")).thenReturn(true);
		when(stationDao.deleteByStation(1)).thenReturn(true);
		when(recordDao.delete("f1", "l1")).thenReturn(true);
}

	@Test
	void givenExistingP1_deletePersonRequest_returnsTrue() throws Exception
	{
		// GIVEN
		// Test data prepared in initTestData
		PersonRequest request = new PersonRequest("f1", "l1");
		// WHEN
		boolean response = service.deletePersonRequest(request);
		// THEN
		assertEquals(true, response);
	}

	@Test
	void givenExistingS1_deleteFireStationByAddressAndStationRequest_returnsTrue() throws Exception
	{
		// GIVEN
		// Test data prepared in initTestData
		boolean response = false;
		String j1 = objectMapper.writeValueAsString(s1);
		// WHEN
		response = service.deleteFireStationRequest(j1);
		// THEN
		assertEquals(true, response);
	}

	@Test
	void givenExistingS1_deleteFireStationByAddressRequest_returnsTrue() throws Exception
	{
		// GIVEN
		// Test data prepared in initTestData
		boolean response = false;
		String j1 = "{ \"address\" : \"a1\" }";
		// WHEN
		response = service.deleteFireStationRequest(j1);
		// THEN
		assertEquals(true, response);
	}

	@Test
	void givenExistingS1_deleteFireStationByStationRequest_returnsTrue() throws Exception
	{
		// GIVEN
		// Test data prepared in initTestData
		boolean response = false;
		String j1 = "{ \"station\" : \"1\" }";
		// WHEN
		response = service.deleteFireStationRequest(j1);
		// THEN
		assertEquals(true, response);
	}

	@Test
	void givenInvalidRequest_deleteFireStationByStationRequest_throwsInvalidDeleteFireStationRequestException()
	{
		// GIVEN
		String j1 = "{ \"invalid\" : \"request\" }";
		// WHEN & THEN
		assertThrows(InvalidDeleteFireStationRequestException.class, () -> {
			service.deleteFireStationRequest(j1);
		});
	}

	@Test
	void givenExistingM1_deleteMedicalRecordRequest_returnsTrue() throws Exception
	{
		// GIVEN
		// Test data prepared in initTestData
		PersonRequest request = new PersonRequest("f1", "l1");
		// WHEN
		boolean response = service.deleteMedicalRecordRequest(request);
		// THEN
		assertEquals(true, response);
	}

}
