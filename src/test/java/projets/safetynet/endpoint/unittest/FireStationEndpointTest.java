package projets.safetynet.endpoint.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import projets.safetynet.endpoint.FireStationEndpoint;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.service.data.DataCreateService;
import projets.safetynet.service.data.DataDeleteService;
import projets.safetynet.service.data.DataReadService;
import projets.safetynet.service.data.DataUpdateService;
import projets.safetynet.service.exception.InvalidDeleteFireStationRequestException;

@SpringBootTest
public class FireStationEndpointTest {

    @Autowired
    private FireStationEndpoint endpoint;

    @MockBean
    private DataReadService readService;

    @MockBean
    private DataCreateService createService;

    @MockBean
    private DataUpdateService updateService;

    @MockBean
    private DataDeleteService deleteService;

    @Test
    public void givenResponse_whenGetFireStationResponse_thenReturnsExpectedResponse() throws Exception
    {
    	// GIVEN
    	FireStationResponse expected = new FireStationResponse();
    	when(readService.getFireStationResponse(12345)).thenReturn(expected);
    	// WHEN
    	FireStationResponse response = endpoint.getFireStationResponse(12345);
    	// THEN
    	assertEquals(expected, response);
    }

    @Test
    public void givenNewFireStation_whenPostFireStationRequest_thenFireStationIsCreated() throws Exception
    {
    	// GIVEN
		FireStation sNew = new FireStation();
    	when(createService.postFireStationRequest(sNew)).thenReturn(sNew);
    	// WHEN
    	ResponseEntity<FireStation> response = endpoint.postFireStationRequest(sNew);
    	// THEN
    	assertEquals(sNew, response.getBody());
    }

    @Test
    public void givenExistingFireStation_whenPutFireStationRequest_thenFireStationIsUpdated() throws Exception
    {
    	// GIVEN
		FireStation sNew = new FireStation();
    	when(updateService.putFireStationRequest(sNew)).thenReturn(sNew);
    	// WHEN
    	ResponseEntity<FireStation> response = endpoint.putFireStationRequest(sNew);
    	// THEN
    	assertEquals(sNew, response.getBody());
    }

    @Test
    public void givenExistingFireStation_whenDeleteFireStationRequest_thenFireStationIsDeleted() throws Exception
    {
    	// GIVEN
    	when(deleteService.deleteFireStationRequest("request")).thenReturn(true);
    	// WHEN
    	ResponseEntity<Boolean> response = endpoint.deleteFireStationRequest("request");
    	// THEN
    	assertEquals(true, response.getBody());
    }

    @Test
    public void givenInvalidRequest_whenDeleteFireStationRequest_thenReturnsBadRequest() throws Exception
    {
    	// GIVEN
    	when(deleteService.deleteFireStationRequest("invalid request")).thenThrow(
    			new InvalidDeleteFireStationRequestException() );
    	// WHEN
    	ResponseEntity<Boolean> response = endpoint.deleteFireStationRequest("invalid request");
    	// THEN
    	assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
