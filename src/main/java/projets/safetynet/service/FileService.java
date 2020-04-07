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
		Data data = getDataFromFile(getJsonFileName());
		if (personDao==null || fireStationDao==null || medicalRecordDao==null)
		{
			LogService.logger.error("loadData() data objects are not ready");
			return;
		}
		personDao.set(data.getPersons());
		fireStationDao.set(data.getFirestations());
		medicalRecordDao.set(data.getMedicalrecords());
		LogService.logger.debug("loadData() stored data into DAO");
	}

	private String getJsonFileName() {
    	StackTraceElement[] trace = Thread.currentThread().getStackTrace();
    	String caller = trace[trace.length-1].getClassName();
    	if (caller == "projets.safetynet.SafetynetApplication") {
    		return "src/main/resources/data.json";
    	}
    	else return "src/test/resources/test.json";
	}

	public Data getDataFromFile(String file) {
		Data data = null;
		LogService.logger.info("getDataFromFile() " + file);
		try {
			data = objectMapper.readValue(new File(file), Data.class);
			LogService.logger.debug("getDataFromFile() successful");
		} catch (Exception e) {
			LogService.logger.error("getDataFromFile() could not read file : " + e);
		}
		return data;
	}
}
