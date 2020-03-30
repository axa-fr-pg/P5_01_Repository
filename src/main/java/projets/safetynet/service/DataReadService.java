package projets.safetynet.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.MedicalRecordNotFoundException;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.FireStationPersonResponse;
import projets.safetynet.model.url.FireStationResponse;

public class DataReadService {

	@Autowired
	PersonDao personDao;

	@Autowired
	FireStationDao stationDao;

	@Autowired
	MedicalRecordDao recordDao;

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
				       LogService.logger.error("getFireStationResponse() no MedicalRecord for " 
				    		   + p.getFirstName() + " " + p.getLastName()); 
				}
			}
		}

		FireStationResponse response = new FireStationResponse(responsePersons, adults, children);
        LogService.logger.info("getFireStationResponse() returns " + responsePersons.size() + " persons, " 
                + adults + " adults, " + children + " children");
		return response;
	}

}
