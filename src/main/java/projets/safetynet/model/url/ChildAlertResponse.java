package projets.safetynet.model.url;

public class ChildAlertResponse {
	
	private String firstName;
	private String lastName;
	private long age;
	String[] household;
	
	public ChildAlertResponse() {
		household = new String[] {};
	}
	
	public ChildAlertResponse(String firstName, String lastName, long age, String[] household) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.household = household;
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

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String[] getHousehold() {
		return household.clone();
	}

	public void setHousehold(String[] household) {
		this.household = household.clone();
	}

}
