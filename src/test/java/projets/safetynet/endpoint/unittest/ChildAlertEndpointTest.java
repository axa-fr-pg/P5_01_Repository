package projets.safetynet.endpoint.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.endpoint.ChildAlertEndpoint;
import projets.safetynet.model.url.ChildAlertResponse;
import projets.safetynet.service.DataReadService;
import projets.safetynet.service.exception.ServerDataCorruptedException;

@SpringBootTest
public class ChildAlertEndpointTest {

	@Autowired
    private ChildAlertEndpoint endpoint;

    @MockBean
    private DataReadService service;
    
    @Test
    public void givenResponse_whenGetChildAlertResponse_thenReturnsExpectedResponse() throws ServerDataCorruptedException
    {
    	// GIVEN
    	ArrayList<ChildAlertResponse> expected = new ArrayList<ChildAlertResponse>();
    	when(service.getChildAlertResponse("12345")).thenReturn(expected);
    	// WHEN
    	ArrayList<ChildAlertResponse> response = endpoint.getChildAlertResponse("12345");
    	// THEN
    	assertEquals(expected, response);
    }

}
