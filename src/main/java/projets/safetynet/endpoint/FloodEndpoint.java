package projets.safetynet.endpoint;

import java.util.ArrayList;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.model.url.FloodAddressResponse;
import projets.safetynet.service.data.DataReadService;
import projets.safetynet.service.exception.ServerDataCorruptedException;
import projets.safetynet.service.util.LogService;

@RestController
@RequestMapping("/flood")
public class FloodEndpoint {

	@Autowired 
	private DataReadService service;

	@GetMapping("/stations")
	public ArrayList<FloodAddressResponse> getFloodByStationResponse(@RequestParam(value = "stations") ArrayList<Long> stations) 
			throws FireStationNotFoundException, ServerDataCorruptedException {
        LogService.logger.debug("getFloodByStationResponse() elements:" + stations.size());
		ArrayList<FloodAddressResponse> response = service.getFloodByStationResponse(stations);
		return response;
	}

}
