package projets.safetynet.model.core;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FireStation {
	private String address;
	
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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
