package projets.safetynet.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.service.DataReadService;
import projets.safetynet.service.LogService;

@RestController
@RequestMapping("/firestation")
public class FireStationEndpoint {

	@Autowired 
	private DataReadService service;
	
	@GetMapping("")
	public FireStationResponse getFireStationResponse(@RequestParam(value = "stationNumber") long id) {
		FireStationResponse response = service.getFireStationResponse(id);
		return response;
	}
	
}
