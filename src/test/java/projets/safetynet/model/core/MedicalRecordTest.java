package projets.safetynet.model.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class MedicalRecordTest {

	long now = new Date().getTime();
	long years19ago = 1000L * 60L * 60L * 24L * 365L * 19L;
	long day1 = 1000L * 60L * 60L * 24L;

	@Test
	void givenPersonJustBefore19_whenGetAge_returns18()
	{
		// GIVEN
		long child = now - years19ago + day1 ;
		MedicalRecord p = new MedicalRecord("", "", new Date(child), new String[] {}, new String[] {});
		// WHEN
		long age = p.getAge();
		// THEN
		assertEquals(18, age);
	}
	
	@Test
	void givenPersonJustAfter19_whenGetAge_returns19()
	{
		// GIVEN
		long child = now - years19ago - day1 ;
		MedicalRecord p = new MedicalRecord("", "", new Date(child), new String[] {}, new String[] {});
		// WHEN
		long age = p.getAge();
		// THEN
		assertEquals(19, age);
	}
}
