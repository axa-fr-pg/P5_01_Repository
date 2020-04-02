package projets.safetynet.endpoint;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.model.url.PersonInfoResponse;
import projets.safetynet.service.DataReadService;

@RestController
@RequestMapping("/personInfo")
public class PersonInfoEndpoint {

	@Autowired 
	private DataReadService service;

	@GetMapping("")
	public PersonInfoResponse getPersonInfoResponse(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName) 
	{
		PersonInfoResponse response = service.getPersonInfoResponse(firstName, lastName);
		return response;
	}

}
