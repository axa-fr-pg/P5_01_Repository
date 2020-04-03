package projets.safetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MultipleFireStationWithSameValuesException;
import projets.safetynet.dao.MultiplePersonWithSameNameException;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.PersonNotFoundException;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.Person;

@Service
public class DataCreateService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private FireStationDao stationDao;

	public Person postPersonRequest(Person pNew) {
        LogService.logger.info("postPersonRequest() " + pNew.getFirstName() + " " + pNew.getLastName());
		try {
			Person pSaved = personDao.save(pNew);
	        LogService.logger.info("postPersonRequest() successful");
			return pSaved;
		} catch (MultiplePersonWithSameNameException e) {
	        LogService.logger.error("postPersonRequest() throws MultiplePersonWithSameNameException");
	        return null;
		}
	}

	public FireStation postFireStationRequest(FireStation sNew) {
        LogService.logger.info("postFireStationRequest() " + sNew.getAddress() + " " + sNew.getStation());
		try {
			FireStation sSaved = stationDao.save(sNew);
	        LogService.logger.info("postFireStationRequest() successful");
			return sSaved;
		} catch (MultipleFireStationWithSameValuesException e) {
	        LogService.logger.error("postFireStationRequest() throws MultipleFireStationWithSameValuesException");
	        return null;
		}
	}

}
