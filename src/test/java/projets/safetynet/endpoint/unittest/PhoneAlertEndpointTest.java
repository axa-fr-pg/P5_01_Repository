package projets.safetynet.endpoint.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.endpoint.PhoneAlertEndpoint;
import projets.safetynet.service.data.DataReadService;

@SpringBootTest
public class PhoneAlertEndpointTest {

    @Autowired
    private PhoneAlertEndpoint endpoint;

    @MockBean
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
