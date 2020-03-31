package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.service.DataReadService;

@SpringBootTest
public class FireStationEndpointTest {

    @Autowired
    private FireStationEndpoint endpoint;

    @MockBean
    private DataReadService service;

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
