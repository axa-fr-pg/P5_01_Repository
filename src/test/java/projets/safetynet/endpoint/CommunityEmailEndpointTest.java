package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.model.url.ChildAlertResponse;
import projets.safetynet.service.DataReadService;

@SpringBootTest
public class CommunityEmailEndpointTest {

	@Autowired
    private CommunityEmailEndpoint endpoint;

    @MockBean
    private DataReadService service;
    
    @Test
    public void givenResponse_whenCommunityEmailResponse_thenReturnsExpectedResponse()
    {
    	// GIVEN
    	ArrayList<String> expected = new ArrayList<String>();
    	when(service.getCommunityEmailResponse("my city")).thenReturn(expected);
    	// WHEN
    	ArrayList<String> response = endpoint.getCommunityEmailResponse("my city");
    	// THEN
    	assertEquals(expected, response);
    }

}
