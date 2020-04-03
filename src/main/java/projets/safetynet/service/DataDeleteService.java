package projets.safetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projets.safetynet.dao.PersonDao;
import projets.safetynet.dao.PersonNotFoundException;
import projets.safetynet.model.url.PersonRequest;

@Service
public class DataDeleteService {

	@Autowired
	private PersonDao personDao;

	public boolean deletePersonRequest(PersonRequest request) {
        LogService.logger.info("deletePersonRequest() " + request.getFirstName() + " " + request.getLastName());
		try {
			boolean result = personDao.delete(request.getFirstName(), request.getLastName());
	        LogService.logger.info("deletePersonRequest() successful");
			return result;
		} catch (PersonNotFoundException e) {
	        LogService.logger.error("deletePersonRequest() throws PersonNotFoundException");
	        return false;
		}
	}

}
