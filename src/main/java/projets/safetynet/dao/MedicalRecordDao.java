package projets.safetynet.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import projets.safetynet.dao.exception.DuplicateMedicalRecordCreationException;
import projets.safetynet.dao.exception.MedicalRecordNotFoundException;
import projets.safetynet.dao.exception.MultipleMedicalRecordWithSameNameException;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;
import projets.safetynet.service.LogService;

@Repository
public class MedicalRecordDao {

	private ArrayList<MedicalRecord> records;
	
    public MedicalRecordDao() {
	}

	public MedicalRecordDao(ArrayList<MedicalRecord> records) {
		LogService.logger.debug("MedicalRecordDao() size = " + records.size());
		this.records = (ArrayList<MedicalRecord>) records.clone();
	}
	
	public void set(ArrayList<MedicalRecord> records) {
		LogService.logger.debug("set() size = " + records.size());
		this.records = (ArrayList<MedicalRecord>) records.clone();
	}
	
    public MedicalRecord save(MedicalRecord m) throws MultipleMedicalRecordWithSameNameException,
    	DuplicateMedicalRecordCreationException
    {
		LogService.logger.debug("save() " + m.getFirstName() + " & " + m.getLastName());
		try {
			get(m.getFirstName(), m.getLastName());
		} catch (MedicalRecordNotFoundException e) {
			MedicalRecord mNew = new MedicalRecord(m.getFirstName(), m.getLastName(), m.getBirthdate(),
	    			m.getMedications(), m.getAllergies());
	    	records.add(mNew);
			LogService.logger.debug("save() successful");
	    	return mNew;
		}
		LogService.logger.error("save() returns DuplicateMedicalRecordCreationException");
		throw new DuplicateMedicalRecordCreationException();

    }

	public ArrayList<MedicalRecord> getAll() {
		LogService.logger.debug("getAll() size = " + records.size());
    	return records;
	}

	public MedicalRecord get(String firstName, String lastName) throws 
		MedicalRecordNotFoundException, MultipleMedicalRecordWithSameNameException 
	{
		LogService.logger.debug("get() " + firstName + " & " + lastName);
		MedicalRecord result = null;
		int count = 0;
		for (MedicalRecord m: records) {
			if (m.getFirstName().equals(firstName) && m.getLastName().equals(lastName)) {
				result = m;
				count ++;
			}
		}
		switch (count) {
		case 0:
			LogService.logger.error("get() returns MedicalRecordNotFoundException");
			throw new MedicalRecordNotFoundException();
		case 1 :
			LogService.logger.debug("get() successful");
			return result;
		default :
			LogService.logger.error("get() returns MultipleMedicalRecordWithSameNameException");
			throw new MultipleMedicalRecordWithSameNameException();
		}
	}

	public MedicalRecord update(MedicalRecord mNew) throws MedicalRecordNotFoundException {
		LogService.logger.debug("update() " + mNew.getFirstName() + " & " + mNew.getLastName());
		for (MedicalRecord m: records) {
			if (m.getFirstName().equals(mNew.getFirstName()) && m.getLastName().equals(mNew.getLastName())) {
				m.setBirthdate(mNew.getBirthdate());
				m.setMedications(mNew.getMedications().clone());
				m.setAllergies(mNew.getAllergies().clone());
				return mNew;
			}
		}
		LogService.logger.error("update() returns MedicalRecordNotFoundException");
    	throw new MedicalRecordNotFoundException();
	}

	public boolean delete(String firstName, String lastName) throws MedicalRecordNotFoundException,
		MultipleMedicalRecordWithSameNameException {
		LogService.logger.debug("delete() " + firstName + " & " + lastName);
		get(firstName, lastName);
		boolean result = records.removeIf( record -> record.getFirstName().equals(firstName) &&
				record.getLastName().equals(lastName) );
		LogService.logger.debug("delete() "+result);
		return result;
	}

}
