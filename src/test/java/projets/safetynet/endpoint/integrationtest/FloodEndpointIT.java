package projets.safetynet.endpoint.integrationtest;

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

import projets.safetynet.model.url.FloodAddressResponse;
import projets.safetynet.service.exception.ServerDataCorruptedException;
import projets.safetynet.service.util.FileService;

@SpringBootTest
@AutoConfigureMockMvc
public class FloodEndpointIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	FileService fileService;
	
	@BeforeEach
	private void refreshData() throws ServerDataCorruptedException
	{
		fileService.loadData();
	}

	@Test
	public void givenAddress_whenGetFloodByStationEndpoint_thenReturnsExpectedResponse() throws Exception {
		// GIVEN
		// Test data provided by test.json 
		// WHEN & THEN
		String responseString = mockMvc.perform(get("/flood/stations?stations=1,3"))
				.andReturn().getResponse().getContentAsString();
		ArrayList<FloodAddressResponse> response = objectMapper.readValue(responseString, ArrayList.class);
		// THEN
		assertEquals(6, response.size());
	}

}
