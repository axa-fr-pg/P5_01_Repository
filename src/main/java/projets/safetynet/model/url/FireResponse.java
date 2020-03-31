package projets.safetynet.model.url;

import java.util.ArrayList;

public class FireResponse {

	private long station;
	private ArrayList<FirePersonResponse> inhabitants;
	
	public FireResponse() {
		this.inhabitants = new ArrayList<FirePersonResponse>();
	}
	
	public FireResponse(long station, ArrayList<FirePersonResponse> inhabitants) {
		this.station = station;
		this.inhabitants = (ArrayList<FirePersonResponse>) inhabitants.clone();
	}

	public long getStation() {
		return station;
	}

	public void setStation(long station) {
		this.station = station;
	}

	public ArrayList<FirePersonResponse> getInhabitants() {
		return (ArrayList<FirePersonResponse>) inhabitants.clone();
	}

	public void setInhabitants(ArrayList<FirePersonResponse> inhabitants) {
		this.inhabitants = (ArrayList<FirePersonResponse>) inhabitants.clone();
	}
	
}
