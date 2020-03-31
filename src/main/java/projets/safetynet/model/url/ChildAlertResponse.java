package projets.safetynet.model.url;

import java.util.ArrayList;

public class ChildAlertResponse {
	
	private String firstName;
	private String lastName;
	private long age;
	ArrayList<String> household;
	
	public ChildAlertResponse() {
		household = new ArrayList<String>();
;
	}
	
	public ChildAlertResponse(String firstName, String lastName, long age, ArrayList<String> household) {
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

	public ArrayList<String> getHousehold() {
		return (ArrayList<String>) household.clone();
	}

	public void setHousehold(ArrayList<String> household) {
		this.household = (ArrayList<String>) household.clone();
	}

}
