package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.service.FileService;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationEndpointIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private FireStation s1 = new FireStation("a1", 1);

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

	@Test
	public void givenFireStation_whenPostFireStationEndpoint_thenReturnsStatusCreated() throws Exception {	
		// GIVEN
		String j1 = objectMapper.writeValueAsString(s1);
		// WHEN & THEN
		
		mockMvc.perform(MockMvcRequestBuilders
				  .post("/firestation")
				  .contentType(MediaType.APPLICATION_JSON)
		          .content(j1)
		          .accept(MediaType.APPLICATION_JSON))
		          .andExpect(status().isCreated())
		          .andExpect(jsonPath("$.address").value("a1")) 
		          .andExpect(jsonPath("$.station").value(1))
		          .andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void givenFireStation_whenPutFireStationEndpoint_thenReturnsStatusOk() throws Exception {	
		// GIVEN
		String j1 = objectMapper.writeValueAsString(s1);
		// WHEN & THEN
		
		mockMvc.perform(MockMvcRequestBuilders
				  .put("/firestation")
				  .contentType(MediaType.APPLICATION_JSON)
		          .content(j1)
		          .accept(MediaType.APPLICATION_JSON))
		          .andExpect(status().isOk())
		          .andExpect(jsonPath("$.address").value("a1")) 
		          .andExpect(jsonPath("$.station").value(1))
		          .andDo(MockMvcResultHandlers.print());
	}

}
