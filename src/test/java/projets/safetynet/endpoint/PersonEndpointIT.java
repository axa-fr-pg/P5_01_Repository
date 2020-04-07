package projets.safetynet.endpoint;

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

import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.PersonRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonEndpointIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	private Person p1 = new Person("f1", "l1", "a1", "c1", 11111L, "t1", "e1");
	private PersonRequest r1 = new PersonRequest("Roger", "AAAA");
	
	@Test
	public void givenPerson_whenPostPersonEndpoint_thenReturnsStatusCreated() throws Exception {	
		// GIVEN
		String j1 = objectMapper.writeValueAsString(p1);
		// WHEN & THEN
		
		mockMvc.perform(MockMvcRequestBuilders
				  .post("/person")
				  .contentType(MediaType.APPLICATION_JSON)
		          .content(j1)
		          .accept(MediaType.APPLICATION_JSON))
		          .andExpect(status().isCreated())
		          .andExpect(jsonPath("$.firstName").value("f1")) 
		          .andExpect(jsonPath("$.lastName").value("l1"))
		          .andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void givenPerson_whenPutPersonEndpoint_thenReturnsStatusOk() throws Exception {	
		// GIVEN
		String j1 = objectMapper.writeValueAsString(p1);
		// WHEN & THEN
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/person")
				.contentType(MediaType.APPLICATION_JSON)
		        .content(j1)
		        .accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.firstName").value("f1")) 
		        .andExpect(jsonPath("$.lastName").value("l1"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void givenPerson_whenDeletePersonEndpoint_thenReturnsStatusAccepted() throws Exception {	
		// GIVEN
		String j1 = objectMapper.writeValueAsString(r1);
		// WHEN & THEN
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/person")
				.contentType(MediaType.APPLICATION_JSON)
		        .content(j1)
		        .accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isAccepted())
        		.andDo(MockMvcResultHandlers.print());
	}
}
