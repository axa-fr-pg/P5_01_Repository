package projets.safetynet.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projets.safetynet.model.core.FireStation;

@SpringBootTest
public class FireStationDaoTest {

	@Autowired
	private FireStationDao dao;
	
	private FireStation s1;
	private FireStation s2;
	private FireStation s3;
	private FireStation s4;
	
	@BeforeEach
	private void prepareTests()
	{
		s1 = new FireStation ("address1", 12345);
		s2 = new FireStation ("COMMON", 2);
		s3 = new FireStation ("address3", 12345);
		s4 = new FireStation ("COMMON", 4);
	}
	
	@Test
	void getAll_returnsCompleteList()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2, s3));
		dao.set(listGiven);
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
		dao.set(listGiven);
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
	void givenMissingS2_getByAddressS2_throwsFireStationNotFoundException()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s3));
		dao.set(listGiven);
		// WHEN & THEN
		assertThrows(FireStationNotFoundException.class, () -> {
			dao.getByAddress(s2.getAddress());
		});
	}


	@Test
	void givenStationWithTwoAddresses_getByStation_returnsListWithBothAddresses()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2, s3, s4));
		FireStationDao dao = new FireStationDao(listGiven);
		// WHEN
		ArrayList<FireStation> listResult = dao.getByStation(s1.getStation());
		// THEN
		assertEquals(2, listResult.size());
		assertEquals(s1.getAddress(), listResult.get(0).getAddress());
		assertEquals(s1.getStation(), listResult.get(0).getStation());
		assertEquals(s3.getAddress(), listResult.get(1).getAddress());
		assertEquals(s3.getStation(), listResult.get(1).getStation());
	}

	@Test
	void givenMissingS2_getByStationS2_returnsEmptyList()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s3));
		dao.set(listGiven);
		// WHEN
		ArrayList<FireStation> listResult = dao.getByStation(s2.getStation());
		// THEN
		assertEquals(0, listResult.size());
	}

	@Test
	void givenNewS3_saveS3_addsS3()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2));
		dao.set(listGiven);
		// WHEN
		dao.save(s3);
		ArrayList<FireStation> listResult = dao.getAll();
		// THEN
		assertEquals(3, listResult.size());
	}

	@Test
	void givenExistingS2_updateByAddressS2_changesS2()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2, s3));
		dao.set(listGiven);
		// WHEN
		FireStation newValueStation = new FireStation (s2.getAddress(), 99999);
		try {
			dao.updateByAddress(newValueStation);
		} catch (FireStationNotFoundException e) {
			e.printStackTrace();
		}
		FireStation checkValueStation = null;
		try {
			checkValueStation = dao.getByAddress(s2.getAddress());
		} catch (FireStationNotFoundException e) {
			e.printStackTrace();
		}
		// THEN
		assertEquals(newValueStation.getAddress(), checkValueStation.getAddress());
		assertEquals(newValueStation.getStation(), checkValueStation.getStation());
	}

	@Test
	void givenMissingS2_updateByAddressS2_throwsFireStationNotFoundException()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s3));
		dao.set(listGiven);
		// WHEN & THEN
		assertThrows(FireStationNotFoundException.class, () -> {
			dao.updateByAddress(s2);
		});
	}

	@Test
	void givenS2_deleteS2_suppressesS2()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2, s3, s4));
		dao.set(listGiven);
		// WHEN
		dao.delete(s2);
		ArrayList<FireStation> listResult = dao.getAll();
		// THEN
		assertEquals(3, listResult.size());
	}	
}
