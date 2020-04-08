package projets.safetynet.endpoint;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
