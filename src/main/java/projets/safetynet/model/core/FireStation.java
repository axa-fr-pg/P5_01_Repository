package projets.safetynet.model.core;

public class FireStation {
	private String address;
	private long station;
	
	public FireStation() {
	}
	
	public FireStation(String address, long station) {
		this.address = address;
		this.station = station;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getStation() {
		return station;
	}

	public void setStation(long station) {
		this.station = station;
	}
	
}
