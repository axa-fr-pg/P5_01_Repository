package projets.safetynet.model;

import java.util.ArrayList;

public class Data {

	private ArrayList<Person> persons;
	private ArrayList<FireStation> firestations;
	private ArrayList<MedicalRecord> medicalrecords;
	
	public Data() {

	}
	
	public Data(ArrayList<Person> persons, ArrayList<FireStation> firestations,	ArrayList<MedicalRecord> medicalrecords) {
		this.persons = persons;
		this.firestations = firestations;
		this.medicalrecords = medicalrecords;
	}
	
	public ArrayList<Person> getPersons() {
		return persons;
	}
	
	public void setPersons(ArrayList<Person> persons) {
		this.persons = persons;
	}
	
	public ArrayList<FireStation> getFirestations() {
		return firestations;
	}
	
	public void setFirestations(ArrayList<FireStation> firestations) {
		this.firestations = firestations;
	}
	
	public ArrayList<MedicalRecord> getMedicalrecords() {
		return medicalrecords;
	}
	
	public void setMedicalrecords(ArrayList<MedicalRecord> medicalrecords) {
		this.medicalrecords = medicalrecords;
	}
}
