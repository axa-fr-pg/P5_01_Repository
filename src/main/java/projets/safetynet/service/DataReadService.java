package projets.safetynet.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.FireStationPersonResponse;
import projets.safetynet.model.url.FireStationResponse;

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
		ArrayList<Person> allPersons = personDao.getAll();
		ArrayList<FireStation> stations = stationDao.getAll();
		ArrayList<MedicalRecord> records = recordDao.getAll();
		ArrayList<FireStationPersonResponse> responsePersons = new ArrayList<FireStationPersonResponse>();
		long adults = 0;
		long children = 0;

		for (FireStation s : stations) if (s.getStation() == station) {
			for (Person p : allPersons) if (p.getAddress().equals(s.getAddress())) {
				FireStationPersonResponse responsePerson = new FireStationPersonResponse(p);
				responsePersons.add(responsePerson);
				for (MedicalRecord r : records)
					if (r.getFirstName().equals(p.getFirstName()) && 
							r.getLastName().equals(p.getLastName()) ){
						long age = r.getAge();
						if (age < 19) children ++;
						else adults ++;
				}
			}
		}

		FireStationResponse response = new FireStationResponse(responsePersons, adults, children);
		LogService.logger.info("getFireStationResponse() returns " + responsePersons.size() + " persons " 
				+ adults + " adults " + children + " children");
		return response;
	}

}
