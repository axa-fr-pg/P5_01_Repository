package projets.safetynet.service.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.dao.exception.MedicalRecordNotFoundException;
import projets.safetynet.dao.exception.PersonNotFoundException;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;
import projets.safetynet.service.util.LogService;

@Service
public class DataUpdateServiceImpl implements DataUpdateService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private FireStationDao stationDao;

	@Autowired
	private MedicalRecordDao recordDao;

	@Override
	public Person putPersonRequest(Person pExpected) throws PersonNotFoundException {
        LogService.logger.debug("putPersonRequest() " + pExpected.getFirstName() + " " + pExpected.getLastName());
		Person pChanged = personDao.update(pExpected);
        LogService.logger.debug("putPersonRequest() successful");
		return pChanged;
	}

	@Override
	public FireStation putFireStationRequest(FireStation sExpected) throws FireStationNotFoundException {
        LogService.logger.debug("putFireStationRequest() " + sExpected.getAddress() + " " + sExpected.getStation());
		FireStation sChanged = stationDao.updateByAddress(sExpected);
        LogService.logger.debug("putFireStationRequest() successful");
		return sChanged;
	}

	@Override
	public MedicalRecord putMedicalRecordRequest(MedicalRecord mExpected) throws MedicalRecordNotFoundException {
        LogService.logger.debug("putMedicalRecordRequest() " + mExpected.getFirstName() + " " + mExpected.getLastName());
		MedicalRecord mChanged = recordDao.update(mExpected);
        LogService.logger.debug("putMedicalRecordRequest() successful");
		return mChanged;
	}

}
