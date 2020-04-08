package projets.safetynet.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import projets.safetynet.dao.exception.DuplicateFireStationCreationException;
import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.dao.exception.MultipleFireStationWithSameValuesException;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.service.util.LogService;

@Repository
public class FireStationDaoImpl implements FireStationDao {

	private ArrayList<FireStation> stations;

	public FireStationDaoImpl() {
	}

	public FireStationDaoImpl(ArrayList<FireStation> stations) {
		LogService.logger.debug("FireStationDao() size = " + stations.size());
		this.stations = (ArrayList<FireStation>) stations.clone();
	}

	@Override
	public void set(ArrayList<FireStation> stations) {
		LogService.logger.debug("set() size = " + stations.size());
		this.stations = (ArrayList<FireStation>) stations.clone();
	}

	@Override
	public FireStation get(String address, long station) throws 
		FireStationNotFoundException, MultipleFireStationWithSameValuesException
    {
		LogService.logger.debug("get() " + address + " & " + station);
		FireStation result = null;
		int count = 0;
		for (FireStation s: stations) {
			if (s.getAddress().equals(address) && s.getStation() == station) {
				result = s;
				count ++;
			}
		}
		switch (count) {
		case 0:
			LogService.logger.error("get() throws FireStationNotFoundException");
			throw new FireStationNotFoundException();
		case 1 :
			LogService.logger.debug("get() successful");
			return result;
		default :
			LogService.logger.error("get() throws MultipleFireStationWithSameValuesException");
			throw new MultipleFireStationWithSameValuesException();
		}
    }

    @Override
	public FireStation save(FireStation s) throws MultipleFireStationWithSameValuesException,
    	DuplicateFireStationCreationException
    {
		LogService.logger.debug("save() " + s.getAddress() + " & " + s.getStation());
		try {
			get(s.getAddress(), s.getStation());
		} catch (FireStationNotFoundException e) {
	    	FireStation sNew = new FireStation(s.getAddress(), s.getStation());
	    	stations.add(sNew);
			LogService.logger.debug("save() successful");
	    	return sNew;
		}
		LogService.logger.error("save() returns DuplicateFireStationCreationException");
		throw new DuplicateFireStationCreationException();
    }

	@Override
	public ArrayList<FireStation> getAll() {
		LogService.logger.debug("getAll() size = " + stations.size());
    	return stations;
	}

	@Override
	public FireStation getByAddress(String address) throws FireStationNotFoundException {
		LogService.logger.debug("getByAddress() " + address);
		for (FireStation s: stations) {
			if (s.getAddress().equals(address)) return s;
		}
		LogService.logger.error("getByAddress() returns FireStationNotFoundException");
		throw new FireStationNotFoundException();
	}

	@Override
	public ArrayList<FireStation> getByStation(long station) {
		LogService.logger.debug("getByStation() " + station);
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		for (FireStation s: stations) {
			if (s.getStation() == station) result.add(s);
		}
		LogService.logger.debug("getByStation() size = " + result.size());
		return result;
	}

	@Override
	public FireStation updateByAddress(FireStation sNew) throws FireStationNotFoundException {
		LogService.logger.debug("updateByAddress() " + sNew.getAddress());
		for (FireStation s: stations) {
			if (s.getAddress().equals(sNew.getAddress())) {
				s.setStation(sNew.getStation());
				return sNew;
			}
		}
		LogService.logger.error("updateByAddress() returns FireStationNotFoundException");
    	throw new FireStationNotFoundException();
	}

	@Override
	public boolean delete(String address, long station) throws FireStationNotFoundException, MultipleFireStationWithSameValuesException
	{
		LogService.logger.debug("delete() " + address + " & " + station);
		get(address, station);
		boolean result = stations.removeIf( s -> s.getAddress().equals(address)
				&& s.getStation() == station );
		LogService.logger.debug("delete() " + result);
		return result;
	}
	
	@Override
	public boolean deleteByStation(long station)
	{
		LogService.logger.debug("deleteByStation() " + station);
		boolean result = stations.removeIf( s -> s.getStation() == station );
		LogService.logger.debug("deleteByStation() "+result);
		return result;
	}

	@Override
	public boolean deleteByAddress(String address) {
		LogService.logger.debug("deleteByAddress() " + address);
		boolean result = stations.removeIf( s -> s.getAddress().equals(address) );
		LogService.logger.debug("deleteByAddress() "+result);
		return result;
	}
	
}
