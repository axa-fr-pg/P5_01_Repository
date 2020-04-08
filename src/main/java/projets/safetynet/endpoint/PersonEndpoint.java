package projets.safetynet.endpoint;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.dao.exception.DuplicateFireStationCreationException;
import projets.safetynet.dao.exception.DuplicatePersonCreationException;
import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.dao.exception.MultiplePersonWithSameNameException;
import projets.safetynet.dao.exception.PersonNotFoundException;
import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.PersonRequest;
import projets.safetynet.service.data.DataCreateService;
import projets.safetynet.service.data.DataDeleteService;
import projets.safetynet.service.data.DataUpdateService;
import projets.safetynet.service.exception.ServerDataCorruptedException;
import projets.safetynet.service.util.LogService;

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
			throws ServerDataCorruptedException, DuplicatePersonCreationException
	{
        LogService.logger.debug("postPersonRequest() " + pNew.getFirstName() +" & " + pNew.getLastName());
		Person response = createService.postPersonRequest(pNew);
	    return new ResponseEntity<Person>(response, HttpStatus.CREATED);
	}

	@PutMapping("")
	public ResponseEntity<Person> putPersonRequest( @RequestBody Person pExpected ) 
			throws PersonNotFoundException {
        LogService.logger.debug("putPersonRequest() " + pExpected.getFirstName() +" & " + pExpected.getLastName());
		Person response = updateService.putPersonRequest(pExpected);
	    return new ResponseEntity<Person>(response, HttpStatus.OK);
	}

	@DeleteMapping("")
	public ResponseEntity<Boolean> deletePersonRequest(@RequestBody PersonRequest r) 
			throws PersonNotFoundException, ServerDataCorruptedException {
        LogService.logger.debug("deletePersonRequest() " + r.getFirstName() +" & " + r.getLastName());
		boolean response = deleteService.deletePersonRequest(r);
	    return new ResponseEntity<Boolean>(response, HttpStatus.ACCEPTED);
	}

	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Wrong request !")
	@ExceptionHandler(ServletRequestBindingException.class)	 
	public void badRequest() {
		return;
	}

	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Wrong request !")
	@ExceptionHandler(TypeMismatchException.class)
	public void badParameterType() {
		return;
	}
	
	@ResponseStatus(value=HttpStatus.FORBIDDEN, 
		reason="Person already exists !")
	@ExceptionHandler(DuplicatePersonCreationException.class)
	public void duplicate() {
		return;
	}
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, 
			reason="Data corrupted : fix input file and restart server !")
	@ExceptionHandler(ServerDataCorruptedException.class)
	public void internalServerError() {
		return;
	}

	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Person not found !")
	@ExceptionHandler(PersonNotFoundException.class)
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
