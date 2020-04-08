package projets.safetynet.service.data;

import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.dao.exception.MedicalRecordNotFoundException;
import projets.safetynet.dao.exception.PersonNotFoundException;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;

public interface DataUpdateService {

	Person putPersonRequest(Person pExpected) throws PersonNotFoundException;

	FireStation putFireStationRequest(FireStation sExpected) throws FireStationNotFoundException;

	MedicalRecord putMedicalRecordRequest(MedicalRecord mExpected) throws MedicalRecordNotFoundException;

}