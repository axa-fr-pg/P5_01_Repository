package projets.safetynet.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projets.safetynet.model.core.MedicalRecord;

public class MedicalRecordDaoTest {
	
	private MedicalRecord m1;
	private MedicalRecord m2;
	private MedicalRecord m3;	
	
	@BeforeEach
	private void prepareTests()
	{
		String[] medications1 = new String[] {};
		String[] medications2 = new String[] {"medication 2"};
		String[] medications3 = new String[] {"medication 3 a", "medication 3 b", "medication 3 c"};
		String[] allergies1 = new String[] {"allergy 1 a", "allergy 1 b", "allergy 1 c", "allergy 1 d"};
		String[] allergies2 = new String[] {"allergy 2 a", "allergy 2 b"};
		String[] allergies3 = new String[] {};
		Date date1 = Date.valueOf("1001-01-01");
		Date date2 = Date.valueOf("2002-02-02");
		Date date3 = Date.valueOf("3003-03-03");
		m1 = new MedicalRecord ("firstName1","lastName1",date1,medications1,allergies1);
		m2 = new MedicalRecord ("firstName2","lastName2",date2,medications2,allergies2);
		m3 = new MedicalRecord ("firstName3","lastName3",date3,medications3,allergies3);
	}
	
	@Test
	void getAll_returnsCompleteList()
	{
		// GIVEN
		ArrayList<MedicalRecord> listGiven = new ArrayList<MedicalRecord>(Arrays.asList(m1, m2, m3));
		MedicalRecordDao dao = new MedicalRecordDao(listGiven);
		// WHEN
		ArrayList<MedicalRecord> listResult = dao.getAll();
		// THEN
		assertEquals(3, listResult.size());
	}

	@Test
	void givenExistingM2_getM2_returnsM2()
	{
		// GIVEN
		ArrayList<MedicalRecord> listGiven = new ArrayList<MedicalRecord>(Arrays.asList(m1, m2, m3));
		MedicalRecordDao dao = new MedicalRecordDao(listGiven);
		// WHEN
		MedicalRecord m = null;
		try {
			m = dao.get(m2.getFirstName(), m2.getLastName());
		} catch (MedicalRecordNotFoundException e) {
			e.printStackTrace();
		}
		// THEN
		assertEquals(m2.getFirstName(), m.getFirstName());
		assertEquals(m2.getLastName(), m.getLastName());
		assertEquals(m2.getBirthdate(), m.getBirthdate());
		assertTrue(Arrays.equals(m2.getMedications(), m.getMedications()));
		assertTrue(Arrays.equals(m2.getAllergies(), m.getAllergies()));
	}

	@Test
	void givenMissingM2_getM2_throwsMedicalRecordNotFoundException()
	{
		// GIVEN
		ArrayList<MedicalRecord> listGiven = new ArrayList<MedicalRecord>(Arrays.asList(m1, m3));
		MedicalRecordDao dao = new MedicalRecordDao(listGiven);
		// WHEN & THEN
		assertThrows(MedicalRecordNotFoundException.class, () -> {
			dao.get(m2.getFirstName(), m2.getLastName());
		});
	}
	
	@Test
	void saveM3_addsM3()
	{
		// GIVEN
		ArrayList<MedicalRecord> listGiven = new ArrayList<MedicalRecord>(Arrays.asList(m1, m2));
		MedicalRecordDao dao = new MedicalRecordDao(listGiven);
		// WHEN
		dao.save(m3);
		ArrayList<MedicalRecord> listResult = dao.getAll();
		// THEN
		assertEquals(3, listResult.size());
	}
	
	@Test
	void updateM2_changesM2()
	{
		// GIVEN
		ArrayList<MedicalRecord> listGiven = new ArrayList<MedicalRecord>(Arrays.asList(m1, m2, m3));
		MedicalRecordDao dao = new MedicalRecordDao(listGiven);
		// WHEN
		String[] medications4 = new String[] {"m4a", "m4b", "m4c", "m4d"};
		String[] allergies4 = new String[] {"a4a", "a4b", "a4c", "a4d"};
		Date date4 = Date.valueOf("4004-04-04");
		
		MedicalRecord m4 = new MedicalRecord (m2.getFirstName(),m2.getLastName(),date4, medications4, allergies4);
		try {
			dao.update(m4);
		} catch (MedicalRecordNotFoundException e) {
			e.printStackTrace();
		}
		MedicalRecord m = null;
		try {
			m = dao.get(m2.getFirstName(), m2.getLastName());
		} catch (MedicalRecordNotFoundException e) {
			e.printStackTrace();
		}
		// THEN
		assertEquals(m4.getFirstName(), m.getFirstName());
		assertEquals(m4.getLastName(), m.getLastName());
		assertEquals(date4, m.getBirthdate());
		assertTrue(Arrays.equals(medications4, m.getMedications()));
		assertTrue(Arrays.equals(allergies4, m.getAllergies()));
	}

	@Test
	void givenMissingM2_updateM2_throwsMedicalRecordNotFoundException()
	{
		// GIVEN
		ArrayList<MedicalRecord> listGiven = new ArrayList<MedicalRecord>(Arrays.asList(m1, m3));
		MedicalRecordDao dao = new MedicalRecordDao(listGiven);
		// WHEN & THEN
		assertThrows(MedicalRecordNotFoundException.class, () -> {
			dao.update(m2);
		});
	}

	@Test
	void givenM2_deleteM2_suppressesM2()
	{
		// GIVEN
		ArrayList<MedicalRecord> listGiven = new ArrayList<MedicalRecord>(Arrays.asList(m1, m2, m3));
		MedicalRecordDao dao = new MedicalRecordDao(listGiven);
		// WHEN
		dao.delete(m2);
		ArrayList<MedicalRecord> listResult = dao.getAll();
		// THEN
		assertEquals(2, listResult.size());
	}

}
