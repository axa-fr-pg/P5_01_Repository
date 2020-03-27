package projets.safetynet.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.service.LogService;

@Repository
public class MedicalRecordDao {

	private ArrayList<MedicalRecord> records;
	
    public MedicalRecordDao() {
	}

	public MedicalRecordDao(ArrayList<MedicalRecord> records) {
		LogService.logger.debug("MedicalRecordDao() size = " + records.size());
		this.records = new ArrayList<MedicalRecord>();
		for (MedicalRecord m: records) {
			save(m);
		}		
	}
	
	public void set(ArrayList<MedicalRecord> records) {
		this.records = new MedicalRecordDao(records).records;
	}
	
    public void save(MedicalRecord m)
    {
		LogService.logger.debug("save() " + m.getFirstName() + " & " + m.getLastName());
    	MedicalRecord mNew = new MedicalRecord(m.getFirstName(), m.getLastName(), m.getBirthdate(),
    			m.getMedications().clone(), m.getAllergies().clone());
    	records.add(mNew);
    }

	public ArrayList<MedicalRecord> getAll() {
		LogService.logger.debug("getAll() size = " + records.size());
    	return records;
	}

	public MedicalRecord get(String firstName, String lastName) throws MedicalRecordNotFoundException 
	{
		LogService.logger.debug("get() " + firstName + " & " + lastName);
		for (MedicalRecord m: records) {
			if (m.getFirstName().equals(firstName) &&
					m.getLastName().equals(lastName)) return m;
		}
		LogService.logger.error("get() returns MedicalRecordNotFoundException");
		throw new MedicalRecordNotFoundException();
	}

	public void update(MedicalRecord mNew) throws MedicalRecordNotFoundException {
		LogService.logger.debug("update() " + mNew.getFirstName() + " & " + mNew.getLastName());
		for (MedicalRecord m: records) {
			if (m.getFirstName().equals(mNew.getFirstName()) && m.getLastName().equals(mNew.getLastName())) {
				m.setBirthdate(mNew.getBirthdate());
				m.setMedications(mNew.getMedications().clone());
				m.setAllergies(mNew.getAllergies().clone());
				return;
			}
		}
		LogService.logger.error("update() returns MedicalRecordNotFoundException");
    	throw new MedicalRecordNotFoundException();
	}

	public void delete(MedicalRecord m) {
		LogService.logger.debug("delete() " + m.getFirstName() + " & " + m.getLastName());
		records.removeIf( record -> record.getFirstName().equals(m.getFirstName()) &&
    			record.getLastName().equals(m.getLastName()) );
	}

}
