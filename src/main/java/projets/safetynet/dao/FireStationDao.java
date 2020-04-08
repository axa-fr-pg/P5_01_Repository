package projets.safetynet.dao;

import java.util.ArrayList;

import projets.safetynet.dao.exception.DuplicateFireStationCreationException;
import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.dao.exception.MultipleFireStationWithSameValuesException;
import projets.safetynet.model.core.FireStation;

public interface FireStationDao {

	void set(ArrayList<FireStation> stations);

	FireStation get(String address, long station)
			throws FireStationNotFoundException, MultipleFireStationWithSameValuesException;

	FireStation save(FireStation s)
			throws MultipleFireStationWithSameValuesException, DuplicateFireStationCreationException;

	ArrayList<FireStation> getAll();

	FireStation getByAddress(String address) throws FireStationNotFoundException;

	ArrayList<FireStation> getByStation(long station);

	FireStation updateByAddress(FireStation sNew) throws FireStationNotFoundException;

	boolean delete(String address, long station)
			throws FireStationNotFoundException, MultipleFireStationWithSameValuesException;

	boolean deleteByStation(long station);

	boolean deleteByAddress(String address);

}