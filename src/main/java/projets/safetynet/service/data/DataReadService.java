package projets.safetynet.service.data;

import java.util.ArrayList;

import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.dao.exception.PersonNotFoundException;
import projets.safetynet.model.url.ChildAlertResponse;
import projets.safetynet.model.url.FireResponse;
import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.model.url.FloodAddressResponse;
import projets.safetynet.model.url.PersonInfoResponse;
import projets.safetynet.service.exception.ServerDataCorruptedException;

public interface DataReadService {

	FireStationResponse getFireStationResponse(long station) throws ServerDataCorruptedException;

	ArrayList<ChildAlertResponse> getChildAlertResponse(String address) throws ServerDataCorruptedException;

	ArrayList<String> getPhoneAlertResponse(long station);

	FireResponse getFireResponse(String address) throws FireStationNotFoundException, ServerDataCorruptedException;

	ArrayList<FloodAddressResponse> getFloodByStationResponse(ArrayList<Long> stations)
			throws FireStationNotFoundException, ServerDataCorruptedException;

	ArrayList<PersonInfoResponse> getPersonInfoResponse(String firstName, String lastName)
			throws PersonNotFoundException, ServerDataCorruptedException;

	ArrayList<String> getCommunityEmailResponse(String city);

}