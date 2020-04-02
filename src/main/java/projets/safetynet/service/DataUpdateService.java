package projets.safetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.PersonNotFoundException;
import projets.safetynet.model.core.Person;

@Service
public class DataUpdateService {

	@Autowired
	private PersonDao personDao;

	public Person putPersonRequest(Person pExpected) {
        LogService.logger.info("putPersonRequest() " + pExpected.getFirstName() + " " + pExpected.getLastName());
		try {
			Person pChanged = personDao.update(pExpected);
	        LogService.logger.info("putPersonRequest() successful");
			return pChanged;
		} catch (PersonNotFoundException e) {
	        LogService.logger.error("putPersonRequest() throws PersonNotFoundException");
	        return null;
		}
	}

}
