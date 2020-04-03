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
import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.PersonRequest;
import projets.safetynet.service.DataCreateService;
import projets.safetynet.service.DataDeleteService;
import projets.safetynet.service.DataUpdateService;
import projets.safetynet.service.LogService;

@RestController
@RequestMapping("/person")
public class PersonEndpoint {

	@Autowired 
	private DataCreateService createService;

	@Autowired 
	private DataUpdateService updateService;

	@Autowired 
	private DataDeleteService deleteService;

	@PostMapping("")
	public ResponseEntity<Person> postPersonRequest( @RequestBody Person pNew )
	{
        LogService.logger.info("postPersonRequest() " + pNew.getFirstName() +" & " + pNew.getLastName());
		Person response = createService.postPersonRequest(pNew);
	    return new ResponseEntity<Person>(response, HttpStatus.CREATED);
	}

	@PutMapping("")
	public ResponseEntity<Person> putPersonRequest( @RequestBody Person pExpected ) {
        LogService.logger.info("putPersonRequest() " + pExpected.getFirstName() +" & " + pExpected.getLastName());
		Person response = updateService.putPersonRequest(pExpected);
	    return new ResponseEntity<Person>(response, HttpStatus.OK);
	}

	@DeleteMapping("")
	public ResponseEntity<Boolean> deletePersonRequest(@RequestBody PersonRequest r) {
        LogService.logger.info("deletePersonRequest() " + r.getFirstName() +" & " + r.getLastName());
		boolean response = deleteService.deletePersonRequest(r);
	    return new ResponseEntity<Boolean>(response, HttpStatus.ACCEPTED);
	}

}
