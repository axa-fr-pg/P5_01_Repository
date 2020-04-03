package projets.safetynet.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.FireStationNotFoundException;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.PersonRequest;

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

	private Person p1 = new Person("f1", "l1", "a1", "c1", 11111L, "t1", "e1");
	private FireStation s1 = new FireStation("a1", 1);

	@BeforeEach
	private void initTestData() throws Exception
	{
		when(personDao.delete("f1", "l1")).thenReturn(true);
		when(stationDao.delete("a1", 1)).thenReturn(true);
		when(stationDao.deleteByAddress("a1")).thenReturn(true);
		when(stationDao.deleteByStation(1)).thenReturn(true);
}

	@Test
	void givenExistingP1_deletePersonRequest_returnsTrue()
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

}
