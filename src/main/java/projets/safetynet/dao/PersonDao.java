package projets.safetynet.dao;

import java.util.ArrayList;
import projets.safetynet.model.Person;

public class PersonDao {

	private ArrayList<Person> persons;
	
    public PersonDao() {
	}

	public PersonDao(ArrayList<Person> persons) {
		this.persons = new ArrayList<Person>();
		for (Person p: persons) {
			save(p);
		}		
	}

	Person get(String firstName, String lastName) throws PersonNotFoundException
    {
		for (Person p: persons) {
			if (p.getFirstName().equals(firstName) &&
					p.getLastName().equals(lastName)) return p;
		}
    	throw new PersonNotFoundException();
    }
    
    ArrayList<Person> getAll()
    {
    	return persons;
    }
     
    void save(Person p)
    {
    	Person pNew = new Person(p.getFirstName(), p.getLastName(), p.getAddress(),
    			p.getCity(), p.getZip(), p.getPhone(), p.getEmail());
    	persons.add(pNew);
    }
     
    void update(Person pNew) throws PersonNotFoundException
    {
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
    	throw new PersonNotFoundException();
    }
     
    void delete(Person p)
    {
    	persons.removeIf( person -> person.getFirstName().equals(p.getFirstName()) &&
    			person.getLastName().equals(p.getLastName()) );
    }
}
