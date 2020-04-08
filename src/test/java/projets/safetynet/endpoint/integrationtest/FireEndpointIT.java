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

import projets.safetynet.model.url.FireResponse;
import projets.safetynet.service.FileService;
import projets.safetynet.service.exception.ServerDataCorruptedException;

@SpringBootTest
@AutoConfigureMockMvc
public class FireEndpointIT {

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
	public void givenAddress_whenGetFireEndpoint_thenReturnsExpectedResponse() throws Exception {
		// GIVEN
		// Test data provided by test.json 
		// WHEN & THEN
		String responseString = mockMvc.perform(get("/fire?address=testaddress"))
				.andReturn().getResponse().getContentAsString();
		FireResponse response = objectMapper.readValue(responseString, FireResponse.class);
		// THEN
		assertEquals(2, response.getStation());
		assertEquals(4, response.getInhabitants().size());
		assertEquals(4, response.getInhabitants().get(0).getMedications().length);
		assertEquals(3, response.getInhabitants().get(0).getAllergies().length);
	}

}
