package projets.safetynet.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.FireStationNotFoundException;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.MedicalRecordNotFoundException;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.PersonNotFoundException;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.ChildAlertResponse;
import projets.safetynet.model.url.FirePersonResponse;
import projets.safetynet.model.url.FireResponse;
import projets.safetynet.model.url.FireStationPersonResponse;
import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.model.url.FloodAddressResponse;
import projets.safetynet.model.url.PersonInfoResponse;

@Service
public class DataReadService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private FireStationDao stationDao;

	@Autowired
	private MedicalRecordDao recordDao;

	public FireStationResponse getFireStationResponse(long station) {
	
        LogService.logger.info("getFireStationResponse() " + station);
        ArrayList<FireStationPersonResponse> responsePersons = new ArrayList<FireStationPersonResponse>();
		long adults = 0;
		long children = 0;

		ArrayList<FireStation> stations = stationDao.getByStation(station);
		for (FireStation s : stations) {
			ArrayList<Person> persons = personDao.getByAddress(s.getAddress());
			for (Person p : persons) {
				FireStationPersonResponse responsePerson = new FireStationPersonResponse(p);
				responsePersons.add(responsePerson); 

				MedicalRecord r = null;
				long age = 0;

				try {
					r = recordDao.get(p.getFirstName(), p.getLastName());
					age = r.getAge();
					if (age < 19) children ++;
					else adults ++;
				} catch (MedicalRecordNotFoundException e) {
				       LogService.logger.error("getFireStationResponse() throws MedicalRecordNotFoundException"); 
				}
			}
		}

		FireStationResponse response = new FireStationResponse(responsePersons, adults, children);
        LogService.logger.info("getFireStationResponse() returns " + responsePersons.size() + " persons, " 
                + adults + " adults, " + children + " children");
		return response;
	}

    public ArrayList<ChildAlertResponse> getChildAlertResponse(String address) {
        LogService.logger.info("getChildAlertResponse() " + address);
        ArrayList<ChildAlertResponse> response = new ArrayList<ChildAlertResponse>();
        ArrayList<Person> persons = personDao.getByAddress(address);
        for (Person p : persons) {
            try {
				MedicalRecord m = recordDao.get(p.getFirstName(), p.getLastName());
				if (m.getAge() > 18) continue;
				ArrayList<String> mates = new ArrayList<String>();
				for (Person x : persons) {
					if ( (! p.getFirstName().equals(x.getFirstName())) || ((! p.getLastName().equals(x.getLastName()))) )
					{
						mates.add(x.getFirstName() + " " + x.getLastName());
					}
				}
				ChildAlertResponse c = new ChildAlertResponse(p.getFirstName(), p.getLastName(), m.getAge(), mates);
				response.add(c);
			} catch (MedicalRecordNotFoundException e) {
		        LogService.logger.error("getChildAlertResponse() throws MedicalRecordNotFoundException");
			}
        }
        LogService.logger.info("getChildAlertResponse() returns " + response.size() + " children");
        return response;
    }

	public ArrayList<String> getPhoneAlertResponse(long station) {
        LogService.logger.info("getPhoneAlertResponse() " + station);
        ArrayList<String> response = new ArrayList<String>();

		ArrayList<FireStation> stations = stationDao.getByStation(station);
		for (FireStation s : stations) {
			ArrayList<Person> persons = personDao.getByAddress(s.getAddress());
			for (Person p : persons) response.add(p.getPhone());
		}
        LogService.logger.info("getPhoneAlertResponse() returns " + response.size() + " phone numbers");
		return response;
	}

	public FireResponse getFireResponse(String address) {
        LogService.logger.info("getFireResponse() " + address);
        FireResponse response = new FireResponse();
        try {
            FireStation station = stationDao.getByAddress(address);
            response.setStation(station.getStation());
        } catch (FireStationNotFoundException e) {
	        LogService.logger.error("getFireResponse() throws FireStationNotFoundException");
	        return null;
        }
        ArrayList<FirePersonResponse> inhabitants = new ArrayList<FirePersonResponse>();
        ArrayList<Person> persons = personDao.getByAddress(address);
        for (Person p : persons) {
            try {
				MedicalRecord m = recordDao.get(p.getFirstName(), p.getLastName());
				FirePersonResponse i = new FirePersonResponse( p.getFirstName() + " " + p.getLastName(),
						p.getPhone(), m.getAge(), m.getMedications(), m.getAllergies());
				inhabitants.add(i);
			} catch (MedicalRecordNotFoundException e) {
		        LogService.logger.error("getFireResponse() throws MedicalRecordNotFoundException");
			}
        }
        response.setInhabitants(inhabitants);
        LogService.logger.info("getFireResponse() returns " + inhabitants.size() + " inhabitants station " + response.getStation());
        return response;
	}

	public ArrayList<FloodAddressResponse> getFloodByStationResponse(ArrayList<Long> stations) {
        LogService.logger.info("getFloodByStationResponse() " + stations.size() + " stations");
        ArrayList<FloodAddressResponse> response = new ArrayList<FloodAddressResponse>();
		for (long s : stations) {
			ArrayList<FireStation> stationMapList = stationDao.getByStation(s);
			for (FireStation f : stationMapList) {
				FireResponse fireResponse = getFireResponse(f.getAddress());
				FloodAddressResponse floodAddress = new FloodAddressResponse(f.getAddress(), fireResponse.getInhabitants());
				response.add(floodAddress);
			}
		}
        LogService.logger.info("getFloodByStationResponse() returns " + response.size() + " addresses");
        return response;
	}

	public PersonInfoResponse getPersonInfoResponse(String firstName, String lastName) {
        LogService.logger.info("getPersonInfoResponse() " + firstName + " " + lastName);
        PersonInfoResponse response = new PersonInfoResponse(firstName + " " + lastName, "", 0, "",
        		new String[] {}, new String[] {});
		try {
			Person p = personDao.get(firstName, lastName);
			response.setAddress(p.getAddress());
			response.setEmail(p.getEmail());
		} catch (PersonNotFoundException e) {
	        LogService.logger.error("getPersonInfoResponse() throws PersonNotFoundException");
		}
		try {
			MedicalRecord r = recordDao.get(firstName, lastName);
			response.setAge(r.getAge());
			response.setMedications(r.getMedications());
			response.setAllergies(r.getAllergies());
		} catch (MedicalRecordNotFoundException e) {
		       LogService.logger.error("getPersonInfoResponse() throws MedicalRecordNotFoundException"); 
		}
        LogService.logger.info("getPersonInfoResponse() successful");
		return response;
	}
}
