package projets.safetynet.model.core;

import static org.junit.Assert.assertEquals;

import java.util.Date;

public class MedicalRecTest {

	void givenPersonJustBefore18_whenGetAge_returns17()
	{
		// GIVEN
		long now = new Date().getTime();
		long child17 = now + 1000 * 60 * 60 * 24 * (365 * 17 - 1);
		MedicalRecord p = new MedicalRecord("", "", new Date(child17), null, null);
		// WHEN
		long age = p.getAge();
		// THEN
		assertEquals(17, age);
	}
}
