package projets.safetynet.endpoint.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.endpoint.FireEndpoint;
import projets.safetynet.model.url.FireResponse;
import projets.safetynet.service.data.DataReadService;
import projets.safetynet.service.exception.ServerDataCorruptedException;

@SpringBootTest
public class FireEndpointTest {

    @Autowired
    private FireEndpoint endpoint;

    @MockBean
    private DataReadService readService;

    @Test
    public void givenResponse_whenGetFireResponse_thenReturnsExpectedResponse() throws Exception
    {
    	// GIVEN
    	FireResponse expected = new FireResponse();
    	when(readService.getFireResponse("test address")).thenReturn(expected);
    	// WHEN
    	FireResponse response = endpoint.getFireResponse("test address");
    	// THEN
    	assertEquals(expected, response);
    }

}
