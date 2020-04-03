package projets.safetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.PersonNotFoundException;
import projets.safetynet.model.core.Person;

@Service
public class DataDeleteService {

	@Autowired
	private PersonDao personDao;

	public boolean deletePersonRequest(Person p) {
        LogService.logger.info("deletePersonRequest() " + p.getFirstName() + " " + p.getLastName());
		try {
			boolean result = personDao.delete(p);
	        LogService.logger.info("deletePersonRequest() successful");
			return result;
		} catch (PersonNotFoundException e) {
	        LogService.logger.error("deletePersonRequest() throws PersonNotFoundException");
	        return false;
		}
	}

}
