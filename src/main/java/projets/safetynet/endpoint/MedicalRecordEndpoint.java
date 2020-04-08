package projets.safetynet.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.dao.exception.DuplicateFireStationCreationException;
import projets.safetynet.dao.exception.DuplicateMedicalRecordCreationException;
import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.dao.exception.MedicalRecordNotFoundException;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.url.PersonRequest;
import projets.safetynet.service.data.DataCreateService;
import projets.safetynet.service.data.DataDeleteService;
import projets.safetynet.service.data.DataUpdateService;
import projets.safetynet.service.exception.ServerDataCorruptedException;
import projets.safetynet.service.util.LogService;

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
	public ResponseEntity<MedicalRecord> postMedicalRecordRequest(@RequestBody MedicalRecord mNew) 
			throws ServerDataCorruptedException, DuplicateMedicalRecordCreationException {
        LogService.logger.debug("postMedicalRecordRequest() " + mNew.getFirstName() +" & " + mNew.getLastName());
        MedicalRecord response = createService.postMedicalRecordRequest(mNew);
	    return new ResponseEntity<MedicalRecord>(response, HttpStatus.CREATED);
	}

	@PutMapping("")
	public ResponseEntity<MedicalRecord> putMedicalRecordRequest(@RequestBody MedicalRecord mExpected) 
			throws MedicalRecordNotFoundException {
        LogService.logger.debug("putMedicalRecordRequest() " + mExpected.getFirstName() +" & " + mExpected.getLastName());
        MedicalRecord response = updateService.putMedicalRecordRequest(mExpected);
	    return new ResponseEntity<MedicalRecord>(response, HttpStatus.OK);
	}

	@DeleteMapping("")
	public ResponseEntity<Boolean> deleteMedicalRecordRequest(@RequestBody PersonRequest r) 
			throws MedicalRecordNotFoundException, ServerDataCorruptedException {
        LogService.logger.debug("deleteMedicalRecordRequest() " + r.getFirstName() +" & " + r.getLastName());
		boolean response = deleteService.deleteMedicalRecordRequest(r);
	    return new ResponseEntity<Boolean>(response, HttpStatus.ACCEPTED);
	}

	@ResponseStatus(value=HttpStatus.FORBIDDEN, 
		reason="MedicalRecord already exists !")
	@ExceptionHandler(DuplicateMedicalRecordCreationException.class)
	public void duplicate() {
		return;
	}
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, 
			reason="Data corrupted : fix input file and restart server !")
	@ExceptionHandler(ServerDataCorruptedException.class)
	public void internalServerError() {
		return;
	}

	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="MedicalRecord not found !")
	@ExceptionHandler(MedicalRecordNotFoundException.class)
	public void notFound() {
		return;
	}

	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, 
			reason="Unknown error : revert to IT for investigation !")
	@ExceptionHandler(Exception.class)
	public void unknownError() {
		return;
	}
		
}
