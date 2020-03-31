package projets.safetynet.model.url;

public class FirePersonResponse {
	
	private String name;
	private String phone;
	private long age;
	private String[] medications;
	private String[] allergies;

	public FirePersonResponse() {
		this.medications = new String[] {};
		this.allergies = new String[] {};
	}

	public FirePersonResponse(String name, String phone, long age, String[] medications, String[] allergies) {
		this.name = name;
		this.phone = phone;
		this.age = age;
		this.medications = medications.clone();
		this.allergies = allergies.clone();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String[] getMedications() {
		return medications;
	}

	public void setMedications(String[] medications) {
		this.medications = medications;
	}

	public String[] getAllergies() {
		return allergies;
	}

	public void setAllergies(String[] allergies) {
		this.allergies = allergies;
	}
	
}
