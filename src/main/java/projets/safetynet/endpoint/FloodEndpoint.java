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
        LogService.logger.debug("getFloodByStationResponse() list size " + stations.size());
		ArrayList<FloodAddressResponse> response = service.getFloodByStationResponse(stations);
		return response;
	}

	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Wrong request !")
	@ExceptionHandler(ServletRequestBindingException.class)	 
	public void badRequest() {
		LogService.logger.error("badRequest() ServletRequestBindingException");
		return;
	}

	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Wrong request !")
	@ExceptionHandler(TypeMismatchException.class)
	public void badParameterType() {
		LogService.logger.error("badParameterType() TypeMismatchException");
		return;
	}

	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, 
			reason="Data corrupted : fix input file and restart server !")
	@ExceptionHandler(ServerDataCorruptedException.class)
	public void dataCorrupted() {
		LogService.logger.error("dataCorrupted() ServerDataCorruptedException");
		return;
	}

	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="At least one of the given firestations was not found !")
	@ExceptionHandler(FireStationNotFoundException.class)
	public void notFound() {
		LogService.logger.error("notFound() FireStationNotFoundException");
		return;
	}

	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, 
			reason="Unknown error : revert to IT for investigation !")
	@ExceptionHandler(Exception.class)
	public void unknownError() {
		LogService.logger.error("unknownError() Exception");
		return;
	}
}
