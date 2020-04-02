package projets.safetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.core.Person;

@Service
public class DataCreateService {

	@Autowired
	private PersonDao personDao;

	public void postPersonRequest(Person p) {
        LogService.logger.info("postPersonRequest() " + p.getFirstName() + " " + p.getLastName());
		personDao.save(p);
	}

}
