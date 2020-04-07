package projets.safetynet.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.dao.DuplicateFireStationCreationException;
import projets.safetynet.dao.FireStationNotFoundException;
import projets.safetynet.model.url.FireResponse;
import projets.safetynet.service.DataReadService;
import projets.safetynet.service.LogService;
import projets.safetynet.service.ServerDataCorruptedException;

@RestController
@RequestMapping("/fire")
public class FireEndpoint {

	@Autowired 
	private DataReadService service;

	@GetMapping("")
	public FireResponse getFireResponse(@RequestParam(value = "address") String address) 
			throws ServerDataCorruptedException, FireStationNotFoundException {
	    LogService.logger.debug("getFireResponse() " + address);
		FireResponse response = service.getFireResponse(address);
		return response;
	}
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, 
			reason="Data corrupted : fix input file and restart server !")
	@ExceptionHandler(ServerDataCorruptedException.class)
	public void internalServerError() {
		return;
	}

	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No FireStation defined for the given address !")
	@ExceptionHandler(FireStationNotFoundException.class)
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
