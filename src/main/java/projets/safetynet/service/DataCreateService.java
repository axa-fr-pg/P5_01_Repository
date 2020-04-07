package projets.safetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projets.safetynet.dao.DuplicateFireStationCreationException;
import projets.safetynet.dao.DuplicateMedicalRecordCreationException;
import projets.safetynet.dao.DuplicatePersonCreationException;
import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.MultipleFireStationWithSameValuesException;
import projets.safetynet.dao.MultipleMedicalRecordWithSameNameException;
import projets.safetynet.dao.MultiplePersonWithSameNameException;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.PersonNotFoundException;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;

@Service
public class DataCreateService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private FireStationDao stationDao;

	@Autowired
	private MedicalRecordDao recordDao;
	
	public Person postPersonRequest(Person pNew) 
			throws ServerDataCorruptedException, DuplicatePersonCreationException {
        LogService.logger.debug("postPersonRequest() " + pNew.getFirstName() + " " + pNew.getLastName());
		try {
			Person pSaved = personDao.save(pNew);
	        LogService.logger.debug("postPersonRequest() successful");
			return pSaved;
		} catch (MultiplePersonWithSameNameException e) {
	        LogService.logger.error("postPersonRequest() throws ServerDataCorruptedException");
	        throw new ServerDataCorruptedException();
		}
	}

	public FireStation postFireStationRequest(FireStation sNew) 
			throws ServerDataCorruptedException, DuplicateFireStationCreationException {
        LogService.logger.debug("postFireStationRequest() " + sNew.getAddress() + " " + sNew.getStation());
		try {
			FireStation sSaved = stationDao.save(sNew);
	        LogService.logger.debug("postFireStationRequest() successful");
			return sSaved;
		} catch (MultipleFireStationWithSameValuesException e) {
	        LogService.logger.error("postFireStationRequest() throws ServerDataCorruptedException");
	        throw new ServerDataCorruptedException();
		}
	}

	public MedicalRecord postMedicalRecordRequest(MedicalRecord mNew) 
			throws ServerDataCorruptedException, DuplicateMedicalRecordCreationException {
        LogService.logger.debug("postMedicalRecordRequest() " + mNew.getFirstName() + " " + mNew.getLastName());
		try {
			MedicalRecord mSaved = recordDao.save(mNew);
	        LogService.logger.debug("postMedicalRecordRequest() successful");
			return mSaved;
		} catch (MultipleMedicalRecordWithSameNameException e) {
	        LogService.logger.error("postMedicalRecordRequest() throws ServerDataCorruptedException");
	        throw new ServerDataCorruptedException();
		}
	}

}
