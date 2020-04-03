package projets.safetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.PersonNotFoundException;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.url.PersonRequest;

@Service
public class DataDeleteService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private FireStationDao stationDao;

	@Autowired
	private ObjectMapper objectMapper;

	public boolean deletePersonRequest(PersonRequest request) {
        LogService.logger.info("deletePersonRequest() " + request.getFirstName() + " " + request.getLastName());
		boolean result = personDao.delete(request.getFirstName(), request.getLastName());
		return result;
	}

	public boolean deleteFireStationRequest(String request) throws JsonMappingException,
			JsonProcessingException, InvalidDeleteFireStationRequestException 
	{
        LogService.logger.info("deleteFireStationRequest() " + request);
        JsonNode givenValues = objectMapper.readTree(request);
        JsonNode givenAddress = givenValues.get("address");
        JsonNode givenStation = givenValues.get("station");
        if (givenAddress != null) {
        	String address = givenAddress.asText();
        	if (givenStation == null) return stationDao.deleteByAddress(address);
        	long station = givenStation.asLong();
        	return stationDao.delete(address, station);
        }
		if (givenStation != null) {
			long station = givenStation.asLong();
			return stationDao.deleteByStation(station);
		}
        LogService.logger.error("deleteFireStationRequest() throws InvalidDeleteFireStationRequestException");
       	throw new InvalidDeleteFireStationRequestException();
	}

}
