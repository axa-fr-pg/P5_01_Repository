package projets.safetynet.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import projets.safetynet.model.core.FireStation;
import projets.safetynet.service.LogService;

@Repository
public class FireStationDao {

	private ArrayList<FireStation> stations;

	public FireStationDao() {
	}

	public FireStationDao(ArrayList<FireStation> stations) {
		LogService.logger.debug("FireStationDao() size = " + stations.size());
		this.stations = (ArrayList<FireStation>) stations.clone();
	}

	public void set(ArrayList<FireStation> stations) {
		LogService.logger.debug("set() size = " + stations.size());
		this.stations = (ArrayList<FireStation>) stations.clone();
	}

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

    public FireStation save(FireStation s) throws MultipleFireStationWithSameValuesException
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
		LogService.logger.error("save() returns MultipleFireStationWithSameValuesException");
		throw new MultipleFireStationWithSameValuesException();
    }

	public ArrayList<FireStation> getAll() {
		LogService.logger.debug("getAll() size = " + stations.size());
    	return stations;
	}

	public FireStation getByAddress(String address) throws FireStationNotFoundException {
		LogService.logger.debug("getByAddress() " + address);
		for (FireStation s: stations) {
			if (s.getAddress().equals(address)) return s;
		}
		LogService.logger.error("getByAddress() returns FireStationNotFoundException");
		throw new FireStationNotFoundException();
	}

	public ArrayList<FireStation> getByStation(long station) {
		LogService.logger.debug("getByStation() " + station);
		ArrayList<FireStation> result = new ArrayList<FireStation>();
		for (FireStation s: stations) {
			if (s.getStation() == station) result.add(s);
		}
		LogService.logger.debug("getByStation() size = " + result.size());
		return result;
	}

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

	public boolean delete(String address, long station)
	{
		LogService.logger.debug("delete() " + address + " & " + station);
		boolean result = stations.removeIf( s -> s.getAddress().equals(address)
				&& s.getStation() == station );
		LogService.logger.debug("delete() " + result);
		return result;
	}
	
	public boolean deleteByStation(long station)
	{
		LogService.logger.debug("deleteByStation() " + station);
		boolean result = stations.removeIf( s -> s.getStation() == station );
		LogService.logger.debug("deleteByStation() "+result);
		return result;
	}

	public boolean deleteByAddress(String address) {
		LogService.logger.debug("deleteByAddress() " + address);
		boolean result = stations.removeIf( s -> s.getAddress().equals(address) );
		LogService.logger.debug("deleteByAddress() "+result);
		return result;
	}
	
}
