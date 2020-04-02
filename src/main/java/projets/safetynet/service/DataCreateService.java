package projets.safetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.PersonNotFoundException;
import projets.safetynet.model.core.Person;

@Service
public class DataCreateService {

	@Autowired
	private PersonDao personDao;

	public Person postPersonRequest(Person pNew) {
        LogService.logger.info("postPersonRequest() " + pNew.getFirstName() + " " + pNew.getLastName());
//		personDao.save(pNew);
/*		try {
			Person p = personDao.get(pNew.getFirstName(), pNew.getLastName());
		} catch (PersonNotFoundException e) {
	        LogService.logger.error("postPersonRequest() throws PersonNotFoundException");
		}*/
		return pNew;
	}

}
