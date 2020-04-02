package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.FloodAddressResponse;
import projets.safetynet.service.DataCreateService;
import projets.safetynet.service.DataReadService;

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
    	// WHEN
		Person pPosted = endpoint.postPersonRequest(pNew);
    	// THEN
    //	assertEquals(expected, response);
    }
}
