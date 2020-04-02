package projets.safetynet.endpoint;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import projets.safetynet.model.core.Person;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonEndpointIT {

//	String p1 = "{ \"firstName\":\"f1\", \"lastName\":\"l1\", \"address\":\"a1\", \"city\":\"c1\","
//			+ " \"zip\":\"11111\", \"phone\":\"t1\", \"email\":\"e1\" }"; 
	private Person p1 = new Person("f1", "l1", "a1", "c1", 11111L, "t1", "e1");
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void givenPerson_whenPostPersonEndpoint_thenReturnsStatusCreated() throws Exception {

		
		// https://www.briansdevblog.com/2017/05/rest-endpoint-testing-with-mockmvc/
		// https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/
		
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
		          .andExpect(jsonPath("$.lastName").value("l1"));
	}

}
