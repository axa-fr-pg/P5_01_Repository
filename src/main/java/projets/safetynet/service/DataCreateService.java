package projets.safetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.exception.DuplicateFireStationCreationException;
import projets.safetynet.dao.exception.DuplicateMedicalRecordCreationException;
import projets.safetynet.dao.exception.DuplicatePersonCreationException;
import projets.safetynet.dao.exception.MultipleFireStationWithSameValuesException;
import projets.safetynet.dao.exception.MultipleMedicalRecordWithSameNameException;
import projets.safetynet.dao.exception.MultiplePersonWithSameNameException;
import projets.safetynet.dao.exception.PersonNotFoundException;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;
import projets.safetynet.service.exception.ServerDataCorruptedException;

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
