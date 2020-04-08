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

import projets.safetynet.model.url.ChildAlertResponse;
import projets.safetynet.service.data.DataReadService;
import projets.safetynet.service.exception.ServerDataCorruptedException;
import projets.safetynet.service.util.LogService;

@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailEndpoint {

	@Autowired 
	private DataReadService service;

	@GetMapping("")
	public ArrayList<String> getCommunityEmailResponse(@RequestParam(value = "city") String city) {
	    LogService.logger.debug("getCommunityEmailResponse() " + city);
		ArrayList<String> response = service.getCommunityEmailResponse(city);
		return response;
	}

	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, 
			reason="Unknown error : revert to IT for investigation !")
	@ExceptionHandler(Exception.class)
	public void unknownError() {
		return;
	}

}
