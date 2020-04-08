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

import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.model.url.ChildAlertResponse;
import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.service.data.DataReadService;
import projets.safetynet.service.exception.ServerDataCorruptedException;
import projets.safetynet.service.util.LogService;

@RestController
@RequestMapping("/childAlert")
public class ChildAlertEndpoint {

	@Autowired 
	private DataReadService service;

	@GetMapping("")
	public ArrayList<ChildAlertResponse> getChildAlertResponse(@RequestParam(value = "address") String address) 
			throws ServerDataCorruptedException {
	    LogService.logger.debug("getChildAlertResponse() " + address);
		ArrayList<ChildAlertResponse> response = service.getChildAlertResponse(address);
		return response;
	}

	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, 
			reason="Data corrupted : fix input file and restart server !")
	@ExceptionHandler(ServerDataCorruptedException.class)
	public void internalServerError() {
		return;
	}

	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, 
			reason="Unknown error : revert to IT for investigation !")
	@ExceptionHandler(Exception.class)
	public void unknownError() {
		return;
	}

}
