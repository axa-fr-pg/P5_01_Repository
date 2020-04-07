package projets.safetynet.endpoint;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.service.DataReadService;
import projets.safetynet.service.LogService;

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

}