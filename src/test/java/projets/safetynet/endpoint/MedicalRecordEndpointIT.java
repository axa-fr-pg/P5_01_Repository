package projets.safetynet.endpoint;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

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
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordEndpointIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	String[] medications1 = new String[] {"med 1", "med 2"};
	String[] allergies1 = new String[] {"allergy 1 a", "allergy 1 b", "allergy 1 c", "allergy 1 d"};
	Date date1 = Date.valueOf("1001-01-01");
	private MedicalRecord m1 = new MedicalRecord ("f1","l1",date1,medications1,allergies1);

	@Test
	public void givenMedicalRecord_whenPostMedicalRecordEndpoint_thenReturnsStatusCreated() throws Exception {	
		// GIVEN
		String j1 = objectMapper.writeValueAsString(m1);
		// WHEN & THEN
		
		mockMvc.perform(MockMvcRequestBuilders
				  .post("/medicalRecord")
				  .contentType(MediaType.APPLICATION_JSON)
		          .content(j1)
		          .accept(MediaType.APPLICATION_JSON))
		          .andExpect(status().isCreated())
		          .andExpect(jsonPath("$.firstName").value("f1")) 
		          .andExpect(jsonPath("$.lastName").value("l1"))
		          .andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void givenMedicalRecord_whenPutMedicalRecordEndpoint_thenReturnsStatusOk() throws Exception {	
		// GIVEN
		String j1 = objectMapper.writeValueAsString(m1);
		// WHEN & THEN
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/medicalRecord")
				.contentType(MediaType.APPLICATION_JSON)
		        .content(j1)
		        .accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.firstName").value("f1")) 
		        .andExpect(jsonPath("$.lastName").value("l1"))
				.andDo(MockMvcResultHandlers.print());
	}

}
