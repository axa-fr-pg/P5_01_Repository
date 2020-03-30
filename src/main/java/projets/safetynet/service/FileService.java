package projets.safetynet.service;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.core.Data;

@Service
public class FileService {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private FireStationDao fireStationDao;
	
	@Autowired
	private MedicalRecordDao medicalRecordDao;
	
	@PostConstruct
	void loadData()
	{
		Data data = getDataFromFile("src/main/resources/data.json");
		if (personDao==null || fireStationDao==null || medicalRecordDao==null)
		{
			LogService.logger.error("loadData() data objects are not ready");
			return;
		}
		personDao.set(data.getPersons());
		fireStationDao.set(data.getFirestations());
		medicalRecordDao.set(data.getMedicalrecords());
		LogService.logger.info("loadData() stored data into DAO");
	}

	public Data getDataFromFile(String file) {
		Data data = null;
		LogService.logger.info("getDataFromFile() " + file);
		try {
			data = objectMapper.readValue(new File(file), Data.class);
			LogService.logger.info("getDataFromFile() successful");
		} catch (Exception e) {
			LogService.logger.error("getDataFromFile() could not read file : " + e);
		}
		return data;
	}
}
