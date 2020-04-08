package projets.safetynet.dao;

import java.util.ArrayList;

import projets.safetynet.dao.exception.DuplicateMedicalRecordCreationException;
import projets.safetynet.dao.exception.MedicalRecordNotFoundException;
import projets.safetynet.dao.exception.MultipleMedicalRecordWithSameNameException;
import projets.safetynet.model.core.MedicalRecord;

public interface MedicalRecordDao {

	void set(ArrayList<MedicalRecord> records);

	MedicalRecord save(MedicalRecord m)
			throws MultipleMedicalRecordWithSameNameException, DuplicateMedicalRecordCreationException;

	ArrayList<MedicalRecord> getAll();

	MedicalRecord get(String firstName, String lastName)
			throws MedicalRecordNotFoundException, MultipleMedicalRecordWithSameNameException;

	MedicalRecord update(MedicalRecord mNew) throws MedicalRecordNotFoundException;

	boolean delete(String firstName, String lastName)
			throws MedicalRecordNotFoundException, MultipleMedicalRecordWithSameNameException;

}