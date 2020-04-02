package projets.safetynet.endpoint;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import projets.safetynet.model.core.Person;
import projets.safetynet.service.DataCreateService;
import projets.safetynet.service.LogService;

@RestController
@RequestMapping("/person")
public class PersonEndpoint {

	@Autowired 
	private DataCreateService service;

	@PostMapping("")
	public ResponseEntity<Person> postPersonRequest( @RequestBody Person pNew )
	{
        LogService.logger.info("postPersonRequest() " + pNew.getFirstName() +" & " + pNew.getLastName());
		Person response = service.postPersonRequest(pNew);
	    return new ResponseEntity<Person>(response, HttpStatus.CREATED);
	}

}
