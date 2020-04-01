package projets.safetynet.model.url;

import java.util.ArrayList;

public class FloodAddressResponse {

	private String address;
	private ArrayList<FirePersonResponse> inhabitants;

	public FloodAddressResponse() {
		this.inhabitants = new ArrayList<FirePersonResponse>();
	}

	public FloodAddressResponse(String address, ArrayList<FirePersonResponse> inhabitants) {
		this.address = address;
		this.inhabitants = (ArrayList<FirePersonResponse>) inhabitants.clone();
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public ArrayList<FirePersonResponse> getInhabitants() {
		return (ArrayList<FirePersonResponse>) inhabitants.clone();
	}
	public void setInhabitants(ArrayList<FirePersonResponse> inhabitants) {
		this.inhabitants = (ArrayList<FirePersonResponse>) inhabitants.clone();
	}
}
