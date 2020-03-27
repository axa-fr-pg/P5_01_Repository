package projets.safetynet.service;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.Data;

@Component
public class FileService {

	@Autowired
	PersonDao personDao;
	
	@Autowired
	FireStationDao fireStationDao;
	
	@Autowired
	MedicalRecordDao medicalRecordDao;
	
	@PostConstruct
	void loadData()
	{
		String fileName = "src/main/resources/data.json";
		Data data = null;
		LogService.logger.info("loadData() " + fileName);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			data = objectMapper.readValue(new File(fileName), Data.class);
			LogService.logger.info("loadData() file read successfully");
			
		} catch (Exception e) {
			LogService.logger.error("loadData() could not read file");
		}
		
		personDao.set(data.getPersons());
		fireStationDao.set(data.getFirestations());
		medicalRecordDao.set(data.getMedicalrecords());
		LogService.logger.info("loadData() stored into data objects");
	}

}
