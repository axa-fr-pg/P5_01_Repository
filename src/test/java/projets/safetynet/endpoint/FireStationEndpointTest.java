package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.service.DataReadService;

@SpringBootTest
public class FireStationEndpointTest {

    @InjectMocks
    private FireStationEndpoint endpoint;

    @Mock
    private DataReadService service;

	@BeforeTestClass
	private void initMocks()
	{
		MockitoAnnotations.initMocks(this);
	}

    @Test
    public void givenResponse_whenGetFireStationResponse_thenReturnsExpectedResponse()
    {
    	// GIVEN
    	FireStationResponse expected = new FireStationResponse();
    	when(service.getFireStationResponse(12345)).thenReturn(expected);
    	// WHEN
    	FireStationResponse response = endpoint.getFireStationResponse(12345);
    	// THEN
    	assertEquals(expected, response);
    }

}
