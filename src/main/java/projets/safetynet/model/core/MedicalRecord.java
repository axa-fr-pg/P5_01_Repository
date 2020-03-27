package projets.safetynet.model.core;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MedicalRecord {

	private String firstName;
	private String lastName;
	
    @JsonFormat(pattern="dd/MM/yyyy")
	private Date birthdate;
    
	private String[] medications;
	private String[] allergies;
	
	public MedicalRecord() 
	{	
	}
	
	public MedicalRecord(String firstName, String lastName, Date birthdate, String[] medications, String[] allergies) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = new Date(birthdate.getTime());
		this.medications = medications.clone();
		this.allergies = allergies.clone();
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

	public Date getBirthdate() {
		return new Date(birthdate.getTime()); 
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = new Date(birthdate.getTime());
	}

<<<<<<< HEAD
=======
	public long getAge() {
		long now = new Date().getTime();
		long birthTime = birthdate.getTime();
		long elapsedTime = now - birthTime;
		long year = 1000L * 60L * 60L * 24L * 365L;
		long age = elapsedTime / year;
		return age;
	}

>>>>>>> refs/heads/feature/10_init_firestation_url_model_and_service
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
