package projets.safetynet.model.url;

import java.util.ArrayList;

public class FireStationResponse {

	private ArrayList<FireStationPersonResponse> persons;
	private long numberAdults;
	private long numberChildren;
	
	public FireStationResponse() {
	}

	public FireStationResponse(ArrayList<FireStationPersonResponse> persons, long numberAdults, long numberChildren) {
		this.persons = new ArrayList<FireStationPersonResponse>(persons);
		this.numberAdults = numberAdults;
		this.numberChildren = numberChildren;
	}
	
	public ArrayList<FireStationPersonResponse> getPersons() {
		return new ArrayList<FireStationPersonResponse>(persons);
	}

	public void setPersons(ArrayList<FireStationPersonResponse> persons) {
		this.persons = new ArrayList<FireStationPersonResponse>(persons);
	}

	public long getNumberAdults() {
		return numberAdults;
	}

	public void setNumberAdults(long numberAdults) {
		this.numberAdults = numberAdults;
	}

	public long getNumberChildren() {
		return numberChildren;
	}

	public void setNumberChildren(long numberChildren) {
		this.numberChildren = numberChildren;
	}

}
