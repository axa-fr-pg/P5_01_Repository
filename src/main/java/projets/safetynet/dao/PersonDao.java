package projets.safetynet.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import projets.safetynet.model.core.Person;
import projets.safetynet.service.LogService;

@Repository
public class PersonDao {

	private ArrayList<Person> persons;
	
    public PersonDao() {
	}

	public PersonDao(ArrayList<Person> persons) {
		LogService.logger.debug("PersonDao() size = " + persons.size());
		this.persons = (ArrayList<Person>) persons.clone();
	}

	public void set(ArrayList<Person> persons)
	{
		LogService.logger.debug("set() size = " + persons.size());
		this.persons = (ArrayList<Person>) persons.clone();
	}
	
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
			LogService.logger.debug("get()successful");
			return result;
		default :
			LogService.logger.error("get() returns MultiplePersonWithSameNameException");
			throw new MultiplePersonWithSameNameException();
		}
    }

    public ArrayList<Person> getAll()
    {
		LogService.logger.debug("getAll() size = " + persons.size());
    	return persons;
    }
     
	public ArrayList<Person> getByAddress(String address) {
		LogService.logger.debug("getByAddress() " + address);
		ArrayList<Person> result = new ArrayList<Person>();
		for (Person p: persons) {
			if (p.getAddress().equals(address)) result.add(p);
		}
		LogService.logger.debug("getByAddress() returns size = " + result.size());
		return result;
	}

	public Person save(Person p) throws MultiplePersonWithSameNameException
    {
		LogService.logger.debug("save() " + p.getFirstName() + " " + p.getLastName());
		try {
			get(p.getFirstName(), p.getLastName());
		} catch (PersonNotFoundException e) {
	    	Person pNew = new Person(p.getFirstName(), p.getLastName(), p.getAddress(),
	    			p.getCity(), p.getZip(), p.getPhone(), p.getEmail());
	    	persons.add(pNew);
	    	return pNew;
		}
		LogService.logger.error("save() returns MultiplePersonWithSameNameException");
		throw new MultiplePersonWithSameNameException();
    }

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

    public boolean delete(Person p) throws PersonNotFoundException
    {
		LogService.logger.debug("delete() " + p.getFirstName() + " & " + p.getLastName());
		try {
			Person exists = get(p.getFirstName(), p.getLastName());
		} catch (MultiplePersonWithSameNameException e) {
			LogService.logger.debug("delete() will remove all duplicates");
		}
		persons.removeIf( person -> person.getFirstName().equals(p.getFirstName()) &&
    			person.getLastName().equals(p.getLastName()) );
		LogService.logger.debug("delete() successful");
		return false;
    }

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
