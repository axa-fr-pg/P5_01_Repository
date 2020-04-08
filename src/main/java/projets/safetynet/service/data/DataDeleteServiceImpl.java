package projets.safetynet.service.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.dao.exception.MedicalRecordNotFoundException;
import projets.safetynet.dao.exception.MultipleFireStationWithSameValuesException;
import projets.safetynet.dao.exception.MultipleMedicalRecordWithSameNameException;
import projets.safetynet.dao.exception.MultiplePersonWithSameNameException;
import projets.safetynet.dao.exception.PersonNotFoundException;
import projets.safetynet.model.url.PersonRequest;
import projets.safetynet.service.exception.InvalidDeleteFireStationRequestException;
import projets.safetynet.service.exception.ServerDataCorruptedException;
import projets.safetynet.service.util.LogService;

@Service
public class DataDeleteServiceImpl implements DataDeleteService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private FireStationDao stationDao;

	@Autowired
	private MedicalRecordDao recordDao;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public boolean deletePersonRequest(PersonRequest request) throws PersonNotFoundException, ServerDataCorruptedException {
        LogService.logger.debug("deletePersonRequest() " + request.getFirstName() + " " + request.getLastName());
		boolean result;
		try {
			result = personDao.delete(request.getFirstName(), request.getLastName());
		} catch (MultiplePersonWithSameNameException e) {
			LogService.logger.error("deletePersonRequest() throws ServerDataCorruptedException");
			throw new ServerDataCorruptedException();
		}
		return result;
	}

	@Override
	public boolean deleteFireStationRequest(String request) throws JsonMappingException,
			JsonProcessingException, InvalidDeleteFireStationRequestException, FireStationNotFoundException, ServerDataCorruptedException 
	{
        LogService.logger.debug("deleteFireStationRequest() " + request);
        JsonNode givenValues = objectMapper.readTree(request);
        JsonNode givenAddress = givenValues.get("address");
        JsonNode givenStation = givenValues.get("station");
        if (givenAddress != null) {
        	String address = givenAddress.asText();
        	if (givenStation == null) return stationDao.deleteByAddress(address);
        	long station = givenStation.asLong();
        	try {
				return stationDao.delete(address, station);
			} catch (MultipleFireStationWithSameValuesException e) {
				LogService.logger.error("deleteFireStationRequest() throws ServerDataCorruptedException");
				throw new ServerDataCorruptedException();
			}
        }
		if (givenStation != null) {
			long station = givenStation.asLong();
			return stationDao.deleteByStation(station);
		}
        LogService.logger.error("deleteFireStationRequest() throws InvalidDeleteFireStationRequestException");
       	throw new InvalidDeleteFireStationRequestException();
	}

	@Override
	public boolean deleteMedicalRecordRequest(PersonRequest request) 
			throws MedicalRecordNotFoundException, ServerDataCorruptedException {
        LogService.logger.debug("deleteMedicalRecordRequest() " + request.getFirstName() + " " + request.getLastName());
		boolean result;
		try {
			result = recordDao.delete(request.getFirstName(), request.getLastName());
		} catch (MultipleMedicalRecordWithSameNameException e) {
			LogService.logger.error("deleteMedicalRecordRequest() throws ServerDataCorruptedException");
			throw new ServerDataCorruptedException();
		}
		return result;
	}

}
