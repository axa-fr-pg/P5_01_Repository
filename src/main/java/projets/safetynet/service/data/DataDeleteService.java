package projets.safetynet.service.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.dao.exception.MedicalRecordNotFoundException;
import projets.safetynet.dao.exception.PersonNotFoundException;
import projets.safetynet.model.url.PersonRequest;
import projets.safetynet.service.exception.InvalidDeleteFireStationRequestException;
import projets.safetynet.service.exception.ServerDataCorruptedException;

public interface DataDeleteService {

	boolean deletePersonRequest(PersonRequest request) throws PersonNotFoundException, ServerDataCorruptedException;

	boolean deleteFireStationRequest(String request) throws JsonMappingException, JsonProcessingException,
			InvalidDeleteFireStationRequestException, FireStationNotFoundException, ServerDataCorruptedException;

	boolean deleteMedicalRecordRequest(PersonRequest request)
			throws MedicalRecordNotFoundException, ServerDataCorruptedException;

}