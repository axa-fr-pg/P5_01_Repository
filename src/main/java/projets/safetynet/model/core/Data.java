package projets.safetynet.model.core;

import java.util.ArrayList;

public class Data {

	private ArrayList<Person> persons;
	private ArrayList<FireStation> firestations;
	private ArrayList<MedicalRecord> medicalrecords;
	
	public Data() {
		persons = new ArrayList<Person>();
		firestations = new ArrayList<FireStation>();
		medicalrecords = new ArrayList<MedicalRecord>();
	}
	
	public Data(ArrayList<Person> persons, ArrayList<FireStation> firestations,	ArrayList<MedicalRecord> medicalrecords) {
		this.persons = (ArrayList<Person>) persons.clone();
		this.firestations = (ArrayList<FireStation>) firestations.clone();
		this.medicalrecords = (ArrayList<MedicalRecord>) medicalrecords.clone();
	}
	
	public ArrayList<Person> getPersons() {
		return (ArrayList<Person>) persons.clone();
	}
	
	public void setPersons(ArrayList<Person> persons) {
		this.persons = (ArrayList<Person>) persons.clone();
	}
	
	public ArrayList<FireStation> getFirestations() {
		return (ArrayList<FireStation>) firestations.clone();
	}
	
	public void setFirestations(ArrayList<FireStation> firestations) {
		this.firestations = (ArrayList<FireStation>) firestations.clone();
	}
	
	public ArrayList<MedicalRecord> getMedicalrecords() {
		return (ArrayList<MedicalRecord>) medicalrecords.clone();
	}
	
	public void setMedicalrecords(ArrayList<MedicalRecord> medicalrecords) {
		this.medicalrecords = (ArrayList<MedicalRecord>) medicalrecords.clone();
	}
}
