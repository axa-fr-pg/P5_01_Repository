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

import projets.safetynet.model.url.PersonInfoResponse;
import projets.safetynet.service.FileService;
import projets.safetynet.service.exception.ServerDataCorruptedException;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonInfoEndpointIT {
	
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
	public void givenFirstNameAndLastName_whenGetPersonInfoEndpoint_thenReturnsExpectedResponse() throws Exception {
		// GIVEN
		// Test data provided by test.json 
		// WHEN & THEN
		String responseString = mockMvc.perform(get("/personInfo?firstName=Sophia&lastName=ZZZZZZZZZ"))
				.andReturn().getResponse().getContentAsString();
		ArrayList response = (ArrayList<PersonInfoResponse>) objectMapper.readValue(responseString, ArrayList.class);
		// THEN
		assertEquals(1, response.size());
	}

	@Test
	public void givenFirstName_whenGetPersonInfoEndpoint_thenReturnsExpectedResponse() throws Exception {
		// GIVEN
		// Test data provided by test.json 
		// WHEN & THEN
		String responseString = mockMvc.perform(get("/personInfo?firstName=John"))
				.andReturn().getResponse().getContentAsString();
		ArrayList<PersonInfoResponse> response = (ArrayList<PersonInfoResponse>) 
				objectMapper.readValue(responseString, ArrayList.class);
		// THEN
		assertEquals(2, response.size());
	}

	@Test
	public void givenLastName_whenGetPersonInfoEndpoint_thenReturnsExpectedResponse() throws Exception {
		// GIVEN
		// Test data provided by test.json 
		// WHEN & THEN
		String responseString = mockMvc.perform(get("/personInfo?lastName=AAAA"))
				.andReturn().getResponse().getContentAsString();
		ArrayList<PersonInfoResponse> response = (ArrayList<PersonInfoResponse>) 
				objectMapper.readValue(responseString, ArrayList.class);
		// THEN
		assertEquals(6, response.size());
	}

}
