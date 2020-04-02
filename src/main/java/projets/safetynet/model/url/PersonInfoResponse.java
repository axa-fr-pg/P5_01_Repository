package projets.safetynet.model.url;

public class PersonInfoResponse {

	private String name;
	private String address;
	private long age;
	private String email;
	private String[] medications;
	private String[] allergies;
	

	public PersonInfoResponse() {
		medications = new String[] {};
		allergies = new String[] {};
	}
	
	public PersonInfoResponse(String name, String address, long age, String email, 
			String[] medications, String[] allergies) {
		this.name = name;
		this.address = address;
		this.age = age;
		this.email = email;
		this.medications = medications.clone();
		this.allergies = allergies.clone();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String[] getMedications() {
		return medications.clone();
	}

	public void setMedications(String[] medications) {
		this.medications = medications.clone();
	}

	public String[] getAllergies() {
		return allergies.clone();
	}

	public void setAllergies(String[] allergies) {
		this.allergies = allergies.clone();
	}

}
