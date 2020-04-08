package projets.safetynet.service.data;

import projets.safetynet.dao.exception.DuplicateFireStationCreationException;
import projets.safetynet.dao.exception.DuplicateMedicalRecordCreationException;
import projets.safetynet.dao.exception.DuplicatePersonCreationException;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;
import projets.safetynet.service.exception.ServerDataCorruptedException;

public interface DataCreateService {

	Person postPersonRequest(Person pNew) throws ServerDataCorruptedException, DuplicatePersonCreationException;

	FireStation postFireStationRequest(FireStation sNew)
			throws ServerDataCorruptedException, DuplicateFireStationCreationException;

	MedicalRecord postMedicalRecordRequest(MedicalRecord mNew)
			throws ServerDataCorruptedException, DuplicateMedicalRecordCreationException;

}