package projets.safetynet.model.core;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class MedicalRecordTest {

	long now = new Date().getTime();
	long years18ago = 1000L * 60L * 60L * 24L * 365L * 18L;
	long day1 = 1000L * 60L * 60L * 24L;

	@Test
	void givenPersonJustBefore18_whenGetAge_returns17()
	{
		// GIVEN
		long child = now - years18ago + day1 ;
		MedicalRecord p = new MedicalRecord("", "", new Date(child), new String[] {}, new String[] {});
		// WHEN
		long age = p.getAge();
		// THEN
		assertEquals(17, age);
	}
	
	@Test
	void givenPersonJustAfter18_whenGetAge_returns18()
	{
		// GIVEN
		long child = now - years18ago - day1 ;
		MedicalRecord p = new MedicalRecord("", "", new Date(child), new String[] {}, new String[] {});
		// WHEN
		long age = p.getAge();
		// THEN
		assertEquals(18, age);
	}
}
