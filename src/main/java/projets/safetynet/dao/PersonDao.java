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
	
	Person get(String firstName, String lastName) throws PersonNotFoundException
    {
		LogService.logger.debug("get() " + firstName + " & " + lastName);
		for (Person p: persons) {
			if (p.getFirstName().equals(firstName) &&
					p.getLastName().equals(lastName)) return p;
		}
		LogService.logger.error("get() returns PersonNotFoundException");
		throw new PersonNotFoundException();
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

	public void save(Person p)
    {
		LogService.logger.debug("save() " + p.getFirstName() + " & " + p.getLastName());
    	Person pNew = new Person(p.getFirstName(), p.getLastName(), p.getAddress(),
    			p.getCity(), p.getZip(), p.getPhone(), p.getEmail());
    	persons.add(pNew);
    }
     
    void update(Person pNew) throws PersonNotFoundException
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
				return;
			}
		}
		LogService.logger.error("update() returns PersonNotFoundException");
    	throw new PersonNotFoundException();
    }

    void delete(Person p) // Does not throw any exception if the person is not found
    {
		LogService.logger.debug("delete() " + p.getFirstName() + " & " + p.getLastName());
		persons.removeIf( person -> person.getFirstName().equals(p.getFirstName()) &&
    			person.getLastName().equals(p.getLastName()) );
    }

}
