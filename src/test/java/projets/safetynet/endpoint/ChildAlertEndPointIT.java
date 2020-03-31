package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import projets.safetynet.model.url.FireStationPersonResponse;
import projets.safetynet.model.url.FireStationResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class ChildAlertEndPointIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void givenAddress_whenGetFireStationResponse_thenReturnsExpectedResponse() throws Exception {
		// GIVEN
		ArrayList<FireStationPersonResponse> persons = new ArrayList<FireStationPersonResponse>();
		FireStationResponse expected = new FireStationResponse();
		// WHEN & THEN
		String responseString = mockMvc.perform(get("/childAlert?address=testaddress"))
				.andReturn().getResponse().getContentAsString();
		ArrayList response = objectMapper.readValue(responseString, ArrayList.class);
		// THEN
		assertEquals(2, response.size());
	}

}
