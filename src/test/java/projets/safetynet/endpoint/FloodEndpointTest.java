package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.model.url.FireResponse;
import projets.safetynet.model.url.FloodAddressResponse;
import projets.safetynet.service.DataReadService;

@SpringBootTest
public class FloodEndpointTest {

    @Autowired
    private FloodEndpoint endpoint;

    @MockBean
    private DataReadService service;

    @Test
    public void givenResponse_whenGetFloodByStationResponse_thenReturnsExpectedResponse()
    {
    	// GIVEN
		ArrayList<Long> stations = new ArrayList<Long>(Arrays.asList(1L, 2L, 3L, 4L, 5L));
    	ArrayList<FloodAddressResponse> expected = new ArrayList<FloodAddressResponse>();
    	when(service.getFloodByStationResponse(stations)).thenReturn(expected);
    	// WHEN
    	ArrayList<FloodAddressResponse> response = endpoint.getFloodByStationResponse(stations);
    	// THEN
    	assertEquals(expected, response);
    }

}
