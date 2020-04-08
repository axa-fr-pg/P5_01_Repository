package projets.safetynet.service.data;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.dao.exception.MedicalRecordNotFoundException;
import projets.safetynet.dao.exception.MultipleMedicalRecordWithSameNameException;
import projets.safetynet.dao.exception.MultiplePersonWithSameNameException;
import projets.safetynet.dao.exception.PersonNotFoundException;
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
import projets.safetynet.service.exception.ServerDataCorruptedException;
import projets.safetynet.service.util.LogService;

@Service
public class DataReadServiceImpl implements DataReadService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private FireStationDao stationDao;

	@Autowired
	private MedicalRecordDao recordDao;

	@Override
	public FireStationResponse getFireStationResponse(long station) throws ServerDataCorruptedException {
	
        LogService.logger.debug("getFireStationResponse() " + station);
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
				} catch (MedicalRecordNotFoundException | MultipleMedicalRecordWithSameNameException e ) {
				       LogService.logger.error("getFireStationResponse() throws ServerDataCorruptedException"); 
				       throw new ServerDataCorruptedException();
				}
			}
		}

		FireStationResponse response = new FireStationResponse(responsePersons, adults, children);
        LogService.logger.debug("getFireStationResponse() returns " + responsePersons.size() + " persons, " 
                + adults + " adults, " + children + " children");
		return response;
	}

    @Override
	public ArrayList<ChildAlertResponse> getChildAlertResponse(String address) throws ServerDataCorruptedException {
        LogService.logger.debug("getChildAlertResponse() " + address);
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
			} catch (MedicalRecordNotFoundException | MultipleMedicalRecordWithSameNameException e) {
		        LogService.logger.error("getChildAlertResponse() throws ServerDataCorruptedException");
		        throw new ServerDataCorruptedException();
			}
        }
        LogService.logger.debug("getChildAlertResponse() returns " + response.size() + " children");
        return response;
    }

	@Override
	public ArrayList<String> getPhoneAlertResponse(long station) {
        LogService.logger.debug("getPhoneAlertResponse() " + station);
        ArrayList<String> response = new ArrayList<String>();

		ArrayList<FireStation> stations = stationDao.getByStation(station);
		for (FireStation s : stations) {
			ArrayList<Person> persons = personDao.getByAddress(s.getAddress());
			for (Person p : persons) response.add(p.getPhone());
		}
        LogService.logger.debug("getPhoneAlertResponse() returns " + response.size() + " phone numbers");
		return response;
	}

	@Override
	public FireResponse getFireResponse(String address) throws FireStationNotFoundException, ServerDataCorruptedException {
        LogService.logger.debug("getFireResponse() " + address);
        FireResponse response = new FireResponse();
        FireStation station = stationDao.getByAddress(address);
        response.setStation(station.getStation());
        ArrayList<FirePersonResponse> inhabitants = new ArrayList<FirePersonResponse>();
        ArrayList<Person> persons = personDao.getByAddress(address);
        for (Person p : persons) {
            try {
				MedicalRecord m = recordDao.get(p.getFirstName(), p.getLastName());
				FirePersonResponse i = new FirePersonResponse( p.getFirstName() + " " + p.getLastName(),
						p.getPhone(), m.getAge(), m.getMedications(), m.getAllergies());
				inhabitants.add(i);
			} catch (MedicalRecordNotFoundException | MultipleMedicalRecordWithSameNameException e) {
		        LogService.logger.error("getFireResponse() throws ServerDataCorruptedException");
		        throw new ServerDataCorruptedException();
			}
        }
        response.setInhabitants(inhabitants);
        LogService.logger.debug("getFireResponse() returns " + inhabitants.size() + " inhabitants station " + response.getStation());
        return response;
	}

	@Override
	public ArrayList<FloodAddressResponse> getFloodByStationResponse(ArrayList<Long> stations) throws FireStationNotFoundException, ServerDataCorruptedException {
        LogService.logger.debug("getFloodByStationResponse() " + stations.size() + " stations");
        ArrayList<FloodAddressResponse> response = new ArrayList<FloodAddressResponse>();
		for (long s : stations) {
			ArrayList<FireStation> stationMapList = stationDao.getByStation(s);
			for (FireStation f : stationMapList) {
				FireResponse fireResponse = getFireResponse(f.getAddress());
				FloodAddressResponse floodAddress = new FloodAddressResponse(f.getAddress(), fireResponse.getInhabitants());
				response.add(floodAddress);
			}
		}
        LogService.logger.debug("getFloodByStationResponse() returns " + response.size() + " addresses");
        return response;
	}

	@Override
	public ArrayList<PersonInfoResponse> getPersonInfoResponse(String firstName, String lastName) throws PersonNotFoundException, ServerDataCorruptedException {
		
		LogService.logger.debug("getPersonInfoResponse() FirstName=" + firstName + " LastName=" + lastName);
        ArrayList<Person> persons = null;
		if (firstName == null) {
			persons = personDao.getByLastName(lastName);
		}
		if (lastName == null) {
			persons = personDao.getByFirstName(firstName);
		}
		if (persons == null) {
			try {
				persons = new ArrayList<Person>();
				Person p = personDao.get(firstName, lastName);
				persons.add(p);
			} catch (MultiplePersonWithSameNameException e) {
		        LogService.logger.error("getPersonInfoResponse() throws ServerDataCorruptedException");
		        throw new ServerDataCorruptedException();
			}
		}
		
        ArrayList<PersonInfoResponse> responsePersons = new ArrayList<PersonInfoResponse>();
        for (Person p : persons) {
        	MedicalRecord r = null;
    		try {
    			r = recordDao.get(p.getFirstName(), p.getLastName());
        		PersonInfoResponse rp = new PersonInfoResponse(p.getFirstName() + " " + p.getLastName(),
        				p.getAddress(), r.getAge(), p.getEmail(), r.getMedications(), r.getAllergies());
                responsePersons.add(rp);
    		} catch (MedicalRecordNotFoundException | MultipleMedicalRecordWithSameNameException e) {
    		       LogService.logger.error("getPersonInfoResponse() throws ServerDataCorruptedException");
    		       throw new ServerDataCorruptedException();
    		}
        }
        LogService.logger.debug("getPersonInfoResponse() successful");
		return responsePersons;
	}
	
    @Override
	public ArrayList<String> getCommunityEmailResponse(String city) {
        LogService.logger.debug("getCommunityEmailResponse() " + city);
        ArrayList<String> response = new ArrayList<String>();
        ArrayList<Person> persons = personDao.getByCity(city);
        for (Person p : persons) {
        	response.add(p.getEmail());
        }
        LogService.logger.debug("getCommunityEmailResponse() returns " + response.size() + " emails");
        return response;
    }

}
