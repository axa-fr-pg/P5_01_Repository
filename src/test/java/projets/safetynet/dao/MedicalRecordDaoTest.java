package projets.safetynet.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;

@SpringBootTest
public class MedicalRecordDaoTest {
	
	@Autowired
	private MedicalRecordDao dao;
	
	String[] medications1 = new String[] {};
	String[] medications2 = new String[] {"medication 2 a", "medication 2 b"};
	String[] medications3 = new String[] {"medication 3 a", "medication 3 b", "medication 3 c"};
	String[] allergies1 = new String[] {"allergy 1 a", "allergy 1 b", "allergy 1 c", "allergy 1 d"};
	String[] allergies2 = new String[] {"allergy 2 a", "allergy 2 b"};
	String[] allergies3 = new String[] {};
	Date date1 = Date.valueOf("1001-01-01");
	Date date2 = Date.valueOf("2002-02-02");
	Date date3 = Date.valueOf("3003-03-03");
	private MedicalRecord m1 = new MedicalRecord ("firstName1","lastName1",date1,medications1,allergies1);
	private MedicalRecord m2 = new MedicalRecord ("firstName2","lastName2",date2,medications2,allergies2);
	private MedicalRecord m3 = new MedicalRecord ("firstName3","lastName3",date3,medications3,allergies3);
	
	@BeforeEach
	private void prepareTests()
	{
		dao.set(new ArrayList<MedicalRecord>());
	}

	@Test
	void getAll_returnsCompleteList()
	{
		// GIVEN
		ArrayList<MedicalRecord> listGiven = new ArrayList<MedicalRecord>(Arrays.asList(m1, m2, m3));
		dao.set(listGiven);
		// WHEN
		ArrayList<MedicalRecord> listResult = dao.getAll();
		// THEN
		assertEquals(3, listResult.size());
		assertEquals("firstName1", listResult.get(0).getFirstName());
		assertEquals("lastName1", listResult.get(0).getLastName());
		assertEquals(date1, listResult.get(0).getBirthdate());
		assertTrue(Arrays.equals(medications1, listResult.get(0).getMedications()));
		assertTrue(Arrays.equals(allergies1, listResult.get(0).getAllergies()));
		assertEquals("firstName2", listResult.get(1).getFirstName());
		assertEquals("lastName2", listResult.get(1).getLastName());
		assertEquals(date2, listResult.get(1).getBirthdate());
		assertTrue(Arrays.equals(medications2, listResult.get(1).getMedications()));
		assertTrue(Arrays.equals(allergies2, listResult.get(1).getAllergies()));
		assertEquals("firstName3", listResult.get(2).getFirstName());
		assertEquals("lastName3", listResult.get(2).getLastName());
		assertEquals(date3, listResult.get(2).getBirthdate());
		assertTrue(Arrays.equals(medications3, listResult.get(2).getMedications()));
		assertTrue(Arrays.equals(allergies3, listResult.get(2).getAllergies()));
	}

	@Test
	void givenExistingM2_getM2_returnsM2() throws Exception
	{
		// GIVEN
		ArrayList<MedicalRecord> listGiven = new ArrayList<MedicalRecord>(Arrays.asList(m1, m2, m3));
		dao.set(listGiven);
		// WHEN
		MedicalRecord m = null;
		m = dao.get("firstName2", "lastName2");
		// THEN
		assertEquals("firstName2", m.getFirstName());
		assertEquals("lastName2", m.getLastName());
		assertEquals(date2, m.getBirthdate());
		assertTrue(Arrays.equals(medications2, m.getMedications()));
		assertTrue(Arrays.equals(allergies2, m.getAllergies()));
	}

	@Test
	void givenMissingM2_getM2_throwsMedicalRecordNotFoundException()
	{
		// GIVEN
		// Empty list
		// WHEN & THEN
		assertThrows(MedicalRecordNotFoundException.class, () -> {
			dao.get("firstName2", "lastName2");
		});
	}
	
	@Test
	void saveM2_addsM2() throws Exception
	{
		// GIVEN
		// Empty list
		// WHEN
		dao.save(m2);
		ArrayList<MedicalRecord> listResult = dao.getAll();
		// THEN
		assertEquals(1, listResult.size());
		assertEquals("firstName2", listResult.get(0).getFirstName());
		assertEquals("lastName2", listResult.get(0).getLastName());
		assertEquals(date2, listResult.get(0).getBirthdate());
		assertTrue(Arrays.equals(medications2, listResult.get(0).getMedications()));
		assertTrue(Arrays.equals(allergies2, listResult.get(0).getAllergies()));
	}
	
	@Test
	void givenExistingRecordWithSameName_saveM1_throwsDuplicateMedicalRecordCreationException()
	{
		// GIVEN
		ArrayList<MedicalRecord> listGiven = new ArrayList<MedicalRecord>(Arrays.asList(m2));
		dao.set(listGiven);
		// WHEN
		assertThrows(DuplicateMedicalRecordCreationException.class, () -> {
			dao.save(m2);
		});
	}


	@Test
	void updateM2_changesM2() throws Exception
	{
		// GIVEN
		ArrayList<MedicalRecord> listGiven = new ArrayList<MedicalRecord>(Arrays.asList(m1, m2, m3));
		dao.set(listGiven);
		// WHEN
		String[] m2New = new String[] {"m4a", "m4b", "m4c", "m4d"};
		String[] a2New = new String[] {"a4a", "a4b", "a4c", "a4d"};
		Date d2New = Date.valueOf("4004-04-04");
		MedicalRecord record2New = new MedicalRecord ("firstName2","lastName2",d2New, m2New, a2New);
		dao.update(record2New);
		MedicalRecord m = null;
		m = dao.get("firstName2","lastName2");
		// THEN
		assertEquals("firstName2", m.getFirstName());
		assertEquals("lastName2", m.getLastName());
		assertEquals(d2New, m.getBirthdate());
		assertTrue(Arrays.equals(m2New, m.getMedications()));
		assertTrue(Arrays.equals(a2New, m.getAllergies()));
	}

	@Test
	void givenMissingM2_updateM2_throwsMedicalRecordNotFoundException()
	{
		// GIVEN
		// Empty list
		// WHEN & THEN
		assertThrows(MedicalRecordNotFoundException.class, () -> {
			dao.update(m2);
		});
	}

	@Test
	void givenM2_deleteM2_returnsTrue() throws Exception
	{
		// GIVEN
		ArrayList<MedicalRecord> listGiven = new ArrayList<MedicalRecord>(Arrays.asList(m1, m2, m3));
		dao.set(listGiven);
		// WHEN
		boolean result = dao.delete("firstName2", "lastName2");
		ArrayList<MedicalRecord> listResult = dao.getAll();
		// THEN
		assertEquals(true, result);
		assertEquals(2, listResult.size());
		assertEquals("firstName1", listResult.get(0).getFirstName());
		assertEquals("firstName3", listResult.get(1).getFirstName());
	}

	@Test
	void givenMissingMedicalRecord_delete_throwsMedicalRecordNotFoundException()
	{
		// GIVEN
		// Empty list
		// WHEN
		assertThrows(MedicalRecordNotFoundException.class, () -> {
			dao.delete("does not", "exist");
		});
	}
	
}
