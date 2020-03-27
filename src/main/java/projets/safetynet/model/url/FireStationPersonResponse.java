package projets.safetynet.model.url;

import projets.safetynet.model.core.Person;

public class FireStationPersonResponse {
	
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	
	public FireStationPersonResponse() {		
	}

	public FireStationPersonResponse(String firstName, String lastName, String address, String phone) {		
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
	}

	public FireStationPersonResponse(Person p) {
		this.firstName = p.getFirstName();
		this.lastName = p.getLastName();
		this.address = p.getAddress();
		this.phone = p.getPhone();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
