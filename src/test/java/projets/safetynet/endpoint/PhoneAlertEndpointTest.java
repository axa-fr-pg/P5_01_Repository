package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import projets.safetynet.service.DataReadService;

@SpringBootTest
public class PhoneAlertEndpointTest {

    @InjectMocks
    private PhoneAlertEndpoint endpoint;

    @Mock
    private DataReadService service;

    @Test
    public void givenResponse_whenGetPhoneAlertResponse_thenReturnsExpectedResponse()
    {
    	// GIVEN
    	ArrayList<String> expected = new ArrayList<String>();
    	when(service.getPhoneAlertResponse(12345)).thenReturn(expected);
    	// WHEN
    	ArrayList<String> response = endpoint.getPhoneAlertResponse(12345L);
    	// THEN
    	assertEquals(expected, response);
    }

}
