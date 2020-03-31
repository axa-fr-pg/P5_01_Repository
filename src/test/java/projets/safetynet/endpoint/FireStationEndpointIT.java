package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.service.FileService;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationEndpointIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void givenStation1_whenGetFireStationEndpoint_thenReturnsExpectedResponse() throws Exception {
		// GIVEN
		// Test data provided by test.json
		// WHEN & THEN
		String responseString = mockMvc.perform(get("/firestation?stationNumber=3"))
				.andReturn().getResponse().getContentAsString();
		FireStationResponse response = objectMapper.readValue(responseString, FireStationResponse.class);
		// THEN
		assertEquals(8, response.getPersons().size());
	}
}
