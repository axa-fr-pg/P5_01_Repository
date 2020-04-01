package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.model.url.FireResponse;
import projets.safetynet.service.DataReadService;

@SpringBootTest
public class FireEndpointTest {

    @Autowired
    private FireEndpoint endpoint;

    @MockBean
    private DataReadService service;

    @Test
    public void givenResponse_whenGetFireResponse_thenReturnsExpectedResponse()
    {
    	// GIVEN
    	FireResponse expected = new FireResponse();
    	when(service.getFireResponse("12345")).thenReturn(expected);
    	// WHEN
    	FireResponse response = endpoint.getFireResponse("12345");
    	// THEN
    	assertEquals(expected, response);
    }
	
}
