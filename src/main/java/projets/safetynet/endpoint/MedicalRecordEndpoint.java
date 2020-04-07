package projets.safetynet.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.PersonRequest;
import projets.safetynet.service.DataCreateService;
import projets.safetynet.service.DataDeleteService;
import projets.safetynet.service.DataUpdateService;
import projets.safetynet.service.LogService;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordEndpoint {

	@Autowired 
	private DataCreateService createService;

	@Autowired 
	private DataUpdateService updateService;

	@Autowired 
	private DataDeleteService deleteService;

	@PostMapping("")
	public ResponseEntity<MedicalRecord> postMedicalRecordRequest(@RequestBody MedicalRecord mNew) {
        LogService.logger.info("postMedicalRecordRequest() " + mNew.getFirstName() +" & " + mNew.getLastName());
        MedicalRecord response = createService.postMedicalRecordRequest(mNew);
	    return new ResponseEntity<MedicalRecord>(response, HttpStatus.CREATED);
	}

	@PutMapping("")
	public ResponseEntity<MedicalRecord> putMedicalRecordRequest(@RequestBody MedicalRecord mExpected) {
        LogService.logger.info("putMedicalRecordRequest() " + mExpected.getFirstName() +" & " + mExpected.getLastName());
        MedicalRecord response = updateService.putMedicalRecordRequest(mExpected);
	    return new ResponseEntity<MedicalRecord>(response, HttpStatus.OK);
	}

	@DeleteMapping("")
	public ResponseEntity<Boolean> deleteMedicalRecordRequest(@RequestBody PersonRequest r) {
        LogService.logger.info("deleteMedicalRecordRequest() " + r.getFirstName() +" & " + r.getLastName());
		boolean response = deleteService.deleteMedicalRecordRequest(r);
	    return new ResponseEntity<Boolean>(response, HttpStatus.ACCEPTED);
	}

}
