package projets.safetynet.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.PersonInfoResponse;
import projets.safetynet.service.DataCreateService;
import projets.safetynet.service.DataReadService;

@RestController
@RequestMapping("/person")
public class PersonEndpoint {

	@Autowired 
	private DataCreateService service;

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Person postPersonRequest(@RequestBody Person pNew)	
	{
		Person response = service.postPersonRequest(pNew);
		return response;
	}

}
