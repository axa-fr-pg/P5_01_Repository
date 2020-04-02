package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import projets.safetynet.model.core.Person;
import projets.safetynet.service.DataCreateService;

@SpringBootTest
public class PersonEndpointTest {

    @Autowired
    private PersonEndpoint endpoint;

    @MockBean
    private DataCreateService service;

    @Test
    public void givenNewPerson_whenPostPersonRequest_thenPersonIsCreated()
    {
    	// GIVEN
		Person pNew = new Person();
    	when(service.postPersonRequest(pNew)).thenReturn(pNew);
    	// WHEN
    	ResponseEntity<Person> response = endpoint.postPersonRequest(pNew);
    	// THEN
    	assertEquals(pNew, response.getBody());
    }
}
