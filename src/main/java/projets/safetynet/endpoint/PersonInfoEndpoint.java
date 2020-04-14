package projets.safetynet.endpoint;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.dao.exception.PersonNotFoundException;
import projets.safetynet.model.url.PersonInfoResponse;
import projets.safetynet.service.data.DataReadService;
import projets.safetynet.service.exception.ServerDataCorruptedException;
import projets.safetynet.service.util.LogService;

@RestController
@RequestMapping("/personInfo")
public class PersonInfoEndpoint {

	@Autowired 
	private DataReadService service;

	@GetMapping("")
	public ArrayList<PersonInfoResponse> getPersonInfoResponse(
		@RequestParam(value = "firstName", required = false) String firstName,
		@RequestParam(value = "lastName", required = false) String lastName) throws 
		PersonNotFoundException, ServerDataCorruptedException 
	{
        LogService.logger.debug("getPersonInfoResponse() " + firstName + " & " + lastName);
		ArrayList<PersonInfoResponse> response = service.getPersonInfoResponse(firstName, lastName);
		return response;
	}
	
}
