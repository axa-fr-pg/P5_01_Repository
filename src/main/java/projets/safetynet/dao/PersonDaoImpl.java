package projets.safetynet.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import projets.safetynet.dao.exception.DuplicatePersonCreationException;
import projets.safetynet.dao.exception.MultiplePersonWithSameNameException;
import projets.safetynet.dao.exception.PersonNotFoundException;
import projets.safetynet.model.core.Person;
import projets.safetynet.service.util.LogService;

@Repository
public class PersonDaoImpl implements PersonDao {

	private ArrayList<Person> persons;
	
    public PersonDaoImpl() {
	}

	public PersonDaoImpl(ArrayList<Person> persons) {
		LogService.logger.debug("PersonDao() size = " + persons.size());
		this.persons = (ArrayList<Person>) persons.clone();
	}

	@Override
	public void set(ArrayList<Person> persons)
	{
		LogService.logger.debug("set() size = " + persons.size());
		this.persons = (ArrayList<Person>) persons.clone();
	}
	
	@Override
	public Person get(String firstName, String lastName) throws PersonNotFoundException, MultiplePersonWithSameNameException
    {
		LogService.logger.debug("get() " + firstName + " & " + lastName);
		Person result = null;
		int count = 0;
		for (Person p: persons) {
			if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
				result = p;
				count ++;
			}
		}
		switch (count) {
		case 0:
			LogService.logger.error("get() returns PersonNotFoundException");
			throw new PersonNotFoundException();
		case 1 :
			LogService.logger.debug("get() successful");
			return result;
		default :
			LogService.logger.error("get() returns MultiplePersonWithSameNameException");
			throw new MultiplePersonWithSameNameException();
		}
    }

    @Override
	public ArrayList<Person> getAll()
    {
		LogService.logger.debug("getAll() size = " + persons.size());
    	return persons;
    }
     
	@Override
	public ArrayList<Person> getByAddress(String address) {
		LogService.logger.debug("getByAddress() " + address);
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person p: persons) {
			if (p.getAddress().equals(address)) result.add(p);
		}
		LogService.logger.debug("getByAddress() returns size = " + result.size());
		return result;
	}

	@Override
	public ArrayList<Person> getByFirstName(String firstName) {
		LogService.logger.debug("getByFirstName() " + firstName);
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person p: persons) {
			if (p.getFirstName().equals(firstName)) result.add(p);
		}
		LogService.logger.debug("getByFirstName() returns size = " + result.size());
		return result;
	}

	@Override
	public ArrayList<Person> getByLastName(String lastName) {
		LogService.logger.debug("getByLastName() " + lastName);
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person p: persons) {
			if (p.getLastName().equals(lastName)) result.add(p);
		}
		LogService.logger.debug("getByLastName() returns size = " + result.size());
		return result;
	}

	@Override
	public Person save(Person p) throws DuplicatePersonCreationException, MultiplePersonWithSameNameException
    {
		LogService.logger.debug("save() " + p.getFirstName() + " & " + p.getLastName());
		try {
			get(p.getFirstName(), p.getLastName());
		} catch (PersonNotFoundException e) {
	    	Person pNew = new Person(p.getFirstName(), p.getLastName(), p.getAddress(),
	    			p.getCity(), p.getZip(), p.getPhone(), p.getEmail());
	    	persons.add(pNew);
			LogService.logger.debug("save() successful");
	    	return pNew;
		}
		LogService.logger.error("save() returns DuplicatePersonCreationException");
		throw new DuplicatePersonCreationException();
    }

    @Override
	public Person update(Person pNew) throws PersonNotFoundException
    {
		LogService.logger.debug("update() " + pNew.getFirstName() + " & " + pNew.getLastName());
		for (Person p: persons) {
			if (p.getFirstName().equals(pNew.getFirstName()) && 
					p.getLastName().equals(pNew.getLastName())) {
				p.setAddress(pNew.getAddress());
				p.setCity(pNew.getCity());
				p.setZip(pNew.getZip());
				p.setPhone(pNew.getPhone());
				p.setEmail(pNew.getEmail());
				return p;
			}
		}
		LogService.logger.error("update() returns PersonNotFoundException");
    	throw new PersonNotFoundException();
    }

    @Override
	public boolean delete(String firstName, String lastName) throws PersonNotFoundException, MultiplePersonWithSameNameException
    {
		LogService.logger.debug("delete() " + firstName + " & " + lastName);
		get(firstName, lastName);
		boolean result = persons.removeIf( person -> person.getFirstName().equals(firstName) &&
    			person.getLastName().equals(lastName) );
		LogService.logger.debug("delete() "+result);
		return result;
    }

	@Override
	public ArrayList<Person> getByCity(String city) {
		LogService.logger.debug("getByCity() " + city);
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person p: persons) {
			if (p.getCity().equals(city)) result.add(p);
		}
		LogService.logger.debug("getByCity() returns size = " + result.size());
		return result;
	}

}
