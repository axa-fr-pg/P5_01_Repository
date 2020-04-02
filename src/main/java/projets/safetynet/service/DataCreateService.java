package projets.safetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projets.safetynet.dao.MultiplePersonWithSameNameException;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.PersonNotFoundException;
import projets.safetynet.model.core.Person;

@Service
public class DataCreateService {

	@Autowired
	private PersonDao personDao;

	public Person postPersonRequest(Person pNew) {
        LogService.logger.info("postPersonRequest() " + pNew.getFirstName() + " " + pNew.getLastName());
		try {
			Person pSaved = personDao.save(pNew);
	        LogService.logger.info("postPersonRequest() successful");
			return pSaved;
		} catch (MultiplePersonWithSameNameException e) {
	        LogService.logger.error("postPersonRequest() throws MultiplePersonWithSameNameException");
	        return null;
		}
	}

}
