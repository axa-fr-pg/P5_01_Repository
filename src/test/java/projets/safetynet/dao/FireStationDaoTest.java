package projets.safetynet.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projets.safetynet.model.FireStation;
import projets.safetynet.model.FireStation;
import projets.safetynet.model.FireStation;

public class FireStationDaoTest {

	private FireStation s1;
	private FireStation s2;
	private FireStation s3;
	
	@BeforeEach
	private void prepareTests()
	{
		s1 = new FireStation ("address1", 1);
		s2 = new FireStation ("address2", 2);
		s3 = new FireStation ("address3", 3);
	}
	
	@Test
	void getAll_returnsCompleteList()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2, s3));
		FireStationDao dao = new FireStationDao(listGiven);
		// WHEN
		ArrayList<FireStation> listResult = dao.getAll();
		// THEN
		assertEquals(3, listResult.size());
	}
	
	@Test
	void givenExistingS2_getByAddressS2_returnsS2()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2, s3));
		FireStationDao dao = new FireStationDao(listGiven);
		// WHEN
		FireStation s = null;
		try {
			s = dao.getByAddress(s2.getAddress());
		} catch (FireStationNotFoundException e) {
			e.printStackTrace();
		}
		// THEN
		assertEquals(s2.getAddress(), s.getAddress());
		assertEquals(s2.getStation(), s.getStation());
	}

	@Test
	void givenExistingS2_getByStationS2_returnsS2()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2, s3));
		FireStationDao dao = new FireStationDao(listGiven);
		// WHEN
		FireStation s = null;
		try {
			s = dao.getByStation(s2.getStation());
		} catch (FireStationNotFoundException e) {
			e.printStackTrace();
		}
		// THEN
		assertEquals(s2.getAddress(), s.getAddress());
		assertEquals(s2.getStation(), s.getStation());
	}

	@Test
	void givenMissingS2_getByAddressS2_throwsFireStationNotFoundException()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s3));
		FireStationDao dao = new FireStationDao(listGiven);
		// WHEN & THEN
		assertThrows(FireStationNotFoundException.class, () -> {
			dao.getByAddress(s2.getAddress());
		});
	}

	@Test
	void givenMissingS2_getByStationS2_throwsFireStationNotFoundException()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s3));
		FireStationDao dao = new FireStationDao(listGiven);
		// WHEN & THEN
		assertThrows(FireStationNotFoundException.class, () -> {
			dao.getByStation(s2.getStation());
		});
	}

	@Test
	void saveS3_addsS3()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2));
		FireStationDao dao = new FireStationDao(listGiven);
		// WHEN
		dao.save(s3);
		ArrayList<FireStation> listResult = dao.getAll();
		// THEN
		assertEquals(3, listResult.size());
	}

	@Test
	void updateByAddressS2_changesS2()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2, s3));
		FireStationDao dao = new FireStationDao(listGiven);
		// WHEN
		FireStation s4 = new FireStation (s2.getAddress(), 99999);
		try {
			dao.updateByAddress(s4);
		} catch (FireStationNotFoundException e) {
			e.printStackTrace();
		}
		FireStation s = null;
		try {
			s = dao.getByAddress(s2.getAddress());
		} catch (FireStationNotFoundException e) {
			e.printStackTrace();
		}
		// THEN
		assertEquals(s4.getAddress(), s.getAddress());
		assertEquals(s4.getStation(), s.getStation());
	}

	@Test
	void updateByStationS2_changesS2()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2, s3));
		FireStationDao dao = new FireStationDao(listGiven);
		// WHEN
		FireStation s4 = new FireStation ("address-test-value", s2.getStation());
		try {
			dao.updateByStation(s4);
		} catch (FireStationNotFoundException e) {
			e.printStackTrace();
		}
		FireStation s = null;
		try {
			s = dao.getByStation(s2.getStation());
		} catch (FireStationNotFoundException e) {
			e.printStackTrace();
		}
		// THEN
		assertEquals(s4.getAddress(), s.getAddress());
		assertEquals(s4.getStation(), s.getStation());
	}

	@Test
	void givenMissingS2_updateByAddressS2_throwsFireStationNotFoundException()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s3));
		FireStationDao dao = new FireStationDao(listGiven);
		// WHEN & THEN
		assertThrows(FireStationNotFoundException.class, () -> {
			dao.updateByAddress(s2);
		});
	}

	@Test
	void givenMissingS2_updateByStationS2_throwsFireStationNotFoundException()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s3));
		FireStationDao dao = new FireStationDao(listGiven);
		// WHEN & THEN
		assertThrows(FireStationNotFoundException.class, () -> {
			dao.updateByStation(s2);
		});
	}

	@Test
	void givenS2_deleteS2_suppressesS2()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2, s3));
		FireStationDao dao = new FireStationDao(listGiven);
		// WHEN
		dao.delete(s2);
		ArrayList<FireStation> listResult = dao.getAll();
		// THEN
		assertEquals(2, listResult.size());
	}	
}
