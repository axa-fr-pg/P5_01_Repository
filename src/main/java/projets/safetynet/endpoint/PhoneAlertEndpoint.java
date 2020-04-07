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

import projets.safetynet.dao.DuplicateFireStationCreationException;
import projets.safetynet.dao.FireStationNotFoundException;
import projets.safetynet.service.DataReadService;
import projets.safetynet.service.LogService;
import projets.safetynet.service.ServerDataCorruptedException;

@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertEndpoint {

	@Autowired 
	private DataReadService service;

	@GetMapping("")
	public ArrayList<String> getPhoneAlertResponse(@RequestParam(value = "firestation") long station) {
        LogService.logger.debug("getPhoneAlertResponse() " + station);
		ArrayList<String> response = service.getPhoneAlertResponse(station);
		return response;
	}

	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, 
			reason="Unknown error : revert to IT for investigation !")
	@ExceptionHandler(Exception.class)
	public void unknownError() {
		return;
	}
	
}