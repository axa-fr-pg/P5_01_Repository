package projets.safetynet.model.url;

public class PersonRequest {

	private String firstName;
	private String lastName;

	public PersonRequest(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public PersonRequest() {
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
