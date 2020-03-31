package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import projets.safetynet.model.url.ChildAlertResponse;
import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.service.DataReadService;

@SpringBootTest
public class ChildAlertEndpointTest {

    @InjectMocks
    private ChildAlertEndpoint endpoint;
    
    @Mock
    private DataReadService service;

    @Test
    public void givenResponse_whenGetChildAlertResponse_thenReturnsExpectedResponse()
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
