package projets.safetynet.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
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
						if (age < 18) children ++;
						else adults ++;
				}
			}
		}
		
		FireStationResponse response = new FireStationResponse(responsePersons, adults, children);
		return response;
	}

}
