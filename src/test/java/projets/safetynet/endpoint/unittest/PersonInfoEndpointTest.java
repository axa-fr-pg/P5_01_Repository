package projets.safetynet.endpoint.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.dao.exception.PersonNotFoundException;
import projets.safetynet.endpoint.PersonInfoEndpoint;
import projets.safetynet.model.url.PersonInfoResponse;
import projets.safetynet.service.data.DataReadService;
import projets.safetynet.service.exception.ServerDataCorruptedException;

@SpringBootTest
public class PersonInfoEndpointTest {

    @Autowired
    private PersonInfoEndpoint endpoint;

    @MockBean
    private DataReadService service;

    @Test
    public void givenResponse_whenGetPersonInfoResponse_thenReturnsExpectedResponse() throws Exception
    {
    	// GIVEN
    	ArrayList<PersonInfoResponse> expected = new ArrayList<PersonInfoResponse>();
    	when(service.getPersonInfoResponse("firstname", "lastname")).thenReturn(expected);
    	// WHEN
    	ArrayList<PersonInfoResponse> response = endpoint.getPersonInfoResponse("firstname", "lastname");
    	// THEN
    	assertEquals(expected, response);
    }

}
