package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;
import projets.safetynet.service.DataCreateService;
import projets.safetynet.service.DataDeleteService;
import projets.safetynet.service.DataUpdateService;

@SpringBootTest
public class MedicalRecordEndpointTest {

    @Autowired
    private MedicalRecordEndpoint endpoint;

    @MockBean
    private DataCreateService createService;

    @MockBean
    private DataUpdateService updateService;

    @MockBean
    private DataDeleteService deleteService;

    @Test
    public void givenNewMedicalRecord_whenPostMedicalRecordRequest_thenMedicalRecordIsCreated()
    {
    	// GIVEN
    	MedicalRecord mNew = new MedicalRecord();
    	when(createService.postMedicalRecordRequest(mNew)).thenReturn(mNew);
    	// WHEN
    	ResponseEntity<MedicalRecord> response = endpoint.postMedicalRecordRequest(mNew);
    	// THEN
    	assertEquals(mNew, response.getBody());
    }
    
    @Test
    public void givenExistingMedicalRecord_whenMedicalRecordPersonRequest_thenMedicalRecordIsUpdated()
    {
    	MedicalRecord mExpected = new MedicalRecord();
    	// GIVEN
    	when(updateService.putMedicalRecordRequest(mExpected)).thenReturn(mExpected);
    	// WHEN
    	ResponseEntity<MedicalRecord> response = endpoint.putMedicalRecordRequest(mExpected);
    	// THEN
    	assertEquals(mExpected, response.getBody());
    }


}
