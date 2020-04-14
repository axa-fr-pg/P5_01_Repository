package projets.safetynet.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.model.url.FireResponse;
import projets.safetynet.service.data.DataReadService;
import projets.safetynet.service.exception.ServerDataCorruptedException;
import projets.safetynet.service.util.LogService;

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
	
}
