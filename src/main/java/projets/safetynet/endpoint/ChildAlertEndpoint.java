package projets.safetynet.endpoint;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.model.url.ChildAlertResponse;
import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.service.DataReadService;
import projets.safetynet.service.LogService;

@RestController
@RequestMapping("/childAlert")
public class ChildAlertEndpoint {

	@Autowired 
	private DataReadService service;

	@GetMapping("")
	public ArrayList<ChildAlertResponse> getChildAlertResponse(@RequestParam(value = "address") String address) {
	    LogService.logger.debug("getChildAlertResponse() " + address);
		ArrayList<ChildAlertResponse> response = service.getChildAlertResponse(address);
		return response;
	}

}
