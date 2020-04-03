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
	
	private FireStation s1 = new FireStation ("address1", 12345);;
	private FireStation s2 = new FireStation ("address2", 2);;
	private FireStation s3 = new FireStation ("address3", 12345);;
	private FireStation s4 = new FireStation ("address4", 4);
	
	@BeforeEach
	private void prepareTests()
	{
		dao.set(new ArrayList<FireStation>());
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
		assertEquals("address1", listResult.get(0).getAddress());
		assertEquals(12345, listResult.get(0).getStation());
		assertEquals("address2", listResult.get(1).getAddress());
		assertEquals(2, listResult.get(1).getStation());
		assertEquals("address3", listResult.get(2).getAddress());
		assertEquals(12345, listResult.get(2).getStation());
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
		assertEquals("address2", s.getAddress());
		assertEquals(2, s.getStation());
	}

	@Test
	void givenMissingS2_getByAddressS2_throwsFireStationNotFoundException()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s3));
		dao.set(listGiven);
		// WHEN & THEN
		assertThrows(FireStationNotFoundException.class, () -> {
			dao.getByAddress("does not exist");
		});
	}


	@Test
	void givenStationWithTwoAddresses_getByStation_returnsListWithBothAddresses()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2, s3, s4));
		dao.set(listGiven);
		// WHEN
		ArrayList<FireStation> listResult = dao.getByStation(12345);
		// THEN
		assertEquals(2, listResult.size());
		assertEquals("address1", listResult.get(0).getAddress());
		assertEquals(12345, listResult.get(0).getStation());
		assertEquals("address3", listResult.get(1).getAddress());
		assertEquals(12345, listResult.get(1).getStation());
	}

	@Test
	void givenMissingS2_getByStationS2_returnsEmptyList()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s3));
		dao.set(listGiven);
		// WHEN
		ArrayList<FireStation> listResult = dao.getByStation(2);
		// THEN
		assertEquals(0, listResult.size());
	}

	@Test
	void givenNewS3_saveS3_addsS3()
	{
		// GIVEN
		// Empty list
		FireStation s = null;
		// WHEN
		try {
			s = dao.save(s1);
		} catch (MultipleFireStationWithSameValuesException e) {
			e.printStackTrace();
		}
		ArrayList<FireStation> listResult = dao.getAll();
		// THEN
		assertEquals(1, listResult.size());
		assertEquals("address1", listResult.get(0).getAddress());
		assertEquals(12345, listResult.get(0).getStation());
	}

	@Test
	void givenExistingMappingWithSameValues_saveS2_throwsMultipleFireStationWithSameValuesException()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s2));
		dao.set(listGiven);
		// WHEN
		assertThrows(MultipleFireStationWithSameValuesException.class, () -> {
			dao.save(s2);
		});
	}

	@Test
	void givenTwoExistingMappingWithSameValues_saveS2_throwsMultipleFireStationWithSameValuesException()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s2, s2));
		dao.set(listGiven);
		// WHEN
		assertThrows(MultipleFireStationWithSameValuesException.class, () -> {
			dao.save(s2);
		});
	}
	
	@Test
	void givenExistingS3_updateByAddressS3_changesS3()
	{
		// GIVEN
		FireStation checkValueStation = null;
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2, s3, s4));
		dao.set(listGiven);
		// WHEN
		FireStation newValueStation = new FireStation ("address3", 99999);
		try {
			checkValueStation = dao.updateByAddress(newValueStation);
		} catch (FireStationNotFoundException e) {
			e.printStackTrace();
		}
		// THEN
		assertEquals("address3", checkValueStation.getAddress());
		assertEquals(99999, checkValueStation.getStation());
	}

	@Test
	void givenMissingS2_updateByAddressS2_throwsFireStationNotFoundException()
	{
		// GIVEN
		// Empty list
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
		assertEquals("address1", listResult.get(0).getAddress());
		assertEquals("address3", listResult.get(1).getAddress());
		assertEquals("address4", listResult.get(2).getAddress());
	}
	
	@Test
	void givenStation12345_deleteByStation_suppressesTwoStations()
	{
		// GIVEN
		ArrayList<FireStation> listGiven = new ArrayList<FireStation>(Arrays.asList(s1, s2, s3, s4));
		dao.set(listGiven);
		// WHEN
		dao.deleteByStation(12345);
		ArrayList<FireStation> listResult = dao.getAll();
		// THEN
		assertEquals(2, listResult.size());
		assertEquals("address2", listResult.get(0).getAddress());
		assertEquals("address4", listResult.get(1).getAddress());
	}	

}
