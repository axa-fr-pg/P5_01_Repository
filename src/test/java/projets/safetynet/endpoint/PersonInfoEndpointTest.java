package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.model.url.PersonInfoResponse;
import projets.safetynet.service.DataReadService;

@SpringBootTest
public class PersonInfoEndpointTest {

    @Autowired
    private PersonInfoEndpoint endpoint;

    @MockBean
    private DataReadService service;

    @Test
    public void givenResponse_whenGetPersonInfoResponse_thenReturnsExpectedResponse()
    {
    	// GIVEN
    	PersonInfoResponse expected = new PersonInfoResponse();
    	when(service.getPersonInfoResponse("firstname", "lastname")).thenReturn(expected);
    	// WHEN
    	PersonInfoResponse response = endpoint.getPersonInfoResponse("firstname", "lastname");
    	// THEN
    	assertEquals(expected, response);
    }

}
