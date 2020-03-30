package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.core.Data;
import projets.safetynet.model.url.FireStationPersonResponse;
import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.service.FileService;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationEndpointIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private FileService service;

	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private FireStationDao fireStationDao;
	
	@Autowired
	private MedicalRecordDao medicalRecordDao;
	
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach	
	private void loadTestFile()
	{
		Data data = service.getDataFromFile("src/test/resources/test.json");
		personDao.set(data.getPersons());
		fireStationDao.set(data.getFirestations());
		medicalRecordDao.set(data.getMedicalrecords());

	}

	@Test
	public void givenStation1_whenGetFireStationResponse_thenReturnsExpectedResponse() throws Exception {
		// GIVEN
		ArrayList<FireStationPersonResponse> persons = new ArrayList<FireStationPersonResponse>();
		FireStationResponse expected = new FireStationResponse();
		// WHEN & THEN
		String responseString = mockMvc.perform(get("/firestation?stationNumber=3"))
				.andReturn().getResponse().getContentAsString();
		FireStationResponse response = objectMapper.readValue(responseString, FireStationResponse.class);
		// THEN
		assertEquals(8, response.getPersons().size());
	}
}
