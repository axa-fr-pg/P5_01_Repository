package projets.safetynet.dao;

import java.util.ArrayList;

import projets.safetynet.dao.exception.DuplicatePersonCreationException;
import projets.safetynet.dao.exception.MultiplePersonWithSameNameException;
import projets.safetynet.dao.exception.PersonNotFoundException;
import projets.safetynet.model.core.Person;

public interface PersonDao {

	void set(ArrayList<Person> persons);

	Person get(String firstName, String lastName) throws PersonNotFoundException, MultiplePersonWithSameNameException;

	ArrayList<Person> getAll();

	ArrayList<Person> getByAddress(String address);

	ArrayList<Person> getByFirstName(String firstName);

	ArrayList<Person> getByLastName(String lastName);

	Person save(Person p) throws DuplicatePersonCreationException, MultiplePersonWithSameNameException;

	Person update(Person pNew) throws PersonNotFoundException;

	boolean delete(String firstName, String lastName)
			throws PersonNotFoundException, MultiplePersonWithSameNameException;

	ArrayList<Person> getByCity(String city);

}