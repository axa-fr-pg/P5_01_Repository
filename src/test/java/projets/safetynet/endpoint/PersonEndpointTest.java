package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.PersonRequest;
import projets.safetynet.service.DataCreateService;
import projets.safetynet.service.DataDeleteService;
import projets.safetynet.service.DataUpdateService;

@SpringBootTest
public class PersonEndpointTest {

    @Autowired
    private PersonEndpoint endpoint;

    @MockBean
    private DataCreateService createService;

    @MockBean
    private DataUpdateService updateService;

    @MockBean
    private DataDeleteService deleteService;

    @Test
    public void givenNewPerson_whenPostPersonRequest_thenPersonIsCreated()
    {
    	// GIVEN
		Person pNew = new Person();
    	when(createService.postPersonRequest(pNew)).thenReturn(pNew);
    	// WHEN
    	ResponseEntity<Person> response = endpoint.postPersonRequest(pNew);
    	// THEN
    	assertEquals(pNew, response.getBody());
    }

    @Test
    public void givenExistingPerson_whenPutPersonRequest_thenPersonIsUpdated()
    {
		Person pExpected = new Person();
    	// GIVEN
    	when(updateService.putPersonRequest(pExpected)).thenReturn(pExpected);
    	// WHEN
    	ResponseEntity<Person> response = endpoint.putPersonRequest(pExpected);
    	// THEN
    	assertEquals(pExpected, response.getBody());
    }

    @Test
    public void givenExistingPerson_whenDeletePersonRequest_thenPersonIsDeleted()
    {
		PersonRequest r = new PersonRequest();
    	// GIVEN
    	when(deleteService.deletePersonRequest(r)).thenReturn(true);
    	// WHEN
    	ResponseEntity<Boolean> response = endpoint.deletePersonRequest(r);
    	// THEN
    	assertEquals(true, response.getBody());
    }

}
